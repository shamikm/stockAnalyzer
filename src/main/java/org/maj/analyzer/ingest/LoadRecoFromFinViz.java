package org.maj.analyzer.ingest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.maj.analyzer.model.FxRecommendation;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamikm78 on 9/23/16.
 */
@Component
public class LoadRecoFromFinViz implements LoadRecommendations{
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(LoadRecoFromFinViz.class);
    private static final String url = "http://finviz.com/quote.ashx?t=%s";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yy");
    @Override
    public List<FxRecommendation> loadRecommendation(String symbol) {
        List<FxRecommendation> recommendations = new ArrayList<>();
        try {
            Document document = Jsoup.connect(String.format(url,symbol)).get();
            Elements elements = document.select("td.fullview-ratings-inner");
            elements.select("tr").forEach(a -> {
                FxRecommendation reco = new FxRecommendation();
                Elements cols = a.select("td");
                for (int i=0; i < cols.size(); i++){
                    String content = cols.get(i).text();
                    switch(i) {
                        case 0 : reco.setWhen(content);
                            break;
                        case 1 : reco.setWhat(content);
                            break;
                        case 2 : reco.setWho(content);
                            break;
                        case 3 : reco.setDetails(content);
                            break;
                        case 4 : reco.setDetails(reco.getDetails() + " " + content);
                            break;
                    }
                }
                recommendations.add(reco);
            });


        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }

        return recommendations;
    }
}
