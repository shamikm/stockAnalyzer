package org.maj.analyzer;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.maj.analyzer.model.Sentiment;
import org.maj.analyzer.service.StanfordNLPBasedAnalyzer;
import org.springframework.test.annotation.SystemProfileValueSource;

/**
 * Created by shamikm78 on 9/19/16.
 */
public class SentimentTest {
    @Test
    //@Ignore
    public void testSentiment(){
        StanfordNLPBasedAnalyzer analyzer  = new StanfordNLPBasedAnalyzer();
        /*Sentiment sentiment = analyzer.analyzeSentiment("#AAPL is great");
        Assert.assertEquals(sentiment,Sentiment.BULLISH);

        sentiment = analyzer.analyzeSentiment("#AAPL sell ou");
        Assert.assertEquals(sentiment,Sentiment.BEARISH);

        sentiment = analyzer.analyzeSentiment("Big upside to yhoo stock once verizon sale is final");
        System.out.println("Sentiment = "  + sentiment);
*/
        Sentiment sentiment = analyzer.analyzeSentiment("bear put spread");
        System.out.println("Sentiment = "  + sentiment);
    }
}
