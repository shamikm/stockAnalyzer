package org.maj.analyzer.service;

import org.maj.analyzer.model.Decision;
import org.maj.analyzer.model.SData;
import org.maj.analyzer.model.Signal;
import org.maj.analyzer.utility.DataSortUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author shamik.majumdar
 */
@Component(value = "rsi")
public class RSIDecisionMaker implements DecisionMaker {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RSIDecisionMaker.class);
    private static final String NAME = "RSI";
    private static final int LOOKBACK_WINDOW = 14;
    /**
     * Steps to calculate :
     *  1: Sort the data in ascending order
     *  2: calculate first 14 days data
     *  3: Perform smoothing average since then
     * @param data
     * @return
     */
    @Override
    public Decision takeDecision(List<SData> data) {
        List<SData> series = new ArrayList<>(data);
        Collections.sort(series,new DataSortUtility(false));
        List<Double> change = new ArrayList<>();
        IntStream.range(0,LOOKBACK_WINDOW+1)
            .forEach(idx -> {
                if (idx == 0) {
                    change.add(0D);
                }else {
                    change.add(series.get(idx).getPrice() - series.get(idx-1).getPrice());
                }
            });

        List<Double> rsi = new ArrayList<>();
        List<Double> avgLossesList = new ArrayList<>();
        List<Double> avgGainsList = new ArrayList<>();
        IntStream.range(LOOKBACK_WINDOW+1,series.size())
                .forEach(idx-> {
                    double avgLosses = 0;
                    double avgGains = 0;
                    if (idx == LOOKBACK_WINDOW+1) {
                        double gains = change.stream().filter(a -> a > 0).mapToDouble(Double::doubleValue).sum();
                        double losses = change.stream().filter(a -> a < 0).map(a -> -a).mapToDouble(Double::doubleValue).sum();
                        avgGains = gains/LOOKBACK_WINDOW;
                        avgGainsList.add(avgGains);
                        avgLosses = losses/LOOKBACK_WINDOW;
                        avgLossesList.add(avgLosses);
                    }else {
                        double c = series.get(idx).getPrice() - series.get(idx-1).getPrice();
                        avgGains = ((avgGainsList.get(avgGainsList.size()-1) * (LOOKBACK_WINDOW -1)) +
                                (c > 0 ? c : 0) ) / LOOKBACK_WINDOW;
                        avgGainsList.add(avgGains);
                        avgLosses = ((avgLossesList.get(avgLossesList.size()-1) * (LOOKBACK_WINDOW -1)) +
                                (c < 0 ? -c : 0) ) / LOOKBACK_WINDOW;
                        avgLossesList.add(avgLosses);
                    }
                    double rs = avgLosses == 0 ? 0 : avgGains/avgLosses;
                    rsi.add(100 - 100/(1+rs));
                });

        Signal signal = Signal.HOLD;
        double latestRSI = rsi.get(rsi.size()-1);
        if (latestRSI >= 70) {
            signal = Signal.OVERBOUGHT;
        }else if (latestRSI <= 30){
            signal = Signal.OVERSOLD;
        }
        Decision decision = new Decision(signal,series.get(0).getSymbol(),NAME);

        return decision;
    }
}
