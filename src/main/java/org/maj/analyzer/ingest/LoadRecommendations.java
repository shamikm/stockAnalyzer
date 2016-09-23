package org.maj.analyzer.ingest;

import org.maj.analyzer.model.FxRecommendation;

import java.util.List;

/**
 * Created by shamikm78 on 9/23/16.
 */
public interface LoadRecommendations {
    List<FxRecommendation> loadRecommendation(String symbol);
}
