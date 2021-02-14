package com.InvestorPal.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Daily extends Series {
    private double split_coefficient;

    @JsonCreator
    public Daily(@JsonProperty(value = "1. open") final double open,
                 @JsonProperty(value = "2. high") final double high,
                 @JsonProperty(value = "3. low") final double low,
                 @JsonProperty(value = "4. close") final double close,
                 @JsonProperty(value = "5. adjusted close") final double adjusted_close,
                 @JsonProperty(value = "6. volume") final Long volume,
                 @JsonProperty(value = "7. dividend amount") final double dividend_amount,
                 @JsonProperty(value = "8. split coefficient") final double split_coefficient) {

        super(open, high, low, close, adjusted_close, volume, dividend_amount);

        this.split_coefficient = split_coefficient;
    }

    public double getSplitCoefficient() {
        return this.split_coefficient;
    }
}
