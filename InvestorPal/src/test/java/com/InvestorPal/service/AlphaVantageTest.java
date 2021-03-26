package com.InvestorPal.service;

import com.InvestorPal.entity.StockEntity;
import com.InvestorPal.repository.StockEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AlphaVantageTest {

    @Autowired
    AlphaVantage alphaVantage;

    @Autowired
    StockEntityRepository stockEntityRepository;

    private String getTestCompanyTicker() {
        return "BAC";
    }

    private String getTestCompanyName() {
        return "Bank of America Corp";
    }

    @Test
    public void saveDailyData() {
        boolean dailyResult = alphaVantage.addDaily(getTestCompanyTicker(), getTestCompanyName());

        assertEquals(true,dailyResult);
    }

    @Test
    public void saveWeeklyData() {
        boolean weeklyResult = alphaVantage.addWeekly(getTestCompanyTicker(), getTestCompanyName());

        assertEquals(true,weeklyResult);
    }

    @Test
    public void saveMonthlyData() {
        boolean monthlyResult = alphaVantage.addMonthly(getTestCompanyTicker(), getTestCompanyName());

        assertEquals(true, monthlyResult);
    }

    @Test
    public void saveIntoStockTable() {
        StockEntity stockEntity = stockEntityRepository.findBySymbolIgnoreCase(getTestCompanyTicker());

        assertNotNull(stockEntity);
    }
}