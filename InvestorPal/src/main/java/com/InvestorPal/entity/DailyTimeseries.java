package com.InvestorPal.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import static org.apache.commons.lang3.Validate.notNull;

@Entity
@Table(name = "daily_timeseries")
public class DailyTimeseries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "daily_symbol")
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

    @Column(nullable = false, name = "split_coefficient")
    private Double splitCoefficient;

    @Column(nullable = false, name = "cobdate_partition")
    private String cobdatePartition;

    public DailyTimeseries() {
    }

    private DailyTimeseries(DailyBuilder dailyBuilder) {
        this.cobdatePartition = dailyBuilder.cobdate_partition;
        this.open = dailyBuilder.open;
        this.high = dailyBuilder.high;
        this.low = dailyBuilder.low;
        this.close = dailyBuilder.close;
        this.adjustedClose = dailyBuilder.adjusted_close;
        this.volume = dailyBuilder.volume;
        this.dividendAmount = dailyBuilder.dividend_amount;
        this.splitCoefficient = dailyBuilder.split_coefficient;
        this.symbol = dailyBuilder.daily_symbol;
    }

    public static DailyBuilder builder() {return new DailyBuilder();}

    public static class DailyBuilder {
        private String cobdate_partition;
        private Double open;
        private Double high;
        private Double low;
        private Double close;
        private Double adjusted_close;
        private Long volume;
        private Double dividend_amount;
        private Double split_coefficient;
        private String daily_symbol;

        public DailyBuilder withDailySymbol(final String dailySymbol) {
            this.daily_symbol = dailySymbol;
            return this;
        }

        public DailyBuilder withCobdatePartition(final String cobdatePartition) {
            this.cobdate_partition = cobdatePartition;
            return this;
        }

        public DailyBuilder withOpenPrice(final Double open) {
            this.open = open;
            return this;
        }

        public DailyBuilder withHighPrice(final Double high) {
            this.high = high;
            return this;
        }

        public DailyBuilder withLowPrice(final Double low) {
            this.low = low;
            return this;
        }

        public DailyBuilder withClosePrice(final Double close) {
            this.close = close;
            return this;
        }

        public DailyBuilder withAdjustedClose(final Double adjusted_close) {
            this.adjusted_close = adjusted_close;
            return this;
        }

        public DailyBuilder withVolume(final Long volume) {
            this.volume = volume;
            return this;
        }

        public DailyBuilder withDividendAmount(final Double dividend_amount) {
            this.dividend_amount = dividend_amount;
            return this;
        }

        public DailyBuilder withSplitCoefficient(final Double split_coefficient) {
            this.split_coefficient = split_coefficient;
            return this;
        }

        public DailyTimeseries build() {
            notNull(cobdate_partition, "cobdate partition <DailyTimeseries> cannot be null");
            notNull(open, "Open <DailyTimeseries> cannot be null");
            notNull(high, "High <DailyTimeseries> cannot be null");
            notNull(low, "Low <DailyTimeseries> cannot be null");
            notNull(close, "Close <DailyTimeseries> cannot be null");
            notNull(adjusted_close, "Adjusted Close <DailyTimeseries> cannot be null");
            notNull(volume, "Volume <DailyTimeseries> cannot be null");
            notNull(dividend_amount, "Dividend Amount <DailyTimeseries> cannot be null");
            notNull(split_coefficient, "Split Coefficient <DailyTimeseries> cannot be null");
            notNull(daily_symbol, "Daily Symbol <DailyTimeseries> cannot be null");

            return new DailyTimeseries(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getCobdatePartition() {
        return cobdatePartition;
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

    public Double getSplitCoefficient() {
        return splitCoefficient;
    }

    public String getSymbol() {
        return symbol;
    }
}