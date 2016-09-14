package org.maj.analyzer.ingest;

import org.maj.analyzer.model.SData;

import java.util.List;

/**
 * @author shamik.majumdar
 */
public interface DataLoader {
    List<SData> loadData(String symbol);
}
