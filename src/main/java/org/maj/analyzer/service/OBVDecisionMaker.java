package org.maj.analyzer.service;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.maj.analyzer.model.Decision;
import org.maj.analyzer.model.SData;
import org.maj.analyzer.model.Signal;
import org.maj.analyzer.utility.DataSortUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * 1. If today's closing price is higher than yesterday's closing price, then: Current OBV = Previous OBV + today's volume
 * 2. If today's closing price is lower than yesterday's closing price, then: Current OBV = Previous OBV - today's volume
 * 3. If today's closing price equals yesterday's closing price, then: Current OBV = Previous OBV
 * Read more: On-Balance Volume (OBV) Definition | Investopedia http://www.investopedia.com/terms/o/onbalancevolume.asp#ixzz4MACyaZi4
 * Follow us: Investopedia on Facebook
 *
 * Created by shamikm78 on 10/4/16.
 */
@Component(value = "obv")
public class OBVDecisionMaker implements DecisionMaker {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(OBVDecisionMaker.class);

    private static final String NAME = "OBV";
    @Override
    public Decision takeDecision(final List<SData> data) {
        DataSortUtility dataSortUtility = new DataSortUtility(false);
        List<SData> series = new ArrayList<>(data);
        Collections.sort(series,dataSortUtility);
        List<Double> obvs = new ArrayList<>();
        for (int i=0; i < series.size(); i++) {
            if (i==0) {
                obvs.add(0D);
            }else {
                if (series.get(i).getPrice() > series.get(i-1).getPrice()) {
                    //Current OBV = Previous OBV + today's volume
                    obvs.add(obvs.get(i-1) + series.get(i).getVolume());
                }else if (series.get(i).getPrice() < series.get(i-1).getPrice()){
                    //Current OBV = Previous OBV - today's volume
                    obvs.add(obvs.get(i-1) - series.get(i).getVolume());
                }else {
                    //Current OBV = Previous OBV
                    obvs.add(obvs.get(i-1));
                }
            }
        }

        //Find the recent price trend
        Collections.reverse(series);
        Collections.reverse(obvs);

        List<Double> recentPrices = series.stream().limit(7).map(SData::getPrice).collect(Collectors.toList());
        List<Double> recetOBVs = obvs.stream().limit(7).collect(Collectors.toList());
        Collections.reverse(recentPrices);
        Collections.reverse(recetOBVs);

        SimpleRegression obvReg = new SimpleRegression();
        SimpleRegression priceReg = new SimpleRegression();

        IntStream.range(0,recetOBVs.size())
                .forEach(idx -> obvReg.addData(idx,recetOBVs.get(idx)));

        IntStream.range(0,recentPrices.size())
                .forEach(idx -> priceReg.addData(idx,recentPrices.get(idx)));

        /*
            If the price trend is up, and OBV is now dropping (bearish divergence), take a short position when the price breaks below its current trendline.
            Place a stop loss above the most recent swing higher in price. Hold the trade for as long as OBV confirms it, and the price is trending lower.
            Exit if the price breaks above its trendline.
         */
        Signal signal = Signal.HOLD;
        double pSlope = priceReg.getSlope();
        double oSlope = obvReg.getSlope();
        if (pSlope > 0 && oSlope < 0) {
            signal = Signal.BEARISH_DIVERGENCE;
        }else if (pSlope < 0 && oSlope > 0){
            signal = Signal.BULLISH_DIVERGENCE;
        }

        LOGGER.info("{} model decided to {}",NAME,signal);
        Decision decision = new Decision(signal,data.get(0).getSymbol(),NAME);

        return decision;
    }
}
