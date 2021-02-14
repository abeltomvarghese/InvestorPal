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

    @Test
    public void saveDailyData() {
        boolean dailyResult = alphaVantage.addDaily("AXP", "American Express");

        assertEquals(true,dailyResult);
    }

    @Test
    public void saveWeeklyData() {
        boolean weeklyResult = alphaVantage.addWeekly("AXP", "American Express");

        assertEquals(true,weeklyResult);
    }

    @Test
    public void saveMonthlyData() {
        boolean monthlyResult = alphaVantage.addMonthly("AXP", "American Express");

        assertEquals(true, monthlyResult);
    }

    @Test
    public void saveIntoStockTable() {
        StockEntity stockEntity = stockEntityRepository.findBySymbolIgnoreCase("AXP");

        assertNotNull(stockEntity);
    }
}