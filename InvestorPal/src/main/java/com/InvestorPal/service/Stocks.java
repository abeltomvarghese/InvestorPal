package com.InvestorPal.service;

public interface Stocks {
    TimeSeries<Daily> getDaily(final String symbol);
    TimeSeries<Weekly> getWeekly(final String symbol);
    TimeSeries<Monthly> getMonthly(final String symbol);
    boolean addDaily(final String symbol, final String name);
    boolean addWeekly(final String symbol, final String name);
    boolean addMonthly(final String symbol, final String name);
    boolean saveDailyData(DailyTimeSeriesAPI dt, String symbol, String name);
    boolean saveWeeklyData(WeeklyTimeSeriesAPI wt, String symbol, String name);
    boolean saveMonthlyData(MonthlyTimeSeriesAPI mt, String symbol, String name);
}
