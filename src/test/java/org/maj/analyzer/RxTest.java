package org.maj.analyzer;

import org.junit.Test;
import org.maj.analyzer.ingest.LoadRecoFromFinViz;
import org.maj.analyzer.ingest.LoadRecommendations;
import org.maj.analyzer.model.FxRecommendation;

import java.util.List;

/**
 * Created by shamikm78 on 9/23/16.
 */
public class RxTest {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RxTest.class);
    @Test
    public void loadRecommendation(){
        LoadRecommendations loadRecommendations = new LoadRecoFromFinViz();
        List<FxRecommendation> recommendationList = loadRecommendations.loadRecommendation("AAPL");
        recommendationList.forEach(a -> LOGGER.info(a.toString()));
    }
}
