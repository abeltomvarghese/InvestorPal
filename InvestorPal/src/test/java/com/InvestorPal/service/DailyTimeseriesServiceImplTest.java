package com.InvestorPal.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import weka.classifiers.evaluation.NumericPrediction;
import weka.core.Instance;
import weka.core.Instances;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DailyTimeseriesServiceImplTest {

    private DailyTimeseriesServiceImpl dailyTimeseriesService;

    @Autowired
    public DailyTimeseriesServiceImplTest(DailyTimeseriesServiceImpl dailyTimeseriesService) {
        this.dailyTimeseriesService = dailyTimeseriesService;
    }

    private String getTestCompany() {
        return "IBM";
    }

    @Test
    @Order(1)
    public void canGenerateForecast() {
        Object[][] forecastedData =
                dailyTimeseriesService.getTimeseriesForecast(getTestCompany(),MLmodel.GAUSSIAN_PROCESS);

        for(int x = 0; x<forecastedData.length; x++){
            System.out.println("Date " + forecastedData[x][0] + " Truth: " + forecastedData[x][1] + " Pred: "+ forecastedData[x][2]);
        }
        assertNotNull(forecastedData);
    }

    @Test
    @Order(2)
    public void canLoadARFFDataCorrectly() {
        Instances inputData = dailyTimeseriesService.loadARFFData();
        assertNotNull(inputData);
    }

    @Test
    @Order(3)
    public void canForecastUsingGaussianProcess() {
        Instances inputData = dailyTimeseriesService.loadARFFData();

        int numRecords = inputData.numInstances();
        int trainingSplit = (60 * numRecords)/100;

        Instances trainingData = new Instances(inputData, 0,trainingSplit);
        Instance lastRecord = trainingData.lastInstance();

        Instances myTestData = new Instances(inputData);
        myTestData.delete();

        for (int recordIndex = trainingSplit;recordIndex < numRecords;recordIndex++){
            Instance myRecordExamine = inputData.get(recordIndex);
            myTestData.add(myRecordExamine);
        }

        System.out.println(myTestData.toString());
        Instance firstRecord = myTestData.instance(0);

        System.out.println("LAST RECORD IN TRAINING DATA: ");
        System.out.println(lastRecord.toString());

        System.out.println("FIRST RECORD IN TEST DATA: ");
        System.out.println(firstRecord.toString());
        System.out.println("buffer");

        List<List<NumericPrediction>> forecastedData = dailyTimeseriesService.forecastGaussianProcess(trainingData, myTestData);
        System.out.println(forecastedData.toString());
    }

    @Test
    @Order(4)
    public void canGenerateForecastUsingLinearRegression() {
        Object[][] forecastedData =
                dailyTimeseriesService.getTimeseriesForecast(getTestCompany(),MLmodel.LINEAR_REGRESSION);

        for(int x = 0; x<forecastedData.length; x++){
            System.out.println("Date " + forecastedData[x][0] + " Truth: " + forecastedData[x][1] + " Pred: "+ forecastedData[x][2]);
        }
        assertNotNull(forecastedData);
    }

    @Test
    @Order(5)
    public void canGenerateForecastUsingKNN() {
        Object[][] forecastedData =
                dailyTimeseriesService.getTimeseriesForecast(getTestCompany(),MLmodel.KNN);

        for(int x = 0; x<forecastedData.length; x++){
            System.out.println("Date " + forecastedData[x][0] + " Truth: " + forecastedData[x][1] + " Pred: "+ forecastedData[x][2]);
        }
        assertNotNull(forecastedData);
    }

    @Test
    @Order(6)
    public void canGenerateForecastUsingSVR() {
        Object[][] forecastedData =
                dailyTimeseriesService.getTimeseriesForecast(getTestCompany(),MLmodel.SVR);

        for(int x = 0; x<forecastedData.length; x++){
            System.out.println("Date " + forecastedData[x][0] + " Truth: " + forecastedData[x][1] + " Pred: "+ forecastedData[x][2]);
        }
        assertNotNull(forecastedData);
    }

    @Test
    @Order(7)
    public void canGenerateForecastUsingDecisionTrees() {
        Object[][] forecastedData =
                dailyTimeseriesService.getTimeseriesForecast(getTestCompany(),MLmodel.DECISION_TREE);

        for(int x = 0; x<forecastedData.length; x++){
            System.out.println("Date " + forecastedData[x][0] + " Truth: " + forecastedData[x][1] + " Pred: "+ forecastedData[x][2]);
        }
        assertNotNull(forecastedData);
    }

    @Test
    @Order(8)
    public void canGenerateForecastUsingNeuralNetworks() {
        Object[][] forecastedData =
                dailyTimeseriesService.getTimeseriesForecast(getTestCompany(),MLmodel.NEURAL_NETWORK);

        for(int x = 0; x<forecastedData.length; x++){
            System.out.println("Date " + forecastedData[x][0] + " Truth: " + forecastedData[x][1] + " Pred: "+ forecastedData[x][2]);
        }
        assertNotNull(forecastedData);
    }

    @Test
    @Order(9)
    public void getErrorRateForGaussianProcess() {
        double errorRate = dailyTimeseriesService.getRMSE(MLmodel.GAUSSIAN_PROCESS);
        System.out.println("RMSE error rate for Gaussian Processes: " + errorRate);
        double hundred = 100.00;

        String error = String.valueOf(errorRate);
        double newError = Double.parseDouble(error);
        double percent = hundred - newError;
        System.out.println("Accuracy: " + percent);
        assertNotEquals(0.0,errorRate);
    }

    @Test
    @Order(10)
    public void getErrorRateForLinearRegression() {
        double errorRate = dailyTimeseriesService.getRMSE(MLmodel.LINEAR_REGRESSION);
        System.out.println("RMSE error rate for Linear Regression: " + errorRate);
        double hundred = 100.00;

        String error = String.valueOf(errorRate);
        double newError = Double.parseDouble(error);
        double percent = hundred - newError;
        System.out.println("Accuracy: " + percent);
        assertNotEquals(0.0,errorRate);
    }

    @Test
    @Order(11)
    public void getErrorRateForSVR() {
        double errorRate = dailyTimeseriesService.getRMSE(MLmodel.SVR);
        System.out.println("RMSE error rate for Support Vector Regression: " + errorRate);
        double hundred = 100.00;

        String error = String.valueOf(errorRate);
        double newError = Double.parseDouble(error);
        double percent = hundred - newError;
        System.out.println("Accuracy: " + percent);
        assertNotEquals(0.0,errorRate);
    }

    @Test
    @Order(12)
    public void getErrorRateForDecisionTrees() {
        double errorRate = dailyTimeseriesService.getRMSE(MLmodel.DECISION_TREE);
        System.out.println("RMSE error rate for Decision Trees: " + errorRate);
        double hundred = 100.00;

        String error = String.valueOf(errorRate);
        double newError = Double.parseDouble(error);
        double percent = hundred - newError;
        System.out.println("Accuracy: " + percent);
        assertNotEquals(0.0,errorRate);
    }

    @Test
    @Order(13)
    public void getErrorRateForKNN() {
        double errorRate = dailyTimeseriesService.getRMSE(MLmodel.KNN);
        System.out.println("RMSE error rate for KNN: " + errorRate);
        double hundred = 100.00;

        String error = String.valueOf(errorRate);
        double newError = Double.parseDouble(error);
        double percent = hundred - newError;
        System.out.println("Accuracy: " + percent);
        assertNotEquals(0.0,errorRate);
    }

    @Test
    @Order(14)
    public void getErrorRateForNeuralNetwork() {
        double errorRate = dailyTimeseriesService.getRMSE(MLmodel.NEURAL_NETWORK);
        System.out.println("RMSE error rate for Neural Network: " + errorRate);
        double hundred = 100.00;

        String error = String.valueOf(errorRate);
        double newError = Double.parseDouble(error);
        double percent = hundred - newError;
        System.out.println("Accuracy: " + percent);
        assertNotEquals(0.0,errorRate);
    }

    @Test
    @Order(15)
    public void getComparisionValsForGaussianProcess() {
        Instances inputData = dailyTimeseriesService.loadARFFData();

        int numRecords = inputData.numInstances();
        int trainingSplit = (60 * numRecords)/100;

        Instances trainingData = new Instances(inputData, 0,trainingSplit);
        Instance lastRecord = trainingData.lastInstance();

        Instances myTestData = new Instances(inputData);
        myTestData.delete();

        for (int recordIndex = trainingSplit;recordIndex < numRecords;recordIndex++){
            Instance myRecordExamine = inputData.get(recordIndex);
            myTestData.add(myRecordExamine);
        }

        System.out.println(myTestData.toString());
        Instance firstRecord = myTestData.instance(0);

        System.out.println("LAST RECORD IN TRAINING DATA: ");
        System.out.println(lastRecord.toString());

        System.out.println("FIRST RECORD IN TEST DATA: ");
        System.out.println(firstRecord.toString());
        System.out.println("buffer");

        List<List<NumericPrediction>> forecastedData = dailyTimeseriesService.forecastGaussianProcess(trainingData, myTestData);
        System.out.println(forecastedData.toString());

        double vals[][] = dailyTimeseriesService.getComparisonForAllValues(forecastedData, myTestData);
        double errorRate = dailyTimeseriesService.findRMSPE(vals);
        System.out.println("Error Rate: " + errorRate);
        assertNotEquals(0.0, errorRate);
    }
}