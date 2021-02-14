package com.InvestorPal.service;

import java.time.LocalDate;
import java.util.Map;

public interface TimeSeries<S extends Series> {
    Metadata getMetaData();
    Map<LocalDate,S> getTimeSeriesAPI();
}
