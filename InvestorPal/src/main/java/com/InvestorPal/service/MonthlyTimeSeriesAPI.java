package com.InvestorPal.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.Map;

public class MonthlyTimeSeriesAPI implements TimeSeries<Monthly> {

    private Metadata metadata;
    private Map<LocalDate, Monthly> timeSeries;

    @JsonCreator
    public MonthlyTimeSeriesAPI(@JsonProperty("Meta Data") final Metadata metadata,
                                @JsonProperty("Monthly Adjusted Time Series") final Map<LocalDate, Monthly> timeSeries) {

        this.metadata = metadata;
        this.timeSeries = timeSeries;

    }
    @Override
    public Metadata getMetaData() {
        return metadata;
    }

    @Override
    public Map<LocalDate, Monthly> getTimeSeriesAPI() {
        return this.timeSeries;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("metadata", metadata).append("timeSeries", timeSeries).toString();
    }

}
