package com.InvestorPal.service;

import com.InvestorPal.entity.StockEntity;
import com.InvestorPal.repository.StockEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class StockServiceImpl implements StockService{

    private StockEntityRepository stockEntityRepository;

    @Autowired
    public StockServiceImpl(StockEntityRepository stockEntityRepository) {
        this.stockEntityRepository = stockEntityRepository;
    }

    @Override
    public List<StockEntity> listAllStocks() {
        return stockEntityRepository.findAll();
    }
}
