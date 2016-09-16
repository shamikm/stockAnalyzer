package org.maj.analyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamikm78 on 9/16/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Symbol {
    private String symbol;
    private String title;
    private Decision decision;
    List<StockDetails> stockDetailList = new ArrayList<>();

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addStockMessage(StockDetails stockDetails){
        this.stockDetailList.add(stockDetails);
    }

    public List<StockDetails> getStockDetailList() {
        return stockDetailList;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public String getBullishPercent(){
        if (stockDetailList.size() > 0) {
            long c = stockDetailList.stream().filter(s -> s.getSeniment() == Seniment.BULLISH).count();
            return String.format("%.2f%%", (double) c / stockDetailList.size());
        }else {
            return "0%";
        }
    }

    public String getBearishPercent(){
        if (stockDetailList.size() > 0) {
            long c = stockDetailList.stream().filter(s -> s.getSeniment() == Seniment.BEARISH).count();
            return String.format("%.2f%%", (double) c / stockDetailList.size());
        }else {
            return "0%";
        }
    }

}
