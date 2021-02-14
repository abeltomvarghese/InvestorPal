package com.InvestorPal.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.Map;

public class DailyTimeSeriesAPI implements TimeSeries<Daily> {
    private DailyTSMetadata metadata;
    private Map<LocalDate, Daily> timeSeries;

    @JsonCreator
    public DailyTimeSeriesAPI(@JsonProperty(value = "Meta Data") final DailyTSMetadata metadata,
                           @JsonProperty(value = "Time Series (Daily)") final Map<LocalDate, Daily> timeSeries) {
        this.metadata = metadata;
        this.timeSeries = timeSeries;
    }


    @Override
    public DailyTSMetadata getMetaData() {
        return metadata;
    }

    @Override
    public Map<LocalDate, Daily> getTimeSeriesAPI() {
        return timeSeries;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("metadata", metadata)
                .append("timeSeries", timeSeries)
                .toString();
    }
}
