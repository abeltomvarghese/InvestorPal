package com.InvestorPal.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import weka.classifiers.evaluation.NumericPrediction;
import weka.core.Instance;
import weka.core.Instances;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DailyTimeseriesServiceImplTest {

    private DailyTimeseriesServiceImpl dailyTimeseriesService;

    @Autowired
    public DailyTimeseriesServiceImplTest(DailyTimeseriesServiceImpl dailyTimeseriesService) {
        this.dailyTimeseriesService = dailyTimeseriesService;
    }

    @Test
    public void canGenerateForecast() {
        List<List<NumericPrediction>> forecastedData =
                dailyTimeseriesService.getTimeseriesForecast("JPM",MLmodel.GAUSSIAN_PROCESS);
        System.out.println(forecastedData.toString());
        assertNotNull(forecastedData);
    }

    @Test
    public void canLoadARFFDataCorrectly() {
        Instances inputData = dailyTimeseriesService.loadARFFData();
        assertNotNull(inputData);
    }

    @Test
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
    public void canGenerateForecastUsingLinearRegression() {
        List<List<NumericPrediction>> forecastedData =
                dailyTimeseriesService.getTimeseriesForecast("JPM",MLmodel.LINEAR_REGRESSION);
        System.out.println(forecastedData.toString());
        assertNotNull(forecastedData);
    }

    @Test
    public void canGenerateForecastUsingKNN() {
        List<List<NumericPrediction>> forecastedData =
                dailyTimeseriesService.getTimeseriesForecast("JPM",MLmodel.KNN);
        System.out.println(forecastedData.toString());
        assertNotNull(forecastedData);
    }

    @Test
    public void canGenerateForecastUsingSVR() {
        List<List<NumericPrediction>> forecastedData =
                dailyTimeseriesService.getTimeseriesForecast("JPM",MLmodel.SVR);
        System.out.println(forecastedData.toString());
        assertNotNull(forecastedData);
    }

    @Test
    public void canGenerateForecastUsingDecisionTrees() {
        List<List<NumericPrediction>> forecastedData =
                dailyTimeseriesService.getTimeseriesForecast("JPM",MLmodel.DECISION_TREE);
        System.out.println(forecastedData.toString());
        assertNotNull(forecastedData);
    }
    @Test
    public void canGenerateForecastUsingNeuralNetworks() {
        List<List<NumericPrediction>> forecastedData =
                dailyTimeseriesService.getTimeseriesForecast("JPM",MLmodel.NEURAL_NETWORK);
        System.out.println(forecastedData.toString());
        assertNotNull(forecastedData);
    }
}