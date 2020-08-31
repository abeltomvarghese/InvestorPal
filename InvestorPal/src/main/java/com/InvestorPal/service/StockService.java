package com.InvestorPal.service;

import com.InvestorPal.entity.StockEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StockService {
    List<StockEntity> listAllStocks();
}
