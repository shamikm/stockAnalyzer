package org.maj.analyzer.transformer;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.maj.analyzer.model.SData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by shamikm78 on 9/14/16.
 */
@Component
public class EMAtransformer implements DeriveMetrics{
    private int window_26  = 26;
    private int window_12  = 12;
    private double alpha = 0.25;
    @Override
    public List<SData> transform(List<SData> data) {
        List<SData> result = new ArrayList<>();
        if (data == null || data.size() == 0 ) return result;
        data = calculateEMA(data,window_12,alpha,(Pair<Double,SData> pair)->{
            SData data1 = pair.getRight();
            data1.setEma12(pair.getLeft());
            return data1;
        });
        data = calculateEMA(data,window_26,alpha,(Pair<Double,SData> pair)->{
            SData data1 = pair.getRight();
            data1.setEma26(pair.getLeft());
            return data1;
        });

        return data.stream().map(d -> {
            d.setMacd(d.getEma12()-d.getEma26());
            return d;
        }).collect(Collectors.toList());

    }

    private List<SData> calculateEMA(List<SData> data, int window, double alpha, Function<Pair<Double,SData>,SData> function){
        List<SData> result;
        if (data.size() > window){
            result = new ArrayList<>();
            for (int i=0; i <= data.size()-window; i++){
                double temp = 0;
                for (int j=i;j<i+window;j++){
                    double priceToday = data.get(j).getPrice();
                    temp = j == i ? priceToday : temp + alpha * (priceToday - temp);
                }
                result.add(function.apply(new ImmutablePair<>(temp,data.get(i))));
            }
        }else {
            result = data;
        }
        return result;
    }
}
