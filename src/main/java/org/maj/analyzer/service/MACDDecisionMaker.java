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
 * Created by shamikm78 on 9/24/16.
 */
@Component(value = "macd")
public class MACDDecisionMaker implements DecisionMaker {
    private static final String NAME = "MACD";
    private int window_26  = 26;
    private int window_12  = 12;
    private double alpha = 0.25;

    @Override
    public Decision takeDecision(List<SData> data) {
        if (data == null || data.size() == 0 ) return null;
        List<Double> ema12List = calculateEMA(data,window_12,alpha);
        List<Double> ema26List = calculateEMA(data,window_26,alpha);
        List<Double> macds = IntStream.range(0,Math.min(ema12List.size(),ema26List.size()))
                .mapToDouble(i -> ema12List.get(i) - ema26List.get(i)).boxed()
                .collect(Collectors.toList());

        Signal signal = Signal.HOLD;
        double finalMacd = macds.get(macds.size() - 1);

        if (finalMacd < 0 ) {
            signal = Signal.SELL;
        }
        else if (finalMacd > 0){
            //TBD : investigate the trend
            signal = Signal.BUY;
        }

        return new Decision(signal,data.get(0).getSymbol(),NAME);
    }

    private List<Double> calculateEMA(List<SData> data, int window, double alpha){
        List<Double> result = new ArrayList<>();
        if (data.size() > window){
            for (int i=0; i <= data.size()-window; i++){
                double temp = 0;
                for (int j=i;j<i+window;j++){
                    double priceToday = data.get(j).getPrice();
                    temp = j == i ? priceToday : temp + alpha * (priceToday - temp);
                }
                result.add(temp);
            }
        }
        return result;
    }
}
