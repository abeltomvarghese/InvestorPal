package com.InvestorPal.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DailyTSMetadata extends Metadata {

    private String outputSize;

    public DailyTSMetadata(@JsonProperty("1. Information") final String information,
                           @JsonProperty("2. Symbol") final String symbol,
                           @JsonProperty("3. Last Refreshed") final String lastRefreshed,
                           @JsonProperty("4. Output Size") final String outputSize,
                           @JsonProperty("5. Time Zone") final String timeZone) {

        super(information, symbol, lastRefreshed, timeZone);
        this.outputSize = outputSize;
    }

    public String getOutputSize() {
        return outputSize;
    }
}

