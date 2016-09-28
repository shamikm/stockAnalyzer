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

    public SData(String symbol, LocalDate date, double price) {
        this.symbol = symbol;
        this.date = date;
        this.price = price;
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

    @Override
    public String toString() {
        return "SData{" +
                "symbol='" + symbol + '\'' +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
