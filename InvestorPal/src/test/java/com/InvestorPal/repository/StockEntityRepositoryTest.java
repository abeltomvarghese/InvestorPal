package com.InvestorPal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;


import com.InvestorPal.entity.StockEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;



@SpringBootTest
class StockEntityRepositoryTest {
    @Autowired
    private StockEntityRepository stockEntityRepository;

    private static StockEntity stockEntityOf(final String name, final String symbol) {
        return StockEntity.builder()
                .withName(name)
                .withSymbol(symbol)
                .build();
    }

    @Test
    void canSaveStock() {
        stockEntityRepository.save(stockEntityOf("Tesla", "TSLA"));
    }

    @Test
    void findBySymbolReturnsStock() {
        final StockEntity expectedEntity = stockEntityRepository.save(stockEntityOf("Apple Inc.", "AAPL"));

        final Optional<StockEntity> actualEntity = stockEntityRepository.findById(expectedEntity.getSymbol());

        /*assertThat(actualEntity)
                .usingFieldByFieldValueComparator()
                .hasValue(expectedEntity);*/

        assertEquals(expectedEntity.getName(), actualEntity.get().getName());
        assertEquals(expectedEntity.getSymbol(), actualEntity.get().getSymbol());
    }

    @Test
    void FindBySymbolQueryTest() {
        stockEntityRepository.save(stockEntityOf("Berkshire Hathaway","BRK.A"));

        StockEntity testStock = stockEntityRepository.findBySymbolIgnoreCase("brk.a");
        assertEquals("BRK.A",testStock.getSymbol());
    }

    @Test
    void FindStockByNameTest() {
        stockEntityRepository.save(stockEntityOf("Jupai Holdings","JP"));

        StockEntity stockEntity = stockEntityRepository.findByNameIgnoreCase("jupai hOldings");
        assertEquals("Jupai Holdings", stockEntity.getName());
    }

}