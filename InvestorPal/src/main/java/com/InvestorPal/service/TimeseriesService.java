package com.InvestorPal.service;

import com.InvestorPal.entity.DailyTimeseries;
import weka.classifiers.evaluation.NumericPrediction;
import weka.core.Instances;

import java.util.List;

public interface TimeseriesService {
    Object[][] getTimeseriesForecast(String symbol, MLmodel modelType);
    Instances loadARFFData();
    List getTimeseriesData(String symbol);
    List<List<NumericPrediction>> forecastGaussianProcess(Instances trainingData, Instances testData);
    List<List<NumericPrediction>> forecastLinearRegression(Instances trainingData, Instances testData);
    List<List<NumericPrediction>> forecastKNearestNeighbours(Instances trainingData, Instances testData);
    List<List<NumericPrediction>> forecastSupportVectorReg(Instances trainingData, Instances testData);
    List<List<NumericPrediction>> forecastDecisionTree(Instances trainingData, Instances testData);
    List<List<NumericPrediction>> forecastNeuralNetworks(Instances trainingData, Instances testData);
}