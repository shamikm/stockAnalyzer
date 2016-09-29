package org.maj.analyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

/**
 * @author shamik.majumdar
 */
public class SData {
    private final String symbol;
    private final LocalDate date;
    private final double price;
    private final double high;
    private final double low;

    public SData(String symbol, LocalDate date, double price) {
        this.symbol = symbol;
        this.date = date;
        this.price = price;
        this.high = this.price;
        this.low = this.price;
    }

    public SData(String symbol, LocalDate date, double price, double high, double low) {
        this.symbol = symbol;
        this.date = date;
        this.price = price;
        this.high = high;
        this.low = low;
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

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    @Override
    public String toString() {
        return "SData{" +
                "symbol='" + symbol + '\'' +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
