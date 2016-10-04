package org.maj.analyzer.service;

import org.maj.analyzer.model.Decision;
import org.maj.analyzer.model.SData;
import org.maj.analyzer.model.Signal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shamik.majumdar
 */
@Component(value="stochastic")
public class StochasticOsc implements DecisionMaker {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StochasticOsc.class);

    private static final String NAME = "STOCHASTIC";
    private static final int LOOK_BACK_WINDOW = 14;
    private static final int PERIOD = 3;
    private static final double MIN_THRESHOLD = .20D;
    private static final double MAX_THRESHOLD = .80D;

    @Override
    public Decision takeDecision(List<SData> data) {

        Signal signal = Signal.HOLD;
        List<Double> percentK = new ArrayList<>();
        for (int i = 0; i < data.size() - LOOK_BACK_WINDOW; i++) {
            double high = Double.NEGATIVE_INFINITY;
            double low = Double.POSITIVE_INFINITY;
            for (int j=i; j < i+LOOK_BACK_WINDOW; j++) {
                if (data.get(j).getHigh() > high) {
                    high = data.get(j).getHigh();
                }
                if (data.get(j).getLow() < low){
                    low = data.get(j).getLow();
                }
            }

            percentK.add(high != low ? (data.get(i).getPrice() - low)/(high-low) : 0D);
        }

        List<Double> percentD = new ArrayList<>();
        for (int i=0; i <=percentK.size()-PERIOD;i++) {
            double v = 0D;
            for (int j=i; j < i + PERIOD; j++) {
                v += percentK.get(j);
            }
            percentD.add(v/PERIOD);
        }
        double recentK = percentK.get(0);
        double recentD = percentD.get(0);

        if (recentK >= MAX_THRESHOLD) {
            //its a signal of overbought
            signal = Signal.OVERBOUGHT;
            if (recentK < recentD) {
                //we might go down, sell it now
                signal = Signal.SELL;
            }

        }else if (recentK <= MIN_THRESHOLD) {
            signal = Signal.OVERSOLD;
            if (recentK > recentD) {
                signal = Signal.BUY; // time to buy as the price may go up
            }
        }

        LOGGER.info("{} decided to {}", NAME, signal);
        Decision decision = new Decision(signal,data.get(0).getSymbol(),"STOCHASTIC");
        decision.addStatistics(recentK);
        decision.addStatistics(recentD);
        return decision;
    }
}
