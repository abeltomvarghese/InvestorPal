package com.InvestorPal.controller;

import com.InvestorPal.entity.StockEntity;
import com.InvestorPal.service.AlphaVantage;
import com.InvestorPal.service.DailyTimeseriesServiceImpl;
import com.InvestorPal.service.MLmodel;
import com.InvestorPal.service.MonthlyTimeseriesServiceImpl;
import com.InvestorPal.service.StockService;
import com.InvestorPal.service.WeeklyTimeseriesServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.logging.Logger;


@RestController
@RequestMapping("/stock")
public class StockController {

    private StockService stockService;
    private AlphaVantage alphaVantage;
    private DailyTimeseriesServiceImpl dailyTimeseriesService;
    private WeeklyTimeseriesServiceImpl weeklyTimeseriesService;
    private MonthlyTimeseriesServiceImpl monthlyTimeseriesService;

    @Autowired
    public StockController(StockService stockService,
                           DailyTimeseriesServiceImpl dailyTimeseriesService,
                           WeeklyTimeseriesServiceImpl weeklyTimeseriesService,
                           MonthlyTimeseriesServiceImpl monthlyTimeseriesService,
                           AlphaVantage alphaVantage) {
        this.stockService = stockService;
        this.dailyTimeseriesService = dailyTimeseriesService;
        this.weeklyTimeseriesService = weeklyTimeseriesService;
        this.monthlyTimeseriesService = monthlyTimeseriesService;
        this.alphaVantage = alphaVantage;
    }

    @GetMapping("/myList")
    public ResponseEntity<List<StockEntity>> getAllStocks() {
        List<StockEntity> list = stockService.listAllStocks();

        if (CollectionUtils.isEmpty(list)) {
            return new ResponseEntity<List<StockEntity>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<StockEntity>>(list, HttpStatus.OK);
    }

    @GetMapping("/{period}/{ticker}/{compName}/{mlModel}")
    public ResponseEntity<Object> getForecast(@PathVariable("ticker") String ticker,
                                              @PathVariable("mlModel") String modelType,
                                              @PathVariable("period") String period,
                                              @PathVariable("compName") String compName) {
        MLmodel mLmodel;
        Object[][] forecastedData;

        mLmodel = getMLmodelType(modelType);

        List<StockEntity> existingStk = stockService.listAllStocks();
        boolean found = false;

        for (StockEntity stock : existingStk) {
            if (stock.getSymbol().equals(ticker)) {
                found = true;
            }
        }

        if (!found) {
            alphaVantage.addDaily(ticker,compName);
            alphaVantage.addWeekly(ticker,compName);
            alphaVantage.addMonthly(ticker,compName);
        }

        forecastedData = getDataForForecast(period,ticker,mLmodel);

        return new ResponseEntity<Object>(forecastedData, HttpStatus.OK);
    }

    @GetMapping("/RMSPE/{period}/{mlModel}")
    public ResponseEntity<Double> getRMSPE(@PathVariable("mlModel") String modelType,
                                     @PathVariable("period") String period) {
        double errorRate = 0.0;
        DecimalFormat df = new DecimalFormat("#.##");

        MLmodel mLmodel;
        mLmodel = getMLmodelType(modelType);

        switch (period) {
            case "daily":
                errorRate = dailyTimeseriesService.getRMSE(mLmodel);
                break;
            case "weekly":
                errorRate = weeklyTimeseriesService.getRMSE(mLmodel);
                break;
            case "monthly":
                errorRate = monthlyTimeseriesService.getRMSE(mLmodel);
                break;
        }

        System.out.println("RMSE error rate for Gaussian Processes: " + errorRate);
        double hundred = 100.00;

        String error = String.valueOf(errorRate);
        double newError = Double.parseDouble(error);
        double percent = hundred - newError;

        percent = Double.parseDouble(df.format(percent));

        if (percent < 0.0 || percent > 100) {
            percent = 0;
        }

        return new ResponseEntity<Double>(percent, HttpStatus.OK);
    }

    private MLmodel getMLmodelType(String modelType) {
        MLmodel mLmodel;
        switch (modelType) {
            case "SVR":
                mLmodel = MLmodel.SVR;
                break;
            case "GAUSSIAN_PROCESS":
                mLmodel = MLmodel.GAUSSIAN_PROCESS;
                break;
            case "LINEAR_REGRESSION":
                mLmodel = MLmodel.LINEAR_REGRESSION;
                break;
            case "NEURAL_NETWORK":
                mLmodel = MLmodel.NEURAL_NETWORK;
                break;
            case "KNN":
                mLmodel = MLmodel.KNN;
                break;
            case "DECISION_TREE":
                mLmodel = MLmodel.DECISION_TREE;
                break;
            default:
                mLmodel = MLmodel.GAUSSIAN_PROCESS;
                break;
        }
        return mLmodel;
    }

    private Object[][] getDataForForecast(String period, String ticker, MLmodel mLmodel) {
        Object[][] forecastedData = null;

        switch (period) {
            case "daily":
                System.out.println("EXECUTED DAILY");
                forecastedData = dailyTimeseriesService.getTimeseriesForecast(ticker,mLmodel);
                break;
            case "weekly":
                System.out.println("EXECUTED WEEKLY");
                forecastedData = weeklyTimeseriesService.getTimeseriesForecast(ticker,mLmodel);
                break;
            case "monthly":
                System.out.println("EXECUTED MONTHLY");
                forecastedData = monthlyTimeseriesService.getTimeseriesForecast(ticker,mLmodel);
                break;
        }

        return forecastedData;
    }

}
