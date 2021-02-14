package com.InvestorPal.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Weekly extends Series {

    @JsonCreator
    public Weekly(@JsonProperty(value = "1. open") final double open,
                  @JsonProperty(value = "2. high") final double high,
                  @JsonProperty(value = "3. low") final double low,
                  @JsonProperty(value = "4. close") final double close,
                  @JsonProperty(value = "5. adjusted close") final double adjusted_close,
                  @JsonProperty(value = "6. volume") final long volume,
                  @JsonProperty(value = "7. dividend amount") final double dividend_amount) {

        super(open, high, low, close, adjusted_close, volume, dividend_amount);
    }
}
