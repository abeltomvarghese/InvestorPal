package com.InvestorPal.repository;

import com.InvestorPal.entity.IntradayTimeseries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntradayTimeseriesRepository extends JpaRepository<IntradayTimeseries, Long> {

    List<IntradayTimeseries> findBySymbolIgnoreCase(String symbol);
}
