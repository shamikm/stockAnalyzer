package org.maj.analyzer.service;

import org.maj.analyzer.ingest.DataLoader;
import org.maj.analyzer.model.Decision;
import org.maj.analyzer.model.SData;
import org.maj.analyzer.rule.EvaluateStock;
import org.maj.analyzer.transformer.DeriveMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by shamikm78 on 9/14/16.
 */
@Component
public class AnalyzeService {
    @Autowired
    private DataLoader dataLoader;
    @Autowired
    private DeriveMetrics deriveMetrics;
    @Autowired
    private EvaluateStock evaluateStock;

    public Decision takeADecision(String symbol){
        List<SData> dataList = dataLoader.loadData(symbol);
        dataList = deriveMetrics.transform(dataList);
        return evaluateStock.evaluate(dataList);
    }
}
