package com.InvestorPal.entity;

import static org.apache.commons.lang3.Validate.notNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "weekly_timeseries")
public class WeeklyTimeseries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "weekly_symbol")
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
    private Double adjusted_close;

    @Column(nullable = false, name = "volume")
    private Long volume;

    @Column(nullable = false, name = "dividend_amount")
    private Double dividend_amount;

    @Column(nullable = false, name = "cobdate_partition")
    private String cobdate_partition;

    public WeeklyTimeseries() {
    }

    private WeeklyTimeseries(WeeklyBuilder weeklyBuilder) {
        this.cobdate_partition = weeklyBuilder.cobdate_partition;
        this.open = weeklyBuilder.open;
        this.high = weeklyBuilder.high;
        this.low = weeklyBuilder.low;
        this.close = weeklyBuilder.close;
        this.adjusted_close = weeklyBuilder.adjusted_close;
        this.volume = weeklyBuilder.volume;
        this.dividend_amount = weeklyBuilder.dividend_amount;
        this.symbol = weeklyBuilder.weekly_symbol;
    }

    public static WeeklyBuilder builder() {return new WeeklyBuilder();}

    public static class WeeklyBuilder {
        private String cobdate_partition;
        private Double open;
        private Double high;
        private Double low;
        private Double close;
        private Double adjusted_close;
        private Long volume;
        private Double dividend_amount;
        private String weekly_symbol;

        public WeeklyBuilder withCobdatePartition(final String cobdatePartition) {
            this.cobdate_partition = cobdatePartition;
            return this;
        }

        public WeeklyBuilder withWeeklySymbol(final String symbol) {
            this.weekly_symbol = symbol;
            return this;
        }
        public WeeklyBuilder withOpen(final Double open) {
            this.open = open;
            return this;
        }

        public WeeklyBuilder withHigh(final Double high) {
            this.high = high;
            return this;
        }

        public WeeklyBuilder withLow(final Double low) {
            this.low = low;
            return this;
        }

        public WeeklyBuilder withClose(final Double close) {
            this.close = close;
            return this;
        }

        public WeeklyBuilder withAdjustedClose(final Double adjustedClose) {
            this.adjusted_close = adjustedClose;
            return this;
        }

        public WeeklyBuilder withVolume(final Long volume) {
            this.volume = volume;
            return this;
        }

        public WeeklyBuilder withDividend(final Double dividend) {
            this.dividend_amount = dividend;
            return this;
        }

        public WeeklyTimeseries build() {
            notNull(cobdate_partition, "cobdate <WeeklyTimeseries> cannot be null");
            notNull(open, "Stock Open <WeeklyTimeseries> cannot be null");
            notNull(high, "Stock High <WeeklyTimeseries> cannot be null");
            notNull(low, "Stock Low <WeeklyTimeseries> cannot be null");
            notNull(close, "Stock Close <WeeklyTimeseries> cannot be null");
            notNull(adjusted_close, "Stock Adjusted_Close <WeeklyTimeseries> cannot be null");
            notNull(volume, "Stock Volume <WeeklyTimeseries> cannot be null");
            notNull(dividend_amount, "Stock Dividend_Amount <WeeklyTimeseries> cannot be null");
            notNull(weekly_symbol, "Stock Symbol <WeeklyTimeseries> cannot be null");

            return new WeeklyTimeseries(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getCobdate_partition() {
        return cobdate_partition;
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

    public Double getAdjusted_close() {
        return adjusted_close;
    }

    public Long getVolume() {
        return volume;
    }

    public Double getDividend_amount() {
        return dividend_amount;
    }

    public String getSymbol() {
        return symbol;
    }
}
