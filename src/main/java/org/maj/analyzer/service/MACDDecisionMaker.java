package org.maj.analyzer.service;

import org.maj.analyzer.model.Decision;
import org.maj.analyzer.model.SData;
import org.maj.analyzer.model.Signal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation acccording to https://investmenttoolkit.wordpress.com/2014/10/12/calculate-macd-in-excel/
 * Created by shamikm78 on 9/24/16.
 */
@Component(value = "macd")
public class MACDDecisionMaker implements DecisionMaker {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MACDDecisionMaker.class);
    private static final String NAME = "MACD";
    private int window_26  = 26;
    private int window_12  = 12;
    private double alpha = 0.25;

    /**
     * Assume data is sorted by date with recent date at the highest
     * @param data
     * @return
     */
    @Override
    public Decision takeDecision(List<SData> data) {
        if (data == null || data.size() == 0 ) return null;
        List<Double> ema12List = calculateEMA(data,window_12,alpha);
        List<Double> ema26List = calculateEMA(data,window_26,alpha);
        List<Double> macds = IntStream.range(0,Math.min(ema12List.size(),ema26List.size()))
                .mapToDouble(i -> ema12List.get(i) - ema26List.get(i)).boxed()
                .collect(Collectors.toList());

        Signal signal = Signal.HOLD;
        //recent macd = the latest data point
        double finalMacd = macds.get(macds.size()-1);

        if (finalMacd < 0 ) {
            signal = Signal.SELL;
        }
        else if (finalMacd > 0){
            //TBD : investigate the trend
            signal = Signal.BUY;
        }

        LOGGER.info("{} decided to {}", NAME, signal);
        Decision decision = new Decision(signal,data.get(0).getSymbol(),NAME);
        decision.addStatistics(finalMacd);
        return decision;
    }

    private List<Double> calculateEMA(List<SData> data, int window, double alpha){
        List<Double> closing = new ArrayList<>();
        List<Double> result = new ArrayList<>();
        for (int i=data.size()-1; i >= 0 ; i --) {
            closing.add(data.get(i).getPrice());
        }
        double v = 0;
        for (int i=0; i < window ; i ++) {
            v += closing.get(i);
            result.add(0D);
        }
        result.add(v/window);
        for (int i=window +1 ; i < closing.size(); i++) {
            result.add((closing.get(i) * ((double)2 / (window+1))) + (result.get(result.size()-1) * (1 - ((double)2/(window+1)))));
        }
        return result;
    }
}
