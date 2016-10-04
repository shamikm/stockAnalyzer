package org.maj.analyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author shamik.majumdar
 */
public class SData {
    private final String symbol;
    private final LocalDate date;
    private final double price;
    private final double high;
    private final double low;
    private String name;
    private String marketCap;
    private double volume;

    public SData(String symbol, LocalDate date, double price) {
        this.symbol = symbol;
        this.date = date;
        this.price = price;
        this.high = this.price;
        this.low = this.price;
        this.volume = 0;
    }

    public SData(String symbol, LocalDate date, double price, double high, double low,double vol) {
        this.symbol = symbol;
        this.date = date;
        this.price = price;
        this.high = high;
        this.low = low;
        this.volume = vol;
    }

    public String getSymbol() {
        return symbol;
    }

    @JsonIgnore
    public LocalDate getDate() {
        return date;
    }

    public String getDateISO() {
        return date.toString();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public double getVolume() {
        return volume;
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
