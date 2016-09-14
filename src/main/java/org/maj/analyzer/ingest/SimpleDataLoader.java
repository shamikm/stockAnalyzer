package org.maj.analyzer.ingest;

import au.com.bytecode.opencsv.CSVReader;
import org.maj.analyzer.Application;
import org.maj.analyzer.model.SData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shamik.majumdar
 */
public class SimpleDataLoader implements DataLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static final String G_ENDPOINT = "http://www.google.com/finance/historical?output=csv&q=%s";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
    @Override
    public List<SData> loadData(String symbol) {
        List<SData> dataList = new ArrayList<>();
        try {
            URL sUrl = new URL(String.format(G_ENDPOINT,symbol));
            try(BufferedReader in = new BufferedReader(new InputStreamReader(sUrl.openStream()))){
                CSVReader reader = new CSVReader(in);
                String[] tokens = null;
                while ((tokens = reader.readNext()) != null) {
                    LocalDate date = LocalDate.parse(tokens[0],formatter);
                    dataList.add(new SData(symbol,date,Double.parseDouble(tokens[4])));
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return dataList;
    }
}
