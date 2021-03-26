package com.InvestorPal.service;

import com.InvestorPal.entity.StockEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class StockServiceTest {

    private StockService stockService;

    @Autowired
    public StockServiceTest(StockService stockService) {
        this.stockService = stockService;
    }

    @Test
    public void canReturnAllStockTest() {
        List<StockEntity> stocks = stockService.listAllStocks();

        System.out.println(stocks.get(0).getName());
        assertNotNull(stocks);
    }
}
