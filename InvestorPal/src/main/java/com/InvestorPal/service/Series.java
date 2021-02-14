package com.InvestorPal.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Series {
    private double open;
    private double high;
    private double low;
    private double close;
    private double adjustedClose;
    private long volume;
    private double dividendAmount;

    @JsonCreator
    public Series(@JsonProperty(value = "1. open") final double open,
                  @JsonProperty(value = "2. high") final double high,
                  @JsonProperty(value = "3. low") final double low,
                  @JsonProperty(value = "4. close") final double close,
                  @JsonProperty(value = "5. adjusted close") final double adjustedClose,
                  @JsonProperty(value = "6. volume") final long volume,
                  @JsonProperty(value = "7. dividend amount") final double dividAmount) {

        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjustedClose = adjustedClose;
        this.volume = volume;
        this.dividendAmount = dividAmount;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public double getAdjustedClose() {
        return adjustedClose;
    }

    public void setAdjustedClose(double adjustedClose) {
        this.adjustedClose = adjustedClose;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getDividendAmount() {
        return dividendAmount;
    }

    public void setDividendAmount(double dividendAmount) {
        this.dividendAmount = dividendAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("open", open)
                .append("high", high)
                .append("low", low)
                .append("close", close)
                .append("volume", volume)
                .toString();
    }
}
