## Overview
This is a very simple implementation of stock analysis. It analyzes 2 years worth of daily data for a stock and predicts whether to BUY/HOLD/SELL.

## Details
It is a spring boot based project. Build it using gradle and run like the following
 ./gradlew build & java -jar build/libs/s-analyzer-0.0.1.jar	

This will by default run the app at the port 8080.

use curl to test it

   curl localhost:8080/AAPL
