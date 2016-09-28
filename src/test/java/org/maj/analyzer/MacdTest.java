package org.maj.analyzer;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.maj.analyzer.model.Decision;
import org.maj.analyzer.model.SData;
import org.maj.analyzer.service.MACDDecisionMaker;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by shamikm78 on 9/27/16.
 */
public class MacdTest {
    private List<SData> data;

    @Before
    public void setUp() throws Exception{
        data = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader("src/test/resources/test.csv"));
        List<String[]> records = reader.readAll();
        records.forEach(r -> {
            SData sData = new SData(r[0], LocalDate.parse(r[1]),Double.parseDouble(r[2]));
            data.add(sData);
        });
    }
    @Test
    public void testMACD(){
        MACDDecisionMaker macd = new MACDDecisionMaker();
        Decision decision = macd.takeDecision(data);
        Assert.assertEquals(decision.getStatistics().get(0),10.939D,0.001D);

    }
}
