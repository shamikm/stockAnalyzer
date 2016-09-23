package org.maj.analyzer.service;

import org.maj.analyzer.ingest.DataLoader;
import org.maj.analyzer.ingest.StockDetailsLoader;
import org.maj.analyzer.model.Decision;
import org.maj.analyzer.model.SData;
import org.maj.analyzer.model.Symbol;
import org.maj.analyzer.rule.EvaluateStock;
import org.maj.analyzer.transformer.DeriveMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by shamikm78 on 9/14/16.
 */
@Component
public class AnalyzeService {
    @Autowired
    private DataLoader dataLoader;
    @Autowired
    private DeriveMetrics deriveMetrics;
    @Autowired
    private EvaluateStock evaluateStock;
    @Autowired
    private FinancialSentimentService financialSentimentService;

    private List<StockDetailsLoader> detailsLoader;

    public void setDetailsLoader(List<StockDetailsLoader> detailsLoader) {
        this.detailsLoader = detailsLoader;
    }

    public Symbol takeADecision(String symbol){
        List<SData> dataList = dataLoader.loadData(symbol);
        dataList = deriveMetrics.transform(dataList);
        Decision decision = evaluateStock.evaluate(dataList);

        Symbol stockSymbolData = new Symbol();
        detailsLoader.forEach(loader -> {
            Symbol s = loader.loadStockDetails(symbol);
            if (stockSymbolData.getSymbol() == null) {
                stockSymbolData.setSymbol(s.getSymbol());
                stockSymbolData.setTitle(s.getTitle());
            }
            stockSymbolData.addStockMessages(s.getStockDetailList());
        });

        stockSymbolData.setDecision(decision);
        stockSymbolData.setFinanceReco(financialSentimentService.loadFinaincialRecommendations(symbol));
        return stockSymbolData;
    }
}
