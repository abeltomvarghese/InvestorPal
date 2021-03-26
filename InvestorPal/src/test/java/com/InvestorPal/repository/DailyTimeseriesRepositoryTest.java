package com.InvestorPal.repository;

import com.InvestorPal.entity.DailyTimeseries;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DailyTimeseriesRepositoryTest {

    @Autowired
    DailyTimeseriesRepository dailyTimeseriesRepository;

    public DailyTimeseries createDailyTimeseries(String symbol, Double open, Double high, Double low, Double close,
                                                 Double adjusted_close, Long volume, Double dividend_amount, Double split_coefficient, String cobdate_partition) {

        return DailyTimeseries.builder()
                .withDailySymbol(symbol)
                .withOpenPrice(open)
                .withHighPrice(high)
                .withLowPrice(low)
                .withClosePrice(close)
                .withAdjustedClose(adjusted_close)
                .withVolume(volume)
                .withDividendAmount(dividend_amount)
                .withSplitCoefficient(split_coefficient)
                .withCobdatePartition(cobdate_partition)
                .build();

    }

    public DailyTimeseries initTimeseries() {
        return createDailyTimeseries("AAPL",24.5000,
                25.5000,20.5000,24.5000,24.5000,
                255555221L,2.3400,3.4000,"2020-02-04");
    }

    /*@Test
    public void canSaveTest() {
        dailyTimeseriesRepository.save(initTimeseries());
    }*/

    @Test
    public void findBySymbolTest() {
        dailyTimeseriesRepository.save(initTimeseries());

        List<DailyTimeseries> timeseries = dailyTimeseriesRepository.findBySymbolIgnoreCaseOrderByCobdatePartitionAsc("jpm");

        assertEquals("JPM",timeseries.get(0).getSymbol());
        assertEquals("1999-11-01",timeseries.get(0).getCobdatePartition());
        System.out.println("FIRST DATE: " + timeseries.get(0).getCobdatePartition());
    }
}
