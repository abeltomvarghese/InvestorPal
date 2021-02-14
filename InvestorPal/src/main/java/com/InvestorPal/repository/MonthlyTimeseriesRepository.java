package com.InvestorPal.repository;

import com.InvestorPal.entity.DailyTimeseries;
import com.InvestorPal.entity.MonthlyTimeseries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyTimeseriesRepository extends JpaRepository<MonthlyTimeseries, Long> {

    List<MonthlyTimeseries> findBySymbolIgnoreCase(String symbol);

    List<MonthlyTimeseries> findBySymbolIgnoreCaseOrderByCobdatePartitionAsc(String symbol);
}
