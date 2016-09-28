package org.maj.analyzer.service;

import org.maj.analyzer.model.Decision;
import org.maj.analyzer.model.SData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shamik.majumdar
 */
@Component(value="stochastic")
public class StochasticOsc implements DecisionMaker {
    @Override
    public Decision takeDecision(List<SData> data) {
        return null;
    }
}
