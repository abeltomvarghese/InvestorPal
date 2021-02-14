package com.InvestorPal.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Metadata {
    private String information;
    private String symbol;
    private String lastRefreshed;

    private String timeZone;

    @JsonCreator
    public Metadata(@JsonProperty("1. Information") final String information,
                    @JsonProperty("2. Symbol") final String symbol,
                    @JsonProperty("3. Last Refreshed") final String lastRefreshed,
                    @JsonProperty("4. Time Zone") final String timeZone) {
        this.information = information;
        this.symbol = symbol;
        this.lastRefreshed = lastRefreshed;
        this.timeZone = timeZone;
    }

    public String getInformation() {
        return information;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getLastRefreshed() {
        return lastRefreshed;
    }

    public String getTimeZone() {
        return timeZone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("information", information)
                .append("symbol", symbol)
                .append("lastRefreshed", lastRefreshed)
                .append("timeZone", timeZone)
                .toString();
    }
}


