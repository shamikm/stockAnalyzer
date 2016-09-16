package org.maj.analyzer.ingest;

import org.maj.analyzer.model.Symbol;

/**
 * Created by shamikm78 on 9/16/16.
 */
public interface StockDetailsLoader {
    Symbol loadStockDetails(String ssymbol);
}
