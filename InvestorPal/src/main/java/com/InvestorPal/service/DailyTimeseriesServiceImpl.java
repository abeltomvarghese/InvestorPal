package com.InvestorPal.service;

import com.InvestorPal.entity.DailyTimeseries;
import com.InvestorPal.repository.DailyTimeseriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.lazy.IBk;
import weka.classifiers.timeseries.WekaForecaster;
import weka.classifiers.trees.REPTree;
import weka.core.Instance;
import weka.core.Instances;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import static weka.core.converters.ConverterUtils.DataSource;

@Service
public class DailyTimeseriesServiceImpl implements TimeseriesService {

    private DailyTimeseriesRepository dailyTimeseriesRepository;
    private List<DailyTimeseries> dailyTimeseriesList;
    private static final String CSV_SEPARATOR = ",";

    @Autowired
    public DailyTimeseriesServiceImpl(DailyTimeseriesRepository dailyTimeseriesRepository) {
        this.dailyTimeseriesRepository = dailyTimeseriesRepository;
    }

    @Override
    public List getTimeseriesForecast(String symbol, MLmodel modelType) {
        List<List<NumericPrediction>> forecastedData = null;

        dailyTimeseriesList = getTimeseriesData(symbol);

        convertToARFF(dailyTimeseriesList);
        Instances fullData = loadARFFData();

        int numRecords = fullData.numInstances();
        int trainingSplit = (60 * numRecords)/100;

        Instances trainingData = new Instances(fullData, 0,trainingSplit);

        Instances testData = new Instances(fullData);
        testData.delete();

        for (int recordIndex = trainingSplit;recordIndex < fullData.numInstances();recordIndex++){
            Instance eachRecord = fullData.get(recordIndex);
            testData.add(eachRecord);
        }

        switch (modelType) {
            case GAUSSIAN_PROCESS:
                forecastedData = forecastGaussianProcess(trainingData, testData);
                break;
            case LINEAR_REGRESSION:
                forecastedData = forecastLinearRegression(trainingData, testData);
                break;
            case KNN:
                forecastedData = forecastKNearestNeighbours(trainingData, testData);
                break;
            case SVR:
                forecastedData = forecastSupportVectorReg(trainingData, testData);
                break;
            case DECISION_TREE:
                forecastedData = forecastDecisionTree(trainingData,testData);
                break;
            case NEURAL_NETWORK:
                forecastedData = forecastNeuralNetworks(trainingData,testData);
                break;
        }


        return forecastedData;
    }

    @Override
    public Instances loadARFFData() {
        Instances fullData = null;
        try {
            InputStream inputStream = new FileInputStream(new File("data/daily.arff"));

            DataSource loader = new DataSource(inputStream);

            fullData = loader.getDataSet();

        } catch (Exception e) {
            System.out.println("Error Cause" + e.getCause());
            System.out.println("Error Message" + e.getMessage());
            e.printStackTrace();
        }
        return fullData;
    }

    @Override
    public List getTimeseriesData(String symbol) {
        dailyTimeseriesList = dailyTimeseriesRepository.findBySymbolIgnoreCaseOrderByCobdatePartitionAsc(symbol);
        return dailyTimeseriesList;
    }


    public void convertToARFF(List<DailyTimeseries> timeseries) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data/daily.arff"),"UTF-8"))) {
            StringBuffer relationHeader = new StringBuffer();

            relationHeader.append("@RELATION daily");
            bufferedWriter.write(relationHeader.toString());
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            StringBuffer symbolHeader = new StringBuffer();
            symbolHeader.append("@ATTRIBUTE daily_symbol string");
            bufferedWriter.write(symbolHeader.toString());
            bufferedWriter.newLine();

            StringBuffer openHeader = new StringBuffer();
            openHeader.append("@ATTRIBUTE stock_open real");
            bufferedWriter.write(openHeader.toString());
            bufferedWriter.newLine();

            StringBuffer highHeader = new StringBuffer();
            highHeader.append("@ATTRIBUTE high real");
            bufferedWriter.write(highHeader.toString());
            bufferedWriter.newLine();

            StringBuffer lowHeader = new StringBuffer();
            lowHeader.append("@ATTRIBUTE low real");
            bufferedWriter.write(lowHeader.toString());
            bufferedWriter.newLine();

            StringBuffer closeHeader = new StringBuffer();
            closeHeader.append("@ATTRIBUTE stock_close real");
            bufferedWriter.write(closeHeader.toString());
            bufferedWriter.newLine();

            StringBuffer adjCloseHeader = new StringBuffer();
            adjCloseHeader.append("@ATTRIBUTE adjusted_close real");
            bufferedWriter.write(adjCloseHeader.toString());
            bufferedWriter.newLine();

