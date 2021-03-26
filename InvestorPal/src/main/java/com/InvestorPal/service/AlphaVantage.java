package com.InvestorPal.service;

import com.InvestorPal.entity.DailyTimeseries;
import com.InvestorPal.entity.MonthlyTimeseries;
import com.InvestorPal.entity.StockEntity;
import com.InvestorPal.entity.WeeklyTimeseries;
import com.InvestorPal.repository.DailyTimeseriesRepository;
import com.InvestorPal.repository.MonthlyTimeseriesRepository;
import com.InvestorPal.repository.StockEntityRepository;
import com.InvestorPal.repository.WeeklyTimeseriesRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Service
public class AlphaVantage implements Stocks, API {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlphaVantage.class);
    private final RestTemplate restTemplate;

    private DailyTimeseriesRepository dailyTimeseriesRepository;
    private WeeklyTimeseriesRepository weeklyTimeseriesRepository;
    private MonthlyTimeseriesRepository monthlyTimeseriesRepository;
    private StockEntityRepository stockEntityRepository;

    @Autowired
    public AlphaVantage(final RestTemplateBuilder restTemplateBuilder,
                        DailyTimeseriesRepository dailyTimeseriesRepository,
                        WeeklyTimeseriesRepository weeklyTimeseriesRepository,
                        MonthlyTimeseriesRepository monthlyTimeseriesRepository,
                        StockEntityRepository stockEntityRepository) {

        restTemplate = requireNonNull(restTemplateBuilder, "restTemplateBuilder must not be null")
                .rootUri(getEndpoint())
                .build();

        this.dailyTimeseriesRepository = dailyTimeseriesRepository;
        this.weeklyTimeseriesRepository = weeklyTimeseriesRepository;
        this.monthlyTimeseriesRepository = monthlyTimeseriesRepository;
        this.stockEntityRepository = stockEntityRepository;
    }


    @Override
    public boolean addDaily(final String symbol, final String name) {

        LOGGER.info("getDaily");
        LOGGER.info(format("root:\t%s", getEndpoint()));
        LOGGER.info(format("query:\t%s", getQuery("TIME_SERIES_DAILY_ADJUSTED", symbol)));

        DailyTimeSeriesAPI dt = getDaily(symbol);

        boolean dataSaved = saveDailyData(dt, symbol, name);
        boolean dataRetrieved = !(MapUtils.isEmpty(dt.getTimeSeriesAPI()));

        boolean returnFlag = dataSaved && dataRetrieved ? true : false;


        return returnFlag;
    }

    public DailyTimeSeriesAPI getDaily(final String symbol) {

        DailyTimeSeriesAPI dt = restTemplate.getForObject(getQuery("TIME_SERIES_DAILY_ADJUSTED", symbol), DailyTimeSeriesAPI.class);

        return dt;
    }

    public boolean saveDailyData(DailyTimeSeriesAPI dt, String symbol, String name) {

        String daily_symbol;
        double stock_open;
        double high;
        double low;
        double stock_close;
        double adjusted_close;
        Long volume;
        double dividend_amount;
        double split_coefficient;
        String cobdate_partition;

        Map<LocalDate, Daily> dailyData = dt.getTimeSeriesAPI();

        for (Map.Entry<LocalDate, Daily> entry : dailyData.entrySet()) {
            cobdate_partition = String.valueOf(entry.getKey());
            daily_symbol = symbol;
            stock_open = entry.getValue().getOpen();
            high = entry.getValue().getHigh();
            low = entry.getValue().getLow();
            stock_close = entry.getValue().getClose();
            adjusted_close = entry.getValue().getAdjustedClose();
            volume = entry.getValue().getVolume();
            dividend_amount = entry.getValue().getDividendAmount();
            split_coefficient = entry.getValue().getSplitCoefficient();



            DailyTimeseries dailyTimeseries = DailyTimeseries.builder().withCobdatePartition(cobdate_partition)
                    .withDailySymbol(daily_symbol)
                    .withOpenPrice(stock_open)
                    .withHighPrice(high)
                    .withLowPrice(low)
                    .withClosePrice(stock_close)
                    .withAdjustedClose(adjusted_close)
                    .withVolume(volume)
                    .withDividendAmount(dividend_amount)
                    .withSplitCoefficient(split_coefficient)
                    .build();
            dailyTimeseriesRepository.saveAndFlush(dailyTimeseries);
        }

        StockEntity stockEntity = StockEntity.builder().withName(name).withSymbol(symbol).build();
        stockEntityRepository.saveAndFlush(stockEntity);

        List<DailyTimeseries> list = dailyTimeseriesRepository.findBySymbolIgnoreCase(symbol);
        stockEntity = stockEntityRepository.findBySymbolIgnoreCase(symbol);

        boolean stockSaved = isNull(stockEntity) ? false : true;
        boolean dailyTSFlag = CollectionUtils.isEmpty(list) ? false : true;
        boolean returnFlag = dailyTSFlag && stockSaved;
        return returnFlag;
    }

    @Override
    public boolean addWeekly(final String symbol, final String name) {
        LOGGER.info("getWeekly");
        LOGGER.info(format("root:\t%s", getEndpoint()));
        LOGGER.info(format("query:\t%s", getQuery("TIME_SERIES_WEEKLY_ADJUSTED", symbol)));

        WeeklyTimeSeriesAPI wt = getWeekly(symbol);

        boolean dataSaved = saveWeeklyData(wt, symbol, name);
        boolean dataRetrieved = !(MapUtils.isEmpty(wt.getTimeSeriesAPI()));

        boolean returnFlag = dataSaved && dataRetrieved ? true : false;


        return returnFlag;

    }

    public WeeklyTimeSeriesAPI getWeekly(final String symbol) {

        WeeklyTimeSeriesAPI wt = restTemplate.getForObject(getQuery("TIME_SERIES_WEEKLY_ADJUSTED", symbol), WeeklyTimeSeriesAPI.class);

        return wt;
    }

    public boolean saveWeeklyData(WeeklyTimeSeriesAPI wt, String symbol, String name) {

        String weekly_symbol;
        double stock_open;
        double high;
        double low;
        double stock_close;
        double adjusted_close;
        Long volume;
        double dividend_amount;
        double split_coefficient;
        String cobdate_partition;

        Map<LocalDate, Weekly> weeklyData = wt.getTimeSeriesAPI();


        for (Map.Entry<LocalDate, Weekly> entry : weeklyData.entrySet()) {
            cobdate_partition = String.valueOf(entry.getKey());
            weekly_symbol = symbol;
            stock_open = entry.getValue().getOpen();
            high = entry.getValue().getHigh();
            low = entry.getValue().getLow();
            stock_close = entry.getValue().getClose();
            adjusted_close = entry.getValue().getAdjustedClose();
            volume = entry.getValue().getVolume();
            dividend_amount = entry.getValue().getDividendAmount();



            WeeklyTimeseries weeklyTimeseries = WeeklyTimeseries.builder().withCobdatePartition(cobdate_partition)
                    .withWeeklySymbol(weekly_symbol)
                    .withOpen(stock_open)
                    .withHigh(high)
                    .withLow(low)
                    .withClose(stock_close)
                    .withAdjustedClose(adjusted_close)
                    .withVolume(volume)
                    .withDividend(dividend_amount)
                    .build();
            weeklyTimeseriesRepository.saveAndFlush(weeklyTimeseries);
        }

        StockEntity stockEntity = StockEntity.builder().withName(name).withSymbol(symbol).build();
        stockEntityRepository.saveAndFlush(stockEntity);

        List<WeeklyTimeseries> list = weeklyTimeseriesRepository.findBySymbolIgnoreCase(symbol);
        stockEntity = stockEntityRepository.findBySymbolIgnoreCase(symbol);

        boolean stockSaved = isNull(stockEntity) ? false : true;
        boolean weeklyTSFlag = CollectionUtils.isEmpty(list) ? false : true;
        boolean returnFlag = stockSaved && weeklyTSFlag;
        return returnFlag;
    }

    @Override
    public boolean addMonthly(final String symbol, final String name) {
        LOGGER.info("getMonthly");
        LOGGER.info(format("root:\t%s", getEndpoint()));
        LOGGER.info(format("query:\t%s", getQuery("TIME_SERIES_MONTHLY_ADJUSTED", symbol)));

        MonthlyTimeSeriesAPI mt = getMonthly(symbol);

        boolean dataSaved = saveMonthlyData(mt, symbol, name);
        boolean dataRetrieved = !(MapUtils.isEmpty(mt.getTimeSeriesAPI()));

        boolean returnFlag = dataSaved && dataRetrieved ? true : false;


        return returnFlag;
    }


    public MonthlyTimeSeriesAPI getMonthly(final String symbol) {

        MonthlyTimeSeriesAPI mt = restTemplate.getForObject(getQuery("TIME_SERIES_MONTHLY_ADJUSTED", symbol), MonthlyTimeSeriesAPI.class);

        return mt;
    }

    public boolean saveMonthlyData(MonthlyTimeSeriesAPI mt, String symbol, String name) {

        String monthly_symbol;
        double stock_open;
        double high;
        double low;
        double stock_close;
        double adjusted_close;
        Long volume;
        double dividend_amount;
        double split_coefficient;
        String cobdate_partition;

        Map<LocalDate, Monthly> monthlyData = mt.getTimeSeriesAPI();

        for (Map.Entry<LocalDate, Monthly> entry : monthlyData.entrySet()) {
            cobdate_partition = String.valueOf(entry.getKey());
            monthly_symbol = symbol;
            stock_open = entry.getValue().getOpen();
            high = entry.getValue().getHigh();
            low = entry.getValue().getLow();
            stock_close = entry.getValue().getClose();
            adjusted_close = entry.getValue().getAdjustedClose();
            volume = entry.getValue().getVolume();
            dividend_amount = entry.getValue().getDividendAmount();



            MonthlyTimeseries monthlyTimeseries = MonthlyTimeseries.builder().withCobdatePartition(cobdate_partition)
                    .withSymbol(monthly_symbol)
                    .withOpen(stock_open)
                    .withHigh(high)
                    .withLow(low)
                    .withClose(stock_close)
                    .withAdjustedClose(adjusted_close)
                    .withVolume(volume)
                    .withDividendAmount(dividend_amount)
                    .build();
            monthlyTimeseriesRepository.saveAndFlush(monthlyTimeseries);
        }

        StockEntity stockEntity = StockEntity.builder().withName(name).withSymbol(symbol).build();
        stockEntityRepository.saveAndFlush(stockEntity);

        List<MonthlyTimeseries> list = monthlyTimeseriesRepository.findBySymbolIgnoreCase(symbol);
        stockEntity = stockEntityRepository.findBySymbolIgnoreCase(symbol);

        boolean stockSaved = isNull(stockEntity) ? false : true;
        boolean monthlyTSFlag = CollectionUtils.isEmpty(list) ? false : true;
        boolean returnFlag = stockSaved && monthlyTSFlag;
        return returnFlag;
    }

    private String getQuery(final String function, final String symbol) {
        return format("/query?function=%s&symbol=%s&outputsize=full&apikey=%s", function, symbol, getKey());
    }

    @Override
    public String getEndpoint() {
        return "https://www.alphavantage.co";
    }


    @Override
    public String getKey() {
        return "QJAFOYP3IJR5UZ00";
    }
}

