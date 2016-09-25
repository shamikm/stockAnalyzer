package org.maj.analyzer.model;

/**
 * Created by shamikm78 on 9/24/16.
 */
public class Decision {
    private final Signal signal;
    private final String symbol;
    private final String algoName;
    private String details;

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
}
