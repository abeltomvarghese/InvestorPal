package com.InvestorPal.repository;

import com.InvestorPal.entity.WeeklyTimeseries;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WeeklyTimeseriesRepositoryTest {

    @Autowired
    WeeklyTimeseriesRepository weeklyTimeseriesRepository;

    private WeeklyTimeseries createTimeseries(String symbol, Double open, Double high, Double low, Double close, Double adjusted_close, Long volume, Double dividend_amount, String cobdate_partition) {
        return WeeklyTimeseries.builder()
                .withWeeklySymbol(symbol)
                .withOpen(open)
                .withHigh(high)
                .withLow(low)
                .withClose(close)
                .withAdjustedClose(adjusted_close)
                .withVolume(volume)
                .withDividend(dividend_amount)
                .withCobdatePartition(cobdate_partition)
                .build();
    }

    private WeeklyTimeseries initTimeseries() {
        return createTimeseries("MMM",25.2304,56.1256,
                12.2314,24.2345,
                20.1245,12345897L,
                1.2025,"2019-05-12");
    }


    @Test
    public void canSaveTest(){
        weeklyTimeseriesRepository.save(initTimeseries());
    }

    @Test
    public void findBySymbolTest(){
        weeklyTimeseriesRepository.save(initTimeseries());

        List<WeeklyTimeseries> weeklyTimeseriesList = weeklyTimeseriesRepository.findBySymbolIgnoreCase("mMm");

        assertEquals("MMM", weeklyTimeseriesList.get(0).getSymbol());
    }
}

