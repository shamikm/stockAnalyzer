package org.maj.analyzer.model;

import java.time.LocalDate;

/**
 * @author shamik.majumdar
 */
public class SData {
    private final String symbol;
    private final LocalDate date;
    private final double price;
    private  Double ema26;
    private  Double ema12;
    private  Double macd;

    public SData(String symbol, LocalDate date, double price) {
        this.symbol = symbol;
        this.date = date;
        this.price = price;
        this.ema26 = null;
        this.ema12 = null;
    }

    public String getSymbol() {
        return symbol;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public double getEma26() {
        return ema26 == null ? 0 : ema26;
    }

    public double getEma12() {
        return ema12 == null ? 0 : ema12;
    }

    public void setEma26(Double ema26) {
        this.ema26 = ema26;
    }

    public void setEma12(Double ema12) {
        this.ema12 = ema12;
    }

    public Double getMacd() {
        return macd == null ? 0 : macd;
    }

    public void setMacd(Double macd) {
        this.macd = macd;
    }

    @Override
    public String toString() {
        return "SData{" +
                "symbol='" + symbol + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", ema26=" + ema26 +
                ", ema12=" + ema12 +
                ", macd=" + macd +
                '}';
    }
}
