package org.maj.analyzer.service;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.maj.analyzer.model.Sentiment;
import org.springframework.stereotype.Component;

/**
 * Created by shamikm78 on 9/19/16.
 */
@Component
public class StanfordNLPBasedAnalyzer implements SentimentAnalysisService {
    private static StanfordCoreNLP PIPELINE;

    static{
        PIPELINE = new StanfordCoreNLP("nlp");
    }
    @Override
    public Sentiment analyzeSentiment(String text) {
        int mainSentiment = 0;
        if (text != null && text.length() > 0) {
            //clean the text first
            // normalize!
            text = text.toLowerCase();
            text = text.trim();
            // remove all non alpha-numeric non whitespace chars
            text = text.replaceAll("[^a-zA-Z0-9\\s]", "");

            int longest = 0;
            Annotation annotation = PIPELINE.process(text);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }

            }
        }
        if (mainSentiment == 2 || mainSentiment > 4 || mainSentiment < 0) {
            return null;
        }
        return mainSentiment <= 2 ? Sentiment.BEARISH : Sentiment.BULLISH;

    }
}
