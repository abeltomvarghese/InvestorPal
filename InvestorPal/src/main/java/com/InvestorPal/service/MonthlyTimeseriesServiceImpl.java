package com.InvestorPal.service;

import com.InvestorPal.entity.MonthlyTimeseries;
import com.InvestorPal.repository.MonthlyTimeseriesRepository;
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
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class MonthlyTimeseriesServiceImpl implements TimeseriesService {
    private MonthlyTimeseriesRepository monthlyTimeseriesRepository;
    private List<MonthlyTimeseries> monthlyTimeseriesList;
    private static final String CSV_SEPARATOR = ",";
    private GaussianProcesses gpModel;
    private LinearRegression linearModel;
    private IBk knnModel;
    private SMOreg svrModel;
    private REPTree decisionTreeModel;
    private MultilayerPerceptron mlpModel;


    @Autowired
    public MonthlyTimeseriesServiceImpl(MonthlyTimeseriesRepository monthlyTimeseriesRepository) {
        this.monthlyTimeseriesRepository = monthlyTimeseriesRepository;

        this.gpModel = new GaussianProcesses();
        this.linearModel = new LinearRegression();
        this.knnModel = new IBk();
        this.svrModel = new SMOreg();
        this.decisionTreeModel = new REPTree();
        this.mlpModel = new MultilayerPerceptron();
    }

    @Override
    public Object[][] getTimeseriesForecast(String symbol, MLmodel modelType) {
        List<List<NumericPrediction>> forecastedData = null;

        monthlyTimeseriesList = getTimeseriesData(symbol);

        convertToARFF(monthlyTimeseriesList);
        Instances fullData = loadARFFData();

        int trainingSplit = getTrainingSplit(fullData);

        Instances trainingData = getTrainingData(fullData, trainingSplit);

        Instances testData = getTestData(fullData, trainingSplit);

        forecastedData = getForecastFromModel(modelType, trainingData, testData);
        System.out.println(forecastedData.toString());
        double predActData[][] = getComparisonForAllValues(forecastedData, testData);

        Object curatedData[][] = new Object[testData.numInstances()][3];

        DecimalFormat df = new DecimalFormat("#.##");

        for (int x = 0; x<testData.numInstances(); x++) {
            String recordString = testData.get(x).toString();
            String date = recordString.substring(recordString.length()-10);
            curatedData[x][0] = date;
            curatedData[x][1] = predActData[x][1];
            curatedData[x][2] = Double.parseDouble(df.format(predActData[x][0]));
        }

        return curatedData;

    }

    @Override
    public Instances loadARFFData() {
        Instances fullData = null;
        try {
            InputStream inputStream = new FileInputStream(new File("data/monthly.arff"));

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
        monthlyTimeseriesList = monthlyTimeseriesRepository.findBySymbolIgnoreCaseOrderByCobdatePartitionAsc(symbol);
        return monthlyTimeseriesList;
    }


    public void convertToARFF(List<MonthlyTimeseries> timeseries) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data/monthly.arff"),"UTF-8"))) {
            StringBuffer relationHeader = new StringBuffer();

            relationHeader.append("@RELATION monthly");
            bufferedWriter.write(relationHeader.toString());
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            StringBuffer symbolHeader = new StringBuffer();
            symbolHeader.append("@ATTRIBUTE monthly_symbol string");
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


            for (MonthlyTimeseries monthlyRecord : timeseries) {
                StringBuffer oneLine = new StringBuffer();

                oneLine.append(monthlyRecord.getSymbol());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(monthlyRecord.getOpen());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(monthlyRecord.getHigh());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(monthlyRecord.getLow());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(monthlyRecord.getClose());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(monthlyRecord.getAdjustedClose());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(monthlyRecord.getVolume());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(monthlyRecord.getDividendAmount());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append("\""+monthlyRecord.getCobdatePartition()+"\"");
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

    public int getTrainingSplit(Instances fullData) {
        int numRecords = fullData.numInstances();
        int trainingSplit = (60 * numRecords)/100;

        return trainingSplit;
    }

    public Instances getTrainingData(Instances fullData, int trainingSplit) {
        return new Instances(fullData, 0,trainingSplit);
    }

    public Instances getTestData(Instances fullData, int trainingSplit) {
        Instances testData = new Instances(fullData);
        testData.delete();

        for (int recordIndex = trainingSplit;recordIndex < fullData.numInstances();recordIndex++){
            Instance eachRecord = fullData.get(recordIndex);
            testData.add(eachRecord);
        }
        return testData;
    }

    private List<List<NumericPrediction>> getForecastFromModel(MLmodel modelType, Instances trainingData, Instances testData) {
        List<List<NumericPrediction>> forecastedData = null;
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
                forecastedData = forecastDecisionTree(trainingData, testData);
                break;
            case NEURAL_NETWORK:
                forecastedData = forecastNeuralNetworks(trainingData, testData);
                break;
        }
        return forecastedData;
    }

    public double[][] getComparisonForAllValues(List<List<NumericPrediction>> predictions, Instances testData) {
        double allValues[][] = new double[testData.numInstances()][2];

        for (int x = 0; x<testData.numInstances(); x++) {
            allValues[x][0] = Double.parseDouble(String.valueOf(predictions.get(x).get(0).predicted()));
            allValues[x][1] = testData.get(x).value(4);
        }

        return allValues;
    }

    public double findRMSPE(double allValues[][]) {
        double errorRate = 0.0d;
        double sum = 0.0;
        double[] individualVals = new double[allValues.length];
        try{
            for(int x = 0; x < allValues.length; x++) {
                String truthVal = String.valueOf(allValues[x][1]).toString();
                String predVal = String.valueOf(allValues[x][0]).toString();

                double dTruth = Double.parseDouble(truthVal) * 1000000000;
                double dPred = Double.parseDouble(predVal) * 1000000000;

                double difference = dTruth - dPred;
                String diff = String.valueOf(difference).toString();
                difference = Double.parseDouble(diff);

                double percentDiff = difference / dTruth;
                percentDiff *= 1000000000;
                String pDiff = String.valueOf(percentDiff).toString();
                percentDiff = Double.parseDouble(pDiff);

                String squaredVal = String.valueOf(Math.pow(percentDiff,2)).toString();
                individualVals[x] = Double.parseDouble(squaredVal);
                sum += individualVals[x];
            }

            errorRate = Math.sqrt(sum/allValues.length);
            String sErr = String.valueOf(errorRate);
            errorRate = Double.parseDouble(sErr);
            errorRate = errorRate / 1000000000;
            errorRate = errorRate * 100;
        } catch (Exception e) {
            System.out.println("Error Cause" + e.getCause());
            System.out.println("Error Message" + e.getMessage());
            e.printStackTrace();
        }

        return errorRate;
    }

    public double getRMSE(MLmodel modelType) {
        double errorRate = 0.0;
        List<List<NumericPrediction>> forecastedData = null;

        Instances fullData = loadARFFData();

        int trainingSplit = getTrainingSplit(fullData);
        Instances trainingData = getTrainingData(fullData, trainingSplit);
        Instances testData = getTestData(fullData, trainingSplit);

        double allValues[][];

        forecastedData = getForecastFromModel(modelType, trainingData, testData);

        allValues = getComparisonForAllValues(forecastedData, testData);
        errorRate = findRMSPE(allValues);

        return errorRate;
    }

    @Override
    public List<List<NumericPrediction>> forecastGaussianProcess(Instances trainingData, Instances testData) {
        WekaForecaster forecaster = new WekaForecaster();
        List<List<NumericPrediction>> forecastData = null;

        try {
            forecaster.setFieldsToForecast("stock_close");
            forecaster.setBaseForecaster(gpModel);

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
            forecaster.setBaseForecaster(linearModel);

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
            forecaster.setBaseForecaster(knnModel);

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
            forecaster.setBaseForecaster(svrModel);

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
            forecaster.setBaseForecaster(decisionTreeModel);

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
            forecaster.setBaseForecaster(mlpModel);

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