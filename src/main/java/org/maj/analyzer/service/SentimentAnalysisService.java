package org.maj.analyzer.service;

import org.maj.analyzer.model.Sentiment;

/**
 * Created by shamikm78 on 9/19/16.
 */
public interface SentimentAnalysisService {
    Sentiment analyzeSentiment(String text);
}
