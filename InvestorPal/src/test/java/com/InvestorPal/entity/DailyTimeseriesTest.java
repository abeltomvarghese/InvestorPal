package com.InvestorPal.entity;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DailyTimeseriesTest {

    @Nested
    class DailyBuilder {
        static final String STRING_CONSTANT = "just text";
        static final double DOUBLE_CONSTANT = 2000.2534;
        static final long LONG_CONSTANT = 3000;

        @Test 
        void buildReturnsEntity() {
            DailyTimeseries dt = DailyTimeseries.builder()
                    .withDailySymbol(STRING_CONSTANT)
                    .withOpenPrice(DOUBLE_CONSTANT)
                    .withHighPrice(DOUBLE_CONSTANT)
                    .withLowPrice(DOUBLE_CONSTANT)
                    .withClosePrice(DOUBLE_CONSTANT)
                    .withAdjustedClose(DOUBLE_CONSTANT)
                    .withVolume(LONG_CONSTANT)
                    .withDividendAmount(DOUBLE_CONSTANT)
                    .withSplitCoefficient(DOUBLE_CONSTANT)
                    .withCobdatePartition(STRING_CONSTANT)
                    .build();

            assertEquals("just text", dt.getSymbol());

        }

        @Test
        void buildThrowsWhenSymbolNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    DailyTimeseries.builder()
                            .withDailySymbol(null)
                            .withOpenPrice(DOUBLE_CONSTANT)
                            .withHighPrice(DOUBLE_CONSTANT)
                            .withLowPrice(DOUBLE_CONSTANT)
                            .withClosePrice(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withSplitCoefficient(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Daily Symbol <DailyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenOpenNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    DailyTimeseries.builder()
                            .withDailySymbol(STRING_CONSTANT)
                            .withOpenPrice(null)
                            .withHighPrice(DOUBLE_CONSTANT)
                            .withLowPrice(DOUBLE_CONSTANT)
                            .withClosePrice(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withSplitCoefficient(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Open <DailyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenHighNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    DailyTimeseries.builder()
                            .withDailySymbol(STRING_CONSTANT)
                            .withOpenPrice(DOUBLE_CONSTANT)
                            .withHighPrice(null)
                            .withLowPrice(DOUBLE_CONSTANT)
                            .withClosePrice(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withSplitCoefficient(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("High <DailyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenLowNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    DailyTimeseries.builder()
                            .withDailySymbol(STRING_CONSTANT)
                            .withOpenPrice(DOUBLE_CONSTANT)
                            .withHighPrice(DOUBLE_CONSTANT)
                            .withLowPrice(null)
                            .withClosePrice(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withSplitCoefficient(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Low <DailyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenCloseNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    DailyTimeseries.builder()
                            .withDailySymbol(STRING_CONSTANT)
                            .withOpenPrice(DOUBLE_CONSTANT)
                            .withHighPrice(DOUBLE_CONSTANT)
                            .withLowPrice(DOUBLE_CONSTANT)
                            .withClosePrice(null)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withSplitCoefficient(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Close <DailyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenAdjustedCloseNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    DailyTimeseries.builder()
                            .withDailySymbol(STRING_CONSTANT)
                            .withOpenPrice(DOUBLE_CONSTANT)
                            .withHighPrice(DOUBLE_CONSTANT)
                            .withLowPrice(DOUBLE_CONSTANT)
                            .withClosePrice(DOUBLE_CONSTANT)
                            .withAdjustedClose(null)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withSplitCoefficient(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Adjusted Close <DailyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenVolumeNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    DailyTimeseries.builder()
                            .withDailySymbol(STRING_CONSTANT)
                            .withOpenPrice(DOUBLE_CONSTANT)
                            .withHighPrice(DOUBLE_CONSTANT)
                            .withLowPrice(DOUBLE_CONSTANT)
                            .withClosePrice(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(null)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withSplitCoefficient(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Volume <DailyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenDividendNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    DailyTimeseries.builder()
                            .withDailySymbol(STRING_CONSTANT)
                            .withOpenPrice(DOUBLE_CONSTANT)
                            .withHighPrice(DOUBLE_CONSTANT)
                            .withLowPrice(DOUBLE_CONSTANT)
                            .withClosePrice(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(null)
                            .withSplitCoefficient(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Dividend Amount <DailyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenSplitNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    DailyTimeseries.builder()
                            .withDailySymbol(STRING_CONSTANT)
                            .withOpenPrice(DOUBLE_CONSTANT)
                            .withHighPrice(DOUBLE_CONSTANT)
                            .withLowPrice(DOUBLE_CONSTANT)
                            .withClosePrice(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withSplitCoefficient(null)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Split Coefficient <DailyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenCobdatePartitionNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    DailyTimeseries.builder()
                            .withDailySymbol(STRING_CONSTANT)
                            .withOpenPrice(DOUBLE_CONSTANT)
                            .withHighPrice(DOUBLE_CONSTANT)
                            .withLowPrice(DOUBLE_CONSTANT)
                            .withClosePrice(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withSplitCoefficient(DOUBLE_CONSTANT)
                            .withCobdatePartition(null)
                            .build()).withMessage("cobdate partition <DailyTimeseries> cannot be null");
        }
    }
}
