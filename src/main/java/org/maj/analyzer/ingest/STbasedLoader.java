package org.maj.analyzer.ingest;

import org.maj.analyzer.model.Sentiment;
import org.maj.analyzer.model.StockDetails;
import org.maj.analyzer.model.Symbol;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by shamikm78 on 9/16/16.
 */
@Component
public class STbasedLoader implements StockDetailsLoader {
    private static final String SOURCE = "StockTwits";
    private static final String URL_PATTERN = "https://api.stocktwits.com/api/2/streams/symbol/{symbol}.json";
    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    @Override
    public Symbol loadStockDetails(String symbol) {
        ResponseEntity<Map<String,Object>> responseEntity = restTemplate.exchange(URL_PATTERN, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {}, symbol);
        Map<String,Object> response = responseEntity.getBody();
        Symbol s = new Symbol();
        Map<String,String> sMap = (Map<String, String>) response.get("symbol");
        s.setSymbol(sMap.get("symbol"));
        s.setTitle(sMap.get("title"));
        List<Map<String,Object>> messageList = (List<Map<String, Object>>) response.get("messages");

        messageList.forEach(entry -> {
            StockDetails stockDetails = new StockDetails();
            stockDetails.setSource(SOURCE);
            stockDetails.setUser(((Map<String,String>)entry.get("user")).get("username"));
            stockDetails.setMessage((String) entry.get("body"));
            Map<String,Object> entities = (Map<String,Object>)entry.get("entities");
            Map<String,Object> sentimentMap = (Map<String,Object>)entities.get("sentiment");
            if (sentimentMap != null ) {
                String sentiment = (String) sentimentMap.get("basic");
                stockDetails.setSentiment(Sentiment.valueOf(sentiment.toUpperCase()));
            }
            s.addStockMessage(stockDetails);
        });

        return s;
    }
}
