package com.InvestorPal.entity;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class StockEntityTest {
    @Nested
    class Builder {
        private static final String ANY = "any";
        @Test
        void buildReturnsEntity() {
            final StockEntity build = StockEntity.builder()
                    .withName("any name")
                    .withSymbol("any symbol")
                    .build();

            assertThat(build)
                    .hasFieldOrPropertyWithValue("name", "any name")
                    .hasFieldOrPropertyWithValue("symbol", "any symbol");
        }

        @Test
        void buildThrowsWhenSymbolIsEmpty() {
            assertThatNullPointerException()
                    .isThrownBy(() -> StockEntity.builder()
                            .withName(ANY)
                            .withSymbol(null)
                            .build())
                    .withMessage("symbol <StockEntity> must not be null");
        }

        @Test
        void buildThrowsWhenNameIsEmpty() {
            assertThatNullPointerException()
                    .isThrownBy(() -> StockEntity.builder()
                            .withSymbol(ANY)
                            .withName(null)
                            .build())
                    .withMessage("name <StockEntity> must not be null");
        }
    }
}