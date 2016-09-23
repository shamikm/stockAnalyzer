## Overview [![Build Status](https://travis-ci.org/shamikm/stockAnalyzer.svg?branch=master)](https://travis-ci.org/shamikm/stockAnalyzer)
This is a very simple implementation of stock analysis. It analyzes 2 years worth of daily data for a stock and predicts whether to BUY/HOLD/SELL.

## Details
It is a spring boot based project. Build it using gradle and run like the following

 ./gradlew build & java -jar build/libs/s-analyzer-0.0.1.jar	

This will by default run the app at the port 8080.

use curl to test it

   curl localhost:8080/index.html
   
## Current backlog
   * Add Analysis tab(below 'dashboard') in left menu showing details of various analysis run and their individual results
   * Add RSI, OBV, Slow Stochastics and Williams %R predictors
   * Add Twitter NLP using Stanford core -- in progress
   * Add news NLP using Stanford core -- in progress
   * Replace Yahoo with a more reliable source
   * Refactor backend code to allow different analysis to be added as plugins and also run in parallel
   * Pull data bout quaterly projections and revenues
   * Pull External data like PMI, unemployment, fed rates, USD and gold prices etc -- in progress
   * Add a home page to it where we shall have all top 10 winner/looser kind of details
   * Allow users to track their own list of stocks
   * From the list of stocks that they track, find like minded users and recommend other stocks to carry
   * Create a value index - show them their portfolio and if they should invest in one more or sell one of them. 
     Allow them to run what-if scenarios. What-if I sell this one vs that one today ?

## Demo Site
  [demo site on heroku](http://shamik-stock-analyzer.herokuapp.com/index.html)