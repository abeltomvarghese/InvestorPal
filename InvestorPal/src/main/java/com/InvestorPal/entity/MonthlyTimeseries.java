package com.InvestorPal.entity;

import static org.apache.commons.lang3.Validate.notNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "monthly_timeseries")
public class MonthlyTimeseries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "monthly_symbol")
    private String symbol;

    @Column(nullable = false, name = "stock_open")
    private Double open;

    @Column(nullable = false, name = "high")
    private Double high;

    @Column(nullable = false, name = "low")
    private Double low;

    @Column(nullable = false, name = "stock_close")
    private Double close;

    @Column(nullable = false, name = "adjusted_close")
    private Double adjustedClose;

    @Column(nullable = false, name = "volume")
    private Long volume;

    @Column(nullable = false, name = "dividend_amount")
    private Double dividendAmount;

    @Column(nullable = false, name = "cobdate_partition")
    private String cobdatePartition;

    public MonthlyTimeseries() {
    }

    private MonthlyTimeseries(MonthlyBuilder builder) {
        this.symbol = builder.symbol;
        this.open = builder.open;
        this.high = builder.high;
        this.low = builder.low;
        this.close = builder.close;
        this.adjustedClose = builder.adjusted_close;
        this.volume = builder.volume;
        this.dividendAmount = builder.dividend_amount;
        this.cobdatePartition = builder.cobdate_partition;
    }

    public static MonthlyBuilder builder() {return new MonthlyBuilder();}

    public static class MonthlyBuilder {
        private String symbol;
        private Double open;
        private Double high;
        private Double low;
        private Double close;
        private Double adjusted_close;
        private Long volume;
        private Double dividend_amount;
        private String cobdate_partition;

        public MonthlyBuilder withSymbol(final String symbol) {
            this.symbol = symbol;

            return this;
        }

        public MonthlyBuilder withOpen(final Double open) {
            this.open = open;

            return this;
        }

        public MonthlyBuilder withHigh(final Double high) {
            this.high = high;

            return this;
        }

        public MonthlyBuilder withLow(final Double low) {
            this.low = low;

            return this;
        }

        public MonthlyBuilder withClose(final Double close) {
            this.close = close;

            return this;
        }

        public MonthlyBuilder withAdjustedClose(final Double adjusted_close) {
            this.adjusted_close = adjusted_close;

            return this;
        }

        public MonthlyBuilder withVolume(final Long volume) {
            this.volume = volume;

            return this;
        }

        public MonthlyBuilder withDividendAmount(final Double dividend_amount) {
            this.dividend_amount = dividend_amount;

            return this;
        }

        public MonthlyBuilder withCobdatePartition(final String cobdatePartition) {
            this.cobdate_partition = cobdatePartition;

            return this;
        }

        public MonthlyTimeseries build() {
            notNull(symbol, "Symbol <MonthlyTimeseries> must not be null");
            notNull(open, "open <MonthlyTimeseries> must not be null");
            notNull(high, "high <MonthlyTimeseries> must not be null");
            notNull(low, "low <MonthlyTimeseries> must not be null");
            notNull(close, "close <MonthlyTimeseries> must not be null");
            notNull(adjusted_close, "adjusted close <MonthlyTimeseries> must not be null");
            notNull(volume, "volume <MonthlyTimeseries> must not be null");
            notNull(dividend_amount, "dividend amount <MonthlyTimeseries> must not be null");
            notNull(cobdate_partition, "cobdate partition <MonthlyTimeseries> must not be null");

            return new MonthlyTimeseries(this);
        }
    }


    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getOpen() {
        return open;
    }

    public Double getHigh() {
        return high;
    }

    public Double getLow() {
        return low;
    }

    public Double getClose() {
        return close;
    }

    public Double getAdjustedClose() {
        return adjustedClose;
    }

    public Long getVolume() {
        return volume;
    }

    public Double getDividendAmount() {
        return dividendAmount;
    }

    public String getCobdatePartition() {
        return cobdatePartition;
    }
}
