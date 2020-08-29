package com.InvestorPal.entity;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonthlyTimeseriesTest {
    @Nested
    class MonthlyBuilder {
        static final String STRING_CONSTANT = "just text";
        static final double DOUBLE_CONSTANT = 2000.2534;
        static final long LONG_CONSTANT = 3000;

        @Test
        void buildReturnsEntity() {
            MonthlyTimeseries mt = MonthlyTimeseries.builder()
                    .withSymbol(STRING_CONSTANT)
                    .withOpen(DOUBLE_CONSTANT)
                    .withHigh(DOUBLE_CONSTANT)
                    .withLow(DOUBLE_CONSTANT)
                    .withClose(DOUBLE_CONSTANT)
                    .withAdjustedClose(DOUBLE_CONSTANT)
                    .withVolume(LONG_CONSTANT)
                    .withDividendAmount(DOUBLE_CONSTANT)
                    .withCobdatePartition(STRING_CONSTANT)
                    .build();

            assertEquals("just text", mt.getSymbol());

        }

        @Test
        void buildThrowsWhenSymbolNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    MonthlyTimeseries.builder()
                            .withSymbol(null)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Symbol <MonthlyTimeseries> must not be null");
        }

        @Test
        void buildThrowsWhenOpenNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    MonthlyTimeseries.builder()
                            .withSymbol(STRING_CONSTANT)
                            .withOpen(null)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("open <MonthlyTimeseries> must not be null");
        }

        @Test
        void buildThrowsWhenHighNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    MonthlyTimeseries.builder()
                            .withSymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(null)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("high <MonthlyTimeseries> must not be null");
        }

        @Test
        void buildThrowsWhenLowNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    MonthlyTimeseries.builder()
                            .withSymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(null)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("low <MonthlyTimeseries> must not be null");
        }

        @Test
        void buildThrowsWhenCloseNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    MonthlyTimeseries.builder()
                            .withSymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(null)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("close <MonthlyTimeseries> must not be null");
        }

        @Test
        void buildThrowsWhenAdjustedCloseNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    MonthlyTimeseries.builder()
                            .withSymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(null)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("adjusted close <MonthlyTimeseries> must not be null");
        }

        @Test
        void buildThrowsWhenVolumeNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    MonthlyTimeseries.builder()
                            .withSymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(null)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("volume <MonthlyTimeseries> must not be null");
        }

        @Test
        void buildThrowsWhenDividendNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    MonthlyTimeseries.builder()
                            .withSymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(null)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("dividend amount <MonthlyTimeseries> must not be null");
        }

        @Test
        void buildThrowsWhenSplitNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    MonthlyTimeseries.builder()
                            .withSymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividendAmount(DOUBLE_CONSTANT)
                            .withCobdatePartition(null)
                            .build()).withMessage("cobdate partition <MonthlyTimeseries> must not be null");
        }

    }
}
