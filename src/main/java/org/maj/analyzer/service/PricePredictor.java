package org.maj.analyzer.service;

import org.maj.analyzer.model.SData;

import java.util.List;

/**
 * Created by shamikm78 on 11/2/16.
 */
public interface PricePredictor {
    double predictPrice(List<SData> data);
}
