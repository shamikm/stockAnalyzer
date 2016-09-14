package org.maj.analyzer.transformer;

import org.maj.analyzer.model.SData;

import java.util.List;

/**
 * Created by shamikm78 on 9/14/16.
 */
public interface DeriveMetrics {
    List<SData> transform(List<SData> data);
}
