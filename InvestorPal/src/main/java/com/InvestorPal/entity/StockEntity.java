package com.InvestorPal.entity;



import javax.persistence.*;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@Entity
@Table(name = "stock")
public class StockEntity {

    @Id
    @Column(nullable = false, name = "symbol")
    private String symbol;

    @Column(nullable = false, name = "name")
    private String name;

    public StockEntity() {
    }

    private StockEntity(final Builder builder) {
        this.symbol = builder.symbol;
        this.name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String symbol;
        private String name;


        public Builder withSymbol(final String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public StockEntity build() {
            notNull(symbol, "symbol <StockEntity> must not be null");
            notNull(name, "name <StockEntity> must not be null");

            return new StockEntity(this);
        }
    }

}
