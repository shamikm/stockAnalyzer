package org.maj.analyzer.service;

import org.maj.analyzer.ingest.LoadRecommendations;
import org.maj.analyzer.model.FxRecommendation;
import org.maj.analyzer.model.Sentiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shamikm78 on 9/23/16.
 */
@Component
public class FinancialSentimentService {
    @Autowired
    private LoadRecommendations recoLoader;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    public List<FxRecommendation> loadFinaincialRecommendations(String symbol){
        List<FxRecommendation> recommendations = recoLoader.loadRecommendation(symbol);
        return recommendations.stream().map(a -> {
            Sentiment sentiment = sentimentAnalysisService.analyzeSentiment(a.getDetails());
            a.setSentiment(fineTune(sentiment,a));
            return a;
        }).collect(Collectors.toList());
    }

    private Sentiment fineTune(Sentiment sentiment, FxRecommendation r){
        String what = r.getWhat().toLowerCase();
        return "downgrade".equals(what) ? Sentiment.BEARISH : "upgrade".equals(what) ? Sentiment.BULLISH : sentiment;
    }
}
