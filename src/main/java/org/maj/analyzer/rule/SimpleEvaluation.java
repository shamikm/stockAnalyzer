package org.maj.analyzer.rule;

import org.maj.analyzer.model.Decision;
import org.maj.analyzer.model.SData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by shamikm78 on 9/14/16.
 */
@Component
public class SimpleEvaluation implements EvaluateStock {
    @Override
    public Decision evaluate(List<SData> data) {

        Decision decision = null;
        SData lastPoint = data.get(data.size()-1);
        if (lastPoint.getMacd() <= 0 ) {
            decision = Decision.SELL;
        }
        else if (lastPoint.getMacd() > 0){
            //TBD : investigate the trend
            decision = Decision.BUY;
        }
        return decision;
    }
}
