package org.maj.analyzer.ingest;

import org.maj.analyzer.model.StockDetails;
import org.maj.analyzer.model.Symbol;
import org.maj.analyzer.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.*;

import java.security.acl.LastOwnerException;
import java.util.List;

/**
 * Created by shamikm78 on 9/19/16.
 */
@Component
public class LoadFromTwitter implements StockDetailsLoader{
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(LoadFromTwitter.class);
    private static final String SOURCE = "Twitter";
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Override
    public Symbol loadStockDetails(String symbol) {
        Symbol s = new Symbol();
        s.setSymbol(symbol);
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            Query query = new Query("$" + symbol + " -filter:retweets -filter:links -filter:replies -filter:images");
            query.setLocale("en");
            query.setLang("en");
            query.setCount(20);
            QueryResult result;
            //do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                LOGGER.info("received {} number of tweets",tweets.size());
                for (Status tweet : tweets) {
                    String text = tweet.getText().toLowerCase();
                    //if (text.contains("stock") || text.contains("share") || text.contains("market")) {
                        StockDetails stockDetails = new StockDetails();
                        stockDetails.setMessage(text);
                        stockDetails.setUser(tweet.getUser().getScreenName());
                        stockDetails.setSource(SOURCE);
                        stockDetails.setSentiment(sentimentAnalysisService.analyzeSentiment(tweet.getText()));
                        //stockDetails.setSentiment();
                        s.addStockMessage(stockDetails);
                        LOGGER.info(stockDetails.toString());
                    //}
                }
            //} while ((query = result.nextQuery()) != null);

        } catch (TwitterException ex) {
            LOGGER.error(ex.getMessage(),ex);
        }
        return s;
    }


}
