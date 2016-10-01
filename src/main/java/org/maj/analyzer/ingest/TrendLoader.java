package org.maj.analyzer.ingest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.maj.analyzer.Application;
import org.maj.analyzer.model.SData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamikm78 on 9/30/16.
 */
@Component(value="trendloader")
public class TrendLoader implements DataLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @Override
    public List<SData> loadData(String symbol) {
        List<SData> result = new ArrayList<>();
        Document document = null;
        try {
            document = Jsoup.connect("http://finviz.com/screener.ashx?v=111&s=ta_topgainers&f=geo_usa,sh_avgvol_o300,sh_price_o2&o=-change")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = document.select("div#screener-content");
        Elements innerElements = elements.select("tr").get(5).select("tr");

        for (int index=2; index < innerElements.size(); index++) {
            Element a = innerElements.get(index);
            Elements cols = a.select("td");
            String s = null;
            double p = 0D;
            String name = null;
            String mcap = null;
            for (int i=0; i < cols.size(); i++){
                switch(i){
                    case 1 :
                        s = cols.get(i).text();
                        break;
                    case 2 :
                        name =  cols.get(i).text();
                        break;
                    case 6:
                        mcap = cols.get(i).text();
                        break;
                    case 8 :
                        p =  Double.valueOf(cols.get(i).text());
                        break;
                }

            }
            SData sData = new SData(s, LocalDate.now(),p);
            sData.setMarketCap(mcap);
            sData.setName(name);
            result.add(sData);
        }
        return result;
    }
}
