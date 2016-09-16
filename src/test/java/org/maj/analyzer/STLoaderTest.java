package org.maj.analyzer;

import org.junit.Assert;
import org.junit.Test;
import org.maj.analyzer.ingest.STbasedLoader;
import org.maj.analyzer.model.Symbol;

/**
 * Created by shamikm78 on 9/16/16.
 */
public class STLoaderTest {
    @Test
    public void testLoad(){
        STbasedLoader loader = new STbasedLoader();
        Symbol symbol = loader.loadStockDetails("AAPL");
        Assert.assertNotNull(symbol);
        Assert.assertTrue(symbol.getStockDetailList().size() == 30);
    }
}
