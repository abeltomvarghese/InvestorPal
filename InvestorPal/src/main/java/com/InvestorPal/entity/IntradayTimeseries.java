package com.InvestorPal.entity;
import static org.apache.commons.lang3.Validate.notNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "intraday_timeseries")
public class IntradayTimeseries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "intraday_symbol")
    private String symbol;

    @Column(nullable = false, name = "stock_open")
    private Double open;

    @Column(nullable = false, name = "high")
    private Double high;

    @Column(nullable = false, name = "low")
    private Double low;

    @Column(nullable = false, name = "stock_close")
    private Double close;

    @Column(nullable = false, name = "volume")
    private Long volume;

    @Column(nullable = false, name = "cobdate_partition")
    private String cobdate_partition;

    public IntradayTimeseries() {
    }

    private IntradayTimeseries(IntradayBuilder builder) {
        this.symbol = builder.intraday_symbol;
        this.open = builder.open;
        this.high = builder.high;
        this.low = builder.low;
        this.close = builder.close;
        this.volume = builder.volume;
        this.cobdate_partition = builder.cobdate_partition;
    }

    public static IntradayBuilder builder() {return new IntradayBuilder();}

    public static class IntradayBuilder{
        private String intraday_symbol;
        private Double open;
        private Double high;
        private Double low;
        private Double close;
        private Long volume;
        private String cobdate_partition;

        public IntradayBuilder withSymbol(final String symbol) {
            this.intraday_symbol = symbol;
            return this;
        }

        public IntradayBuilder withOpen(final Double open) {
            this.open = open;
            return this;
        }

        public IntradayBuilder withHigh(final Double high) {
            this.high = high;
            return this;
        }

        public IntradayBuilder withLow(final Double low) {
            this.low = low;
            return this;
        }

        public IntradayBuilder withClose(final Double close) {
            this.close = close;
            return this;
        }

        public IntradayBuilder withVolume(final Long volume) {
            this.volume = volume;
            return this;
        }

        public IntradayBuilder withCobdatePartition(final String cobdatePartition) {
            this.cobdate_partition = cobdatePartition;
            return this;
        }

        public IntradayTimeseries build() {
            notNull(intraday_symbol,"Symbol <IntradayTimeseries> must not be null");
            notNull(open, "Open <IntradayTimeseries> must not be null");
            notNull(high, "High <IntradayTimeseries> must not be null");
            notNull(low, "Low <IntradayTimeseries> must not be null");
            notNull(close, "Close <IntradayTimeseries> must not be null");
            notNull(volume, "Volume <IntradayTimeseries> must not be null");
            notNull(cobdate_partition,"Cobdate Partition <IntradayTimeseries> must not be null");

            return new IntradayTimeseries(this);
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

    public Long getVolume() {
        return volume;
    }

    public String getCobdate_partition() {
        return cobdate_partition;
    }
}
