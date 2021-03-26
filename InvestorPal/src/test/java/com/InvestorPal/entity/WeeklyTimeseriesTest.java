package com.InvestorPal.entity;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeeklyTimeseriesTest {
    @Nested
    class WeeklyBuilder {
        static final String STRING_CONSTANT = "just text";
        static final double DOUBLE_CONSTANT = 2000.2534;
        static final long LONG_CONSTANT = 3000;

        @Test
        void buildReturnsEntity() {
            WeeklyTimeseries wt = WeeklyTimeseries.builder()
                    .withWeeklySymbol(STRING_CONSTANT)
                    .withOpen(DOUBLE_CONSTANT)
                    .withHigh(DOUBLE_CONSTANT)
                    .withLow(DOUBLE_CONSTANT)
                    .withClose(DOUBLE_CONSTANT)
                    .withAdjustedClose(DOUBLE_CONSTANT)
                    .withVolume(LONG_CONSTANT)
                    .withDividend(DOUBLE_CONSTANT)
                    .withCobdatePartition(STRING_CONSTANT)
                    .build();

            assertEquals("just text", wt.getSymbol());

        }

        @Test
        void buildThrowsWhenSymbolNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    WeeklyTimeseries.builder()
                            .withWeeklySymbol(null)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividend(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Stock Symbol <WeeklyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenOpenNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    WeeklyTimeseries.builder()
                            .withWeeklySymbol(STRING_CONSTANT)
                            .withOpen(null)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividend(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Stock Open <WeeklyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenHighNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    WeeklyTimeseries.builder()
                            .withWeeklySymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(null)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividend(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Stock High <WeeklyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenLowNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    WeeklyTimeseries.builder()
                            .withWeeklySymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(null)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividend(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Stock Low <WeeklyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenCloseNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    WeeklyTimeseries.builder()
                            .withWeeklySymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(null)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividend(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Stock Close <WeeklyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenAdjustedCloseNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    WeeklyTimeseries.builder()
                            .withWeeklySymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(null)
                            .withVolume(LONG_CONSTANT)
                            .withDividend(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Stock Adjusted_Close <WeeklyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenVolumeNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    WeeklyTimeseries.builder()
                            .withWeeklySymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(null)
                            .withDividend(DOUBLE_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Stock Volume <WeeklyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenDividendNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    WeeklyTimeseries.builder()
                            .withWeeklySymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividend(null)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Stock Dividend_Amount <WeeklyTimeseries> cannot be null");
        }

        @Test
        void buildThrowsWhenSplitNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    WeeklyTimeseries.builder()
                            .withWeeklySymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withAdjustedClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withDividend(DOUBLE_CONSTANT)
                            .withCobdatePartition(null)
                            .build()).withMessage("cobdate <WeeklyTimeseries> cannot be null");
        }

    }
}

