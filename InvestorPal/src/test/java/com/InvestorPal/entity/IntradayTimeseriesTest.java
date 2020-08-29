package com.InvestorPal.entity;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class IntradayTimeseriesTest {

    @Nested
    class IntradayBuilder {
        static final String STRING_CONSTANT = "text";
        static final long LONG_CONSTANT = 2000;
        static final double DOUBLE_CONSTANT = 4523.2356;

        @Test
        void buildReturnsEntity() {
            IntradayTimeseries it = IntradayTimeseries.builder()
                    .withSymbol(STRING_CONSTANT)
                    .withOpen(DOUBLE_CONSTANT)
                    .withHigh(DOUBLE_CONSTANT)
                    .withLow(DOUBLE_CONSTANT)
                    .withClose(DOUBLE_CONSTANT)
                    .withVolume(LONG_CONSTANT)
                    .withCobdatePartition(STRING_CONSTANT)
                    .build();

            Assertions.assertEquals("text", it.getSymbol());
        }

        @Test
        void buildThrowsWhenSymbolNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    IntradayTimeseries.builder()
                            .withSymbol(null)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Symbol <IntradayTimeseries> must not be null");
        }

        @Test
        void buildThrowsWhenOpenNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    IntradayTimeseries.builder()
                            .withSymbol(STRING_CONSTANT)
                            .withOpen(null)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Open <IntradayTimeseries> must not be null");
        }

        @Test
        void buildThrowsWhenHighNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    IntradayTimeseries.builder()
                            .withSymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(null)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("High <IntradayTimeseries> must not be null");
        }

        @Test
        void buildThrowsWhenLowNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    IntradayTimeseries.builder()
                            .withSymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(null)
                            .withClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Low <IntradayTimeseries> must not be null");
        }

        @Test
        void buildThrowsWhenCloseNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    IntradayTimeseries.builder()
                            .withSymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(null)
                            .withVolume(LONG_CONSTANT)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Close <IntradayTimeseries> must not be null");
        }

        @Test
        void buildThrowsWhenVolumeNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    IntradayTimeseries.builder()
                            .withSymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withVolume(null)
                            .withCobdatePartition(STRING_CONSTANT)
                            .build()).withMessage("Volume <IntradayTimeseries> must not be null");
        }

        @Test
        void buildThrowsWheCobdatePartitionNull() {
            assertThatNullPointerException().isThrownBy(() ->
                    IntradayTimeseries.builder()
                            .withSymbol(STRING_CONSTANT)
                            .withOpen(DOUBLE_CONSTANT)
                            .withHigh(DOUBLE_CONSTANT)
                            .withLow(DOUBLE_CONSTANT)
                            .withClose(DOUBLE_CONSTANT)
                            .withVolume(LONG_CONSTANT)
                            .withCobdatePartition(null)
                            .build()).withMessage("Cobdate Partition <IntradayTimeseries> must not be null");
        }
    }
}

