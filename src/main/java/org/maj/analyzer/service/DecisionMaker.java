package org.maj.analyzer.service;

import org.maj.analyzer.model.Decision;
import org.maj.analyzer.model.SData;

import java.util.List;

/**
 * Created by shamikm78 on 9/24/16.
 */
public interface DecisionMaker {
    Decision takeDecision(final List<SData> data);
}
