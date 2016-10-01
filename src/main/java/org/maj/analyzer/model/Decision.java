package org.maj.analyzer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamikm78 on 9/24/16.
 */
public class Decision {
    private final Signal signal;
    private final String symbol;
    private final String algoName;
    private String details;
    private List<Double> statistics = new ArrayList<>();

    public Decision(Signal signal, String symbol, String algoName) {
        this.signal = signal;
        this.symbol = symbol;
        this.algoName = algoName;
    }

    public Signal getSignal() {
        return signal;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAlgoName() {
        return algoName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Double> getStatistics() {
        return statistics;
    }

    public void addStatistics(double d) {
        statistics.add(d);
    }
}
