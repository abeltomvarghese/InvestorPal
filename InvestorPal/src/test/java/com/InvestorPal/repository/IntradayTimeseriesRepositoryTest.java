package com.InvestorPal.repository;

import com.InvestorPal.entity.IntradayTimeseries;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IntradayTimeseriesRepositoryTest {

    @Autowired
    IntradayTimeseriesRepository intradayTimeseriesRepository;

    private IntradayTimeseries createTimeseries(String symbol, Double open,
                                                Double high, Double low,
                                                Double close, Long volume,
                                                String cobdate_partition) {
        return IntradayTimeseries.builder()
                .withSymbol(symbol)
                .withOpen(open)
                .withHigh(high)
                .withLow(low)
                .withClose(close)
                .withVolume(volume)
                .withCobdatePartition(cobdate_partition)
                .build();
    }

    private IntradayTimeseries initTimeseries() {

        return createTimeseries("AMZN", 24.0225,45.2054,65.0574,45.0231,2235478L,"2020-05-01");
    }


    @Test
    public void canSaveTest() {
        intradayTimeseriesRepository.save(initTimeseries());
    }

    @Test
    public void findBySymbol() {
        intradayTimeseriesRepository.save(initTimeseries());

        List<IntradayTimeseries> intradayTimeseriesList = intradayTimeseriesRepository.findBySymbolIgnoreCase("amzn");

        assertEquals("AMZN", intradayTimeseriesList.get(0).getSymbol());
    }
}
