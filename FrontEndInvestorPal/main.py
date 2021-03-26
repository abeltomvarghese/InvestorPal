import pandas as pd
import json
import requests
from bokeh.plotting import figure, output_file, show
from bokeh.layouts import gridplot, row, layout
from bokeh.models import Button


def forecastAPI(period, ticker, name,model):
    url_string = f'http://localhost:8080/stock/{period}/{ticker}/{name}/{model}'
    r = requests.get(url_string)

    json_data = r.json()
    str_data = json.dumps(json_data, indent=2)
    json_data = json.loads(str_data)

    df = pd.DataFrame.from_dict(json_data)

    return df

def getAccuracy(period, model):
    url_string = f'http://localhost:8080/stock/RMSPE/{period}/{model}'
    r = requests.get(url_string)
    json_data = r.json()
    str_data = json.dumps(json_data, indent=2)

    return str_data

def getAllEquities():
    stockList = {}

    r = requests.get("https://dumbstockapi.com/stock?exchanges=NYSE&exchanges=NASDAQ&exchanges=AMEX")
    json_data = r.json()

    for item in json_data:
        stockList[item["ticker"]] = item["name"]

    return stockList


def visualiseData(ticker, compName, modelType):
    print("Extracting Data...")

    output_file("index.html")
    df = forecastAPI("daily", ticker,compName, modelType)

    print("Visualising Data...")
    titleString = f'Stock Ticker: {ticker}, Company Name: {compName},  ML-Model: {modelType}'
    titleButton = Button(label=titleString, button_type="success", min_width=1500)


    accuracy = getAccuracy("daily", modelType)
    graphTitle = f'Daily Timeseries Graph  RMSPE: {accuracy}% Accuracy'

    dailyGraph = figure(
        title=graphTitle,
        x_axis_label="Cobdate Partition",
        y_axis_label="Price",
        x_range=df[0],
        plot_height=350,
        plot_width=750
    )
    dailyGraph.title.text_font_size = '20px'
    dailyGraph.line(df[0], df[1], legend_label="Actual Price", line_width=2, line_color="blue")
    dailyGraph.line(df[0], df[2], legend_label="Predicted Price", line_width=2, line_color="orange")

    dfW = forecastAPI("weekly", ticker,compName, modelType)

    accuracy = getAccuracy("weekly", modelType)
    graphTitle = f'Weekly Timeseries Graph  RMSPE: {accuracy}% Accuracy'

    weeklyGraph = figure(
        title=graphTitle,
        x_axis_label="Cobdate Partition",
        y_axis_label="Price",
        x_range=dfW[0],
        plot_height=350,
        plot_width=750
    )
    weeklyGraph.title.text_font_size = '20px'
    weeklyGraph.line(dfW[0], dfW[1], legend_label="Actual Price", line_width=2, line_color="blue")
    weeklyGraph.line(dfW[0], dfW[2], legend_label="Predicted Price", line_width=2, line_color="orange")

    dfM = forecastAPI("monthly", ticker, compName, modelType)

    accuracy = getAccuracy("monthly", modelType)
    graphTitle = f'Monthly Timeseries Graph  RMSPE: {accuracy}% Accuracy'

    monthlyGraph = figure(
        title=graphTitle,
        x_axis_label="Cobdate Partition",
        y_axis_label="Price",
        x_range=dfM[0],
        plot_width=1500,
        plot_height=350
    )
    monthlyGraph.title.text_font_size = '20px'
    monthlyGraph.line(dfM[0], dfM[1], legend_label="Actual Price", line_width=2, line_color="blue")
    monthlyGraph.line(dfM[0], dfM[2], legend_label="Predicted Price", line_width=2, line_color="orange")

    show(layout([
        [titleButton],
        [dailyGraph, weeklyGraph],
        [monthlyGraph]
    ]))

def validateMLModelInput():
    print("\n")
    print("===============================================")
    print("           **** MODEL SELECTION ****")
    print("===============================================")
    print("Please select one of the following ML Models: ")
    print("1. Gaussian Process")
    print("2. Linear Regression")
    print("3. SVR")
    print("4. KNN")
    print("5. Decision Trees")
    print("6. Neural Network")
    print("===============================================")
    mlModels = ['GAUSSIAN_PROCESS', 'LINEAR_REGRESSION', 'SVR', 'KNN','DECISION_TREE','NEURAL_NETWORK']
    notValid = True
    while (notValid):
        try:
            modelOption = int(input("Enter the Model Number: "))
            if modelOption > 6 or modelOption < 1:
                print("Not a valid Option.")
                continue
            else:
                return mlModels[modelOption-1]
        except ValueError:
            print("Not a valid Option.")
            continue


def validateTickerName(stockList):
    print("\n")
    print("===============================================")
    print("           **** STOCK SELECTION ****")
    print("===============================================")
    ticker = input("Enter a stock ticker: ")
    while (ticker not in stockList):
        print("Error! Ticker not recognised")
        ticker = input("Enter a stock ticker: ")
    return ticker,stockList[ticker]


if __name__ == '__main__':


    stockList = getAllEquities()

    executeFlag = True
    while(executeFlag):
        tickerObj = validateTickerName(stockList)
        print(f'Selection: {tickerObj}')
        modelOption = validateMLModelInput()

        visualiseData(tickerObj[0],tickerObj[1],modelOption)

    # df = forecastAPI("daily","BAC","Bank of America Corp","LINEAR_REGRESSION")






