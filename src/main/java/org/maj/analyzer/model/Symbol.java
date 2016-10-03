package org.maj.analyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamikm78 on 9/16/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Symbol {
    private String symbol;
    private String title;
    private Signal signal;
    private List<StockDetails> stockDetailList = new ArrayList<>();
    private List<FxRecommendation> financeReco;
    private List<SData> rawData;


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

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public List<SData> getRawData() {
        return rawData;
    }

    public void setRawData(List<SData> rawData) {
        this.rawData = rawData;
    }

    public String getBullishPercent(){
        if (stockDetailList.size() > 0) {
            long c = stockDetailList.stream().filter(s -> s.getSentiment() == Sentiment.BULLISH).count();
            return String.format("%.2f%%", (double) c / stockDetailList.size() * 100);
        }else {
            return "0%";
        }
    }

    public String getBearishPercent(){
        if (stockDetailList.size() > 0) {
            long c = stockDetailList.stream().filter(s -> s.getSentiment() == Sentiment.BEARISH).count();
            return String.format("%.2f%%", (double) c / stockDetailList.size() * 100);
        }else {
            return "0%";
        }
    }

    public void addStockMessages(List<StockDetails> stockDetailList) {
        this.stockDetailList.addAll(stockDetailList);
    }

    public void setStockDetailList(List<StockDetails> stockDetailList) {
        this.stockDetailList = stockDetailList;
    }

    public List<FxRecommendation> getFinanceReco() {
        return financeReco;
    }

    public void setFinanceReco(List<FxRecommendation> financeReco) {
        this.financeReco = financeReco;
    }
}
