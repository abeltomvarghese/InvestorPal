package com.InvestorPal.service;

import com.InvestorPal.entity.StockEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface StockService {
    List<StockEntity> listAllStocks();
    StockEntity findBySymbolIgnoreCase(String ticker);
}
