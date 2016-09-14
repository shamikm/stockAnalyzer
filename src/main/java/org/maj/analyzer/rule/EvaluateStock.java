package org.maj.analyzer.rule;

import org.maj.analyzer.model.Decision;
import org.maj.analyzer.model.SData;

import java.util.List;

/**
 * Created by shamikm78 on 9/14/16.
 */
public interface EvaluateStock {
    Decision evaluate(List<SData> data);
}
