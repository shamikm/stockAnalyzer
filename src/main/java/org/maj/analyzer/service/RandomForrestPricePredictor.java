package org.maj.analyzer.service;

import org.maj.analyzer.model.SData;
import org.maj.analyzer.utility.DataSortUtility;
import org.springframework.stereotype.Component;
import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by shamikm78 on 11/2/16.
 */
@Component
public class RandomForrestPricePredictor implements PricePredictor {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MACDDecisionMaker.class);

    private static final int LOOKBACK = 3;

    @Override
    public double predictPrice(List<SData> data) {
        double result = 0D;
        DataSortUtility dataSortUtility = new DataSortUtility(true);
        Collections.sort(data,dataSortUtility);
        List<Double> changes = new ArrayList<>();
        for (int i=0; i < data.size()-1;i++) {
            changes.add(data.get(i).getPrice()-data.get(i+1).getPrice());
        }

        Attribute Attribute1 = new Attribute("firstNumeric");
        Attribute Attribute2 = new Attribute("secondNumeric");
        Attribute Attribute3 = new Attribute("thirdNumeric");
        Attribute classAttribute = new Attribute("theClass");

        FastVector fvWekaAttributes = new FastVector(4);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(Attribute2);
        fvWekaAttributes.addElement(Attribute3);
        fvWekaAttributes.addElement(classAttribute);

        // Create an empty training set
        Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 10);
        // Set class index
        isTrainingSet.setClassIndex(3);

        for (int i=5; i <changes.size()-LOOKBACK; i++) {
            // Create the instance
            Instance iExample = new DenseInstance(4);
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(0), changes.get(i));
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), changes.get(i+1));
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(2), changes.get(i+2));
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(3), changes.get(i-1));
            // add the instance
            isTrainingSet.add(iExample);
        }

        try {
            // Create a Random Forrest classifier
            Classifier cModel = new RandomForest();
            cModel.buildClassifier(isTrainingSet);


            Instance testInstance = new DenseInstance(4);
            testInstance.setValue((Attribute) fvWekaAttributes.elementAt(0), changes.get(0));
            testInstance.setValue((Attribute) fvWekaAttributes.elementAt(1), changes.get(1));
            testInstance.setValue((Attribute) fvWekaAttributes.elementAt(2), changes.get(2));
            testInstance.setDataset(isTrainingSet);

            double[] v = cModel.distributionForInstance(testInstance);

            for (int i = 0; i < 3; i++) {
                LOGGER.info("data " + data.get(i).toString());
                LOGGER.info("change " + changes.get(i));
            }

            LOGGER.info("Expected change {}" ,v[0]);
            LOGGER.info("Expected price {}" , (data.get(0).getPrice() + v[0]));
            result = v[0];
        }catch (Exception ex){
            LOGGER.error("Failed to predict price ", ex);
        }

        return result;
    }
}
