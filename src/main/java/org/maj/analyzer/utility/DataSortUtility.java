package org.maj.analyzer.utility;

import org.maj.analyzer.model.SData;

import java.util.Comparator;

/**
 * Sorts Data in Descending or ascending order
 * Created by shamikm78 on 10/4/16.
 */
public class DataSortUtility implements Comparator<SData> {
    private final boolean desc;

    public DataSortUtility(boolean desc) {
        this.desc = desc;
    }

    @Override
    public int compare(SData o1, SData o2) {
        return desc ? o2.getDate().compareTo(o1.getDate()) :  o1.getDate().compareTo(o2.getDate());
    }
}