            StringBuffer volumeHeader = new StringBuffer();
            volumeHeader.append("@ATTRIBUTE volume real");
            bufferedWriter.write(volumeHeader.toString());
            bufferedWriter.newLine();

            StringBuffer divHeader = new StringBuffer();
            divHeader.append("@ATTRIBUTE dividend_amount real");
            bufferedWriter.write(divHeader.toString());
            bufferedWriter.newLine();

            StringBuffer splitCoEffHeader = new StringBuffer();
            splitCoEffHeader.append("@ATTRIBUTE split_coefficient real");
            bufferedWriter.write(splitCoEffHeader.toString());
            bufferedWriter.newLine();

            StringBuffer dateHeader = new StringBuffer();
            dateHeader.append("@ATTRIBUTE cobdate_partition DATE \"yyyy-MM-dd\"");
            bufferedWriter.write(dateHeader.toString());
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            StringBuffer dataHeader = new StringBuffer();
            dataHeader.append("@DATA");
            bufferedWriter.write(dataHeader.toString());
            bufferedWriter.newLine();


            for (DailyTimeseries dailyRecord : timeseries) {
                StringBuffer oneLine = new StringBuffer();

                oneLine.append(dailyRecord.getSymbol());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(dailyRecord.getOpen());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(dailyRecord.getHigh());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(dailyRecord.getLow());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(dailyRecord.getClose());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(dailyRecord.getAdjustedClose());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(dailyRecord.getVolume());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(dailyRecord.getDividendAmount());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(dailyRecord.getSplitCoefficient());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append("\""+dailyRecord.getCobdatePartition()+"\"");
                bufferedWriter.write(oneLine.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();
        } catch (Exception e) {
            System.out.println("Error Cause" + e.getCause());
            System.out.println("Error Message" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<List<NumericPrediction>> forecastGaussianProcess(Instances trainingData, Instances testData) {
        WekaForecaster forecaster = new WekaForecaster();
        List<List<NumericPrediction>> forecastData = null;

        try {
            forecaster.setFieldsToForecast("stock_close");
            forecaster.setBaseForecaster(new GaussianProcesses());

            forecaster.getTSLagMaker().setTimeStampField("cobdate_partition");
            forecaster.getTSLagMaker().setMinLag(1);
            forecaster.getTSLagMaker().setMaxLag(12);

            forecaster.getTSLagMaker().setAddDayOfWeek(true);
            forecaster.getTSLagMaker().setAddDayOfMonth(true);
            forecaster.setOverlayFields("stock_open,high,low,stock_close,adjusted_close,volume,dividend_amount,split_coefficient");

            forecaster.buildForecaster(trainingData, System.out);
            forecaster.primeForecaster(trainingData);

            forecastData = forecaster.forecast(12, testData);

            return forecastData;
        } catch (Exception e) {
            System.out.println("Error Cause: " + e.getCause());
            System.out.println("Error Message: " + e.getMessage());
            e.printStackTrace();
        }
        return forecastData;
    }

    @Override
    public List<List<NumericPrediction>> forecastLinearRegression(Instances trainingData, Instances testData) {
        WekaForecaster forecaster = new WekaForecaster();
        List<List<NumericPrediction>> forecastData = null;

        try {
            forecaster.setFieldsToForecast("stock_close");
            forecaster.setBaseForecaster(new LinearRegression());

            forecaster.getTSLagMaker().setTimeStampField("cobdate_partition");
            forecaster.getTSLagMaker().setMinLag(1);
            forecaster.getTSLagMaker().setMaxLag(12);

            forecaster.getTSLagMaker().setAddDayOfWeek(true);
            forecaster.getTSLagMaker().setAddDayOfMonth(true);
            forecaster.setOverlayFields("stock_open,high,low,stock_close,adjusted_close,volume,dividend_amount,split_coefficient");

            forecaster.buildForecaster(trainingData, System.out);
            forecaster.primeForecaster(trainingData);

            forecastData = forecaster.forecast(12, testData);

            return forecastData;
        } catch (Exception e) {
            System.out.println("Error Cause: " + e.getCause());
            System.out.println("Error Message: " + e.getMessage());
            e.printStackTrace();
        }
        return forecastData;
    }

    @Override
    public List<List<NumericPrediction>> forecastKNearestNeighbours(Instances trainingData, Instances testData) {
        WekaForecaster forecaster = new WekaForecaster();
        List<List<NumericPrediction>> forecastData = null;

        try {
            forecaster.setFieldsToForecast("stock_close");
            forecaster.setBaseForecaster(new IBk());

            forecaster.getTSLagMaker().setTimeStampField("cobdate_partition");
            forecaster.getTSLagMaker().setMinLag(1);
            forecaster.getTSLagMaker().setMaxLag(12);

            forecaster.getTSLagMaker().setAddDayOfWeek(true);
            forecaster.getTSLagMaker().setAddDayOfMonth(true);
            forecaster.setOverlayFields("stock_open,high,low,stock_close,adjusted_close,volume,dividend_amount,split_coefficient");

            forecaster.buildForecaster(trainingData, System.out);
            forecaster.primeForecaster(trainingData);

            forecastData = forecaster.forecast(12, testData);

            return forecastData;
        } catch (Exception e) {
            System.out.println("Error Cause: " + e.getCause());
            System.out.println("Error Message: " + e.getMessage());
            e.printStackTrace();
        }
        return forecastData;
    }

    @Override
    public List<List<NumericPrediction>> forecastSupportVectorReg(Instances trainingData, Instances testData) {
        WekaForecaster forecaster = new WekaForecaster();
        List<List<NumericPrediction>> forecastData = null;

        try {
            forecaster.setFieldsToForecast("stock_close");
            forecaster.setBaseForecaster(new SMOreg());

            forecaster.getTSLagMaker().setTimeStampField("cobdate_partition");
            forecaster.getTSLagMaker().setMinLag(1);
            forecaster.getTSLagMaker().setMaxLag(12);

            forecaster.getTSLagMaker().setAddDayOfWeek(true);
            forecaster.getTSLagMaker().setAddDayOfMonth(true);
            forecaster.setOverlayFields("stock_open,high,low,stock_close,adjusted_close,volume,dividend_amount,split_coefficient");

            forecaster.buildForecaster(trainingData, System.out);
            forecaster.primeForecaster(trainingData);

            forecastData = forecaster.forecast(12, testData);

            return forecastData;
        } catch (Exception e) {
            System.out.println("Error Cause: " + e.getCause());
            System.out.println("Error Message: " + e.getMessage());
            e.printStackTrace();
        }
        return forecastData;
    }

    @Override
    public List<List<NumericPrediction>> forecastDecisionTree(Instances trainingData, Instances testData) {
        WekaForecaster forecaster = new WekaForecaster();
        List<List<NumericPrediction>> forecastData = null;

        try {
            forecaster.setFieldsToForecast("stock_close");
            forecaster.setBaseForecaster(new REPTree());

            forecaster.getTSLagMaker().setTimeStampField("cobdate_partition");
            forecaster.getTSLagMaker().setMinLag(1);
            forecaster.getTSLagMaker().setMaxLag(12);

            forecaster.getTSLagMaker().setAddDayOfWeek(true);
            forecaster.getTSLagMaker().setAddDayOfMonth(true);
            forecaster.setOverlayFields("stock_open,high,low,stock_close,adjusted_close,volume,dividend_amount,split_coefficient");

            forecaster.buildForecaster(trainingData, System.out);
            forecaster.primeForecaster(trainingData);

            forecastData = forecaster.forecast(12, testData);

            return forecastData;
        } catch (Exception e) {
            System.out.println("Error Cause: " + e.getCause());
            System.out.println("Error Message: " + e.getMessage());
            e.printStackTrace();
        }
        return forecastData;
    }

    @Override
    public List<List<NumericPrediction>> forecastNeuralNetworks(Instances trainingData, Instances testData) {
        WekaForecaster forecaster = new WekaForecaster();
        List<List<NumericPrediction>> forecastData = null;

        try {
            forecaster.setFieldsToForecast("stock_close");
            forecaster.setBaseForecaster(new MultilayerPerceptron());

            forecaster.getTSLagMaker().setTimeStampField("cobdate_partition");
            forecaster.getTSLagMaker().setMinLag(1);
            forecaster.getTSLagMaker().setMaxLag(12);

            forecaster.getTSLagMaker().setAddDayOfWeek(true);
            forecaster.getTSLagMaker().setAddDayOfMonth(true);
            forecaster.setOverlayFields("stock_open,high,low,stock_close,adjusted_close,volume,dividend_amount,split_coefficient");

            forecaster.buildForecaster(trainingData, System.out);
            forecaster.primeForecaster(trainingData);

            forecastData = forecaster.forecast(12, testData);

            return forecastData;
        } catch (Exception e) {
            System.out.println("Error Cause: " + e.getCause());
            System.out.println("Error Message: " + e.getMessage());
            e.printStackTrace();
        }
        return forecastData;
    }
}
