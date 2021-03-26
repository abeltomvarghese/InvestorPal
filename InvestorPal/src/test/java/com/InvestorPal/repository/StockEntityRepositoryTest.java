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
        stockEntityRepository.save(stockEntityOf("International Business Machines Corporation", "IBM"));
    }

    @Test
    void findBySymbolReturnsStock() {
        final StockEntity expectedEntity = stockEntityRepository.save(stockEntityOf("J P Morgan Chase & Co", "JPM"));

        final Optional<StockEntity> actualEntity = stockEntityRepository.findById(expectedEntity.getSymbol());

        /*assertThat(actualEntity)
                .usingFieldByFieldValueComparator()
                .hasValue(expectedEntity);*/

        assertEquals(expectedEntity.getName(), actualEntity.get().getName());
        assertEquals(expectedEntity.getSymbol(), actualEntity.get().getSymbol());
    }

    @Test
    void FindBySymbolQueryTest() {
        stockEntityRepository.save(stockEntityOf("J P Morgan Chase & Co","JPM"));

        StockEntity testStock = stockEntityRepository.findBySymbolIgnoreCase("jpm");
        assertEquals("JPM",testStock.getSymbol());
    }

    @Test
    void FindStockByNameTest() {
        stockEntityRepository.save(stockEntityOf("J P Morgan Chase & Co","JP"));

        StockEntity stockEntity = stockEntityRepository.findByNameIgnoreCase("j p morgan chase & co");
        assertEquals("J P Morgan Chase & Co", stockEntity.getName());
    }

}