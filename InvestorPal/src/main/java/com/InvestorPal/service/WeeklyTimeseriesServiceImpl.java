package com.InvestorPal.service;


import com.InvestorPal.entity.WeeklyTimeseries;
import com.InvestorPal.repository.WeeklyTimeseriesRepository;
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
import weka.core.converters.ConverterUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

@Service
public class WeeklyTimeseriesServiceImpl implements TimeseriesService{
    private WeeklyTimeseriesRepository weeklyTimeseriesRepository;
    private List<WeeklyTimeseries> weeklyTimeseriesList;
    private static final String CSV_SEPARATOR = ",";

    @Autowired
    public WeeklyTimeseriesServiceImpl(WeeklyTimeseriesRepository weeklyTimeseriesRepository) {
        this.weeklyTimeseriesRepository = weeklyTimeseriesRepository;
    }

    @Override
    public List getTimeseriesForecast(String symbol, MLmodel modelType) {
        List<List<NumericPrediction>> forecastedData = null;

        weeklyTimeseriesList = getTimeseriesData(symbol);

        convertToARFF(weeklyTimeseriesList);
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
            InputStream inputStream = new FileInputStream(new File("data/weekly.arff"));

            ConverterUtils.DataSource loader = new ConverterUtils.DataSource(inputStream);

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
        weeklyTimeseriesList = weeklyTimeseriesRepository.findBySymbolIgnoreCaseOrderByCobdatePartitionAsc(symbol);
        return weeklyTimeseriesList;
    }


    public void convertToARFF(List<WeeklyTimeseries> timeseries) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data/weekly.arff"),"UTF-8"))) {
            StringBuffer relationHeader = new StringBuffer();

            relationHeader.append("@RELATION weekly");
            bufferedWriter.write(relationHeader.toString());
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            StringBuffer symbolHeader = new StringBuffer();
            symbolHeader.append("@ATTRIBUTE weekly_symbol string");
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

            StringBuffer dateHeader = new StringBuffer();
            dateHeader.append("@ATTRIBUTE cobdate_partition DATE \"yyyy-MM-dd\"");
            bufferedWriter.write(dateHeader.toString());
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            StringBuffer dataHeader = new StringBuffer();
            dataHeader.append("@DATA");
            bufferedWriter.write(dataHeader.toString());
            bufferedWriter.newLine();


            for (WeeklyTimeseries weeklyRecord : timeseries) {
                StringBuffer oneLine = new StringBuffer();

                oneLine.append(weeklyRecord.getSymbol());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(weeklyRecord.getOpen());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(weeklyRecord.getHigh());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(weeklyRecord.getLow());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(weeklyRecord.getClose());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(weeklyRecord.getAdjustedClose());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(weeklyRecord.getVolume());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(weeklyRecord.getDividendAmount());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append("\""+weeklyRecord.getCobdatePartition()+"\"");
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
            forecaster.setOverlayFields("stock_open,high,low,stock_close,adjusted_close,volume,dividend_amount");

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
            forecaster.setOverlayFields("stock_open,high,low,stock_close,adjusted_close,volume,dividend_amount");

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
            forecaster.setOverlayFields("stock_open,high,low,stock_close,adjusted_close,volume,dividend_amount");

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
            forecaster.setOverlayFields("stock_open,high,low,stock_close,adjusted_close,volume,dividend_amount");

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
            forecaster.setOverlayFields("stock_open,high,low,stock_close,adjusted_close,volume,dividend_amount");

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
            forecaster.setOverlayFields("stock_open,high,low,stock_close,adjusted_close,volume,dividend_amount");

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
