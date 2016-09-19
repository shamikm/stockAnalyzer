package org.maj.analyzer.config;

import org.maj.analyzer.ingest.LoadFromTwitter;
import org.maj.analyzer.ingest.STbasedLoader;
import org.maj.analyzer.ingest.StockDetailsLoader;
import org.maj.analyzer.service.AnalyzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamikm78 on 9/19/16.
 */
@Configuration
public class AppConfig {
    @Autowired
    private STbasedLoader sTbasedLoader;
    @Autowired
    private LoadFromTwitter loadFromTwitter;
    @Bean
    public AnalyzeService analyzeService(){
        AnalyzeService analyzeService = new AnalyzeService();
        List<StockDetailsLoader> stockDetailsLoaders = new ArrayList<>();
        stockDetailsLoaders.add(sTbasedLoader);
        stockDetailsLoaders.add(loadFromTwitter);
        analyzeService.setDetailsLoader(stockDetailsLoaders);
        return analyzeService;
    }
}
