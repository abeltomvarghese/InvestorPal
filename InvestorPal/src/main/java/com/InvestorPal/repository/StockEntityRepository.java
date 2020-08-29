package com.InvestorPal.repository;

import com.InvestorPal.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockEntityRepository extends JpaRepository<StockEntity, String> {

    StockEntity findBySymbolIgnoreCase(String symbol);

    StockEntity findByNameIgnoreCase(String name);
}
