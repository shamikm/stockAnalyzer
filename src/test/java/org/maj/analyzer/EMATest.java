package org.maj.analyzer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamikm78 on 9/13/16.
 */
public class EMATest {
    private int[] array = new int[]{12,10,9,11,9,8,10};

    @Test
    public void testMovingAverage(){
        int window = 3;
        List<Integer>  results = new ArrayList<>();

        for (int i=0; i <= array.length - window; i++){
            int t = 0;
            for (int j=i; j<i+window;j++){
               t = t + array[j];
            }
            results.add(t/window);
        }

        results.forEach(a -> System.out.println(a));

    }

    @Test
    public void testEMA(){
        int window = 3;
        List<Double>  results = new ArrayList<>();

        for (int i=0; i <= array.length - window; i++){
            double t = 0;
            double alpha = 0.25;
            for (int j=i; j<i+window;j++){
                t = j == i ? array[j] : t + alpha * (array[j]-t);
            }
            results.add(t);
        }

        results.forEach(a -> System.out.println(a));
    }
}
