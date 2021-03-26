package com.InvestorPal.repository;

import com.InvestorPal.entity.MonthlyTimeseries;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MonthlyTimeseriesRepositoryTest {

    @Autowired
    MonthlyTimeseriesRepository monthlyTimeseriesRepository;

    private MonthlyTimeseries createTimeseries(String symbol, Double open,
                                               Double high, Double low,
                                               Double close, Double adjusted_close,
                                               Long volume, Double dividend_amount, String cobdate_partition) {
        return MonthlyTimeseries.builder()
                .withSymbol(symbol)
                .withOpen(open)
                .withHigh(high)
                .withLow(low)
                .withClose(close)
                .withAdjustedClose(adjusted_close)
                .withVolume(volume)
                .withDividendAmount(dividend_amount)
                .withCobdatePartition(cobdate_partition)
                .build();

    }

    private MonthlyTimeseries initTimeseries() {
        return createTimeseries("BA",25.1456,45.6598,78.2598,74.2145,23.5645,4589213L,45.2058,"2020-03-25");
    }

    @Test
    public void canSaveTest() {
        monthlyTimeseriesRepository.save(initTimeseries());
    }

    @Test
    public void findBySymbolTest(){
        monthlyTimeseriesRepository.save(initTimeseries());

        List<MonthlyTimeseries> monthlyTimeseriesList = monthlyTimeseriesRepository.findBySymbolIgnoreCase("bA");

        assertEquals("BA", monthlyTimeseriesList.get(0).getSymbol());
    }

}
