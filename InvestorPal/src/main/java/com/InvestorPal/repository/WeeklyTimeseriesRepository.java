package com.InvestorPal.repository;

import com.InvestorPal.entity.WeeklyTimeseries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeeklyTimeseriesRepository extends JpaRepository<WeeklyTimeseries, Long> {

    List<WeeklyTimeseries> findBySymbolIgnoreCase(String symbol);
}
