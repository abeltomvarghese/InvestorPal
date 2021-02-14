package com.InvestorPal.repository;

import com.InvestorPal.entity.DailyTimeseries;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyTimeseriesRepository extends JpaRepository<DailyTimeseries, Long> {

    List<DailyTimeseries> findBySymbolIgnoreCase(String symbol);

    List<DailyTimeseries> findBySymbolIgnoreCaseOrderByCobdatePartitionAsc(String symbol);
}
