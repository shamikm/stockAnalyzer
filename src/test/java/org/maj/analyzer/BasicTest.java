package org.maj.analyzer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.maj.analyzer.ingest.SimpleDataLoader;
import org.maj.analyzer.model.SData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

/**
 * @author shamik.majumdar
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BasicTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicTest.class);
    @Autowired
    private MockMvc mvc;
    @Autowired
    private SimpleDataLoader dataLoader;


    @Test
    public void loadData() throws Exception {
        List<SData> list = dataLoader.loadData("AMZN");
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }
    @Test
    public void loadAndTransform() throws Exception {
        List<SData> list = dataLoader.loadData("AMZN");
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);

    }
}
