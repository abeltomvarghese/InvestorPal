DROP TABLE IF EXISTS Daily_Timeseries;
CREATE TABLE Stock
(
  symbol    VARCHAR(10)     NOT NULL PRIMARY KEY,
  name      VARCHAR(255)    NOT NULL
);

CREATE TABLE Intraday_Timeseries
(
    id                  SERIAL          PRIMARY KEY,
    intraday_symbol     VARCHAR(10)     NOT NULL,
    stock_open          NUMERIC(10,4)   NOT NULL,
    high                NUMERIC(10,4)   NOT NULL,
    low                 NUMERIC(10,4)   NOT NULL,
    stock_close         NUMERIC(10,4)   NOT NULL,
    volume              BIGINT          NOT NULL,
    cobdate_partition   VARCHAR(10)     NOT NULL
);

DROP TABLE IF EXISTS Daily_Timeseries;
CREATE TABLE Daily_Timeseries
(
    id                  SERIAL          PRIMARY KEY,
    daily_symbol        VARCHAR(10)     NOT NULL,
    stock_open          NUMERIC(10,4)   NOT NULL,
    high                NUMERIC(10,4)   NOT NULL,
    low                 NUMERIC(10,4)   NOT NULL,
    stock_close         NUMERIC(10,4)   NOT NULL,
    adjusted_close      NUMERIC(10,4)   NOT NULL,
    volume              BIGINT          NOT NULL,
    dividend_amount     NUMERIC(10,4)   NOT NULL,
    split_coefficient   NUMERIC(10,4)   NOT NULL,
    cobdate_partition   VARCHAR(10)     NOT NULL
);

DROP TABLE IF EXISTS Weekly_Timeseries;
CREATE TABLE Weekly_Timeseries
(
    id                  SERIAL          PRIMARY KEY,
    weekly_symbol       VARCHAR(10)     NOT NULL,
    stock_open          NUMERIC(10,4)   NOT NULL,
    high                NUMERIC(10,4)   NOT NULL,
    low                 NUMERIC(10,4)   NOT NULL,
    stock_close         NUMERIC(10,4)   NOT NULL,
    adjusted_close      NUMERIC(10,4)   NOT NULL,
    volume              BIGINT          NOT NULL,
    dividend_amount     NUMERIC(10,4)   NOT NULL,
    cobdate_partition   VARCHAR(10)     NOT NULL
);

DROP TABLE IF EXISTS Monthly_Timeseries;
CREATE TABLE Monthly_Timeseries
(
    id                  SERIAL          PRIMARY KEY,
    monthly_symbol      VARCHAR(10)     NOT NULL,
    stock_open          NUMERIC(10,4)   NOT NULL,
    high                NUMERIC(10,4)   NOT NULL,
    low                 NUMERIC(10,4)   NOT NULL,
    stock_close         NUMERIC(10,4)   NOT NULL,
    adjusted_close      NUMERIC(10,4)   NOT NULL,
    volume              BIGINT          NOT NULL,
    dividend_amount     NUMERIC(10,4)   NOT NULL,
    cobdate_partition   VARCHAR(10)     NOT NULL
);