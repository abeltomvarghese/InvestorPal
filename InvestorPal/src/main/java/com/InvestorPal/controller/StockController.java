package com.InvestorPal.controller;

import com.InvestorPal.entity.StockEntity;
import com.InvestorPal.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/myList")
    public ResponseEntity<List<StockEntity>> getAllStocks() {
        List<StockEntity> list = stockService.listAllStocks();

        if (CollectionUtils.isEmpty(list)) {
            return new ResponseEntity<List<StockEntity>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<StockEntity>>(list, HttpStatus.OK);
    }
	
	
}
