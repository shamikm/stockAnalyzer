package org.maj.analyzer.service;

import org.maj.analyzer.ingest.DataLoader;
import org.maj.analyzer.ingest.StockDetailsLoader;
import org.maj.analyzer.ingest.TrendLoader;
import org.maj.analyzer.model.Decision;
import org.maj.analyzer.model.SData;
import org.maj.analyzer.model.Signal;
import org.maj.analyzer.model.Symbol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shamikm78 on 9/14/16.
 */
@Component
public class AnalyzeService {
    @Autowired
    @Qualifier("simpleloader")
    private DataLoader dataLoader;

    @Autowired
    private FinancialSentimentService financialSentimentService;

    @Autowired
    @Qualifier("trendloader")
    private TrendLoader trendLoader;

    private List<StockDetailsLoader> detailsLoader;

    private List<DecisionMaker> decisionMakers;

    public void setDecisionMakers(List<DecisionMaker> decisionMakers) {
        this.decisionMakers = decisionMakers;
    }

    public void setDetailsLoader(List<StockDetailsLoader> detailsLoader) {
        this.detailsLoader = detailsLoader;
    }

    public Symbol takeADecision(String symbol){
        final List<SData> dataList = dataLoader.loadData(symbol);

/*        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter("/tmp/test.csv"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dataList.forEach(d -> {
                csvWriter.writeNext(new String[]{d.getSymbol(),d.getDate().format(formatter),String.format("%.2f",d.getPrice())});

            });
            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        List<Decision> decisions = new ArrayList<>();
        decisionMakers.forEach(decisionMaker -> decisions.add(decisionMaker.takeDecision(dataList)));
        List<Decision> sellSignals = decisions.stream().filter(d -> d.getSignal() == Signal.SELL).collect(Collectors.toList());
        List<Decision> buySignals = decisions.stream().filter(d -> d.getSignal() == Signal.BUY).collect(Collectors.toList());



        Symbol stockSymbolData = new Symbol();
        detailsLoader.forEach(loader -> {
            Symbol s = loader.loadStockDetails(symbol);
            if (stockSymbolData.getSymbol() == null) {
                stockSymbolData.setSymbol(s.getSymbol());
                stockSymbolData.setTitle(s.getTitle());
            }
            stockSymbolData.addStockMessages(s.getStockDetailList());
        });

        stockSymbolData.setSignal(sellSignals.size() > buySignals.size() ? Signal.SELL : Signal.BUY);
        stockSymbolData.setFinanceReco(financialSentimentService.loadFinaincialRecommendations(symbol));
        stockSymbolData.setRawData(dataList);
        return stockSymbolData;
    }

    public List<SData> getTrends(){
        return trendLoader.loadData("");
    }
}
