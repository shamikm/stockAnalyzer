package org.maj.analyzer;

import org.junit.Before;
import org.junit.Test;
import org.maj.analyzer.ingest.SimpleDataLoader;
import org.maj.analyzer.model.SData;
import org.maj.analyzer.utility.DataSortUtility;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author shamik.majumdar
 */
public class WekaTest {
    @Test
    public void testRandomForest() throws Exception {
        // Declare two numeric attributes
        Attribute Attribute1 = new Attribute("firstNumeric");
        Attribute Attribute2 = new Attribute("secondNumeric");
        Attribute Attribute3 = new Attribute("thirdNumeric");
        Attribute classAttribute = new Attribute("theClass");

        /*// Declare a nominal attribute along with its values
        FastVector fvNominalVal = new FastVector(3);
        fvNominalVal.addElement("blue");
        fvNominalVal.addElement("gray");
        fvNominalVal.addElement("black");
        Attribute Attribute3 = new Attribute("aNominal", fvNominalVal);

        // Declare the class attribute along with its values
        FastVector fvClassVal = new FastVector(2);
        fvClassVal.addElement("positive");
        fvClassVal.addElement("negative");
        Attribute ClassAttribute = new Attribute("theClass", fvClassVal);*/

        // Declare the feature vector
        FastVector fvWekaAttributes = new FastVector(4);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(Attribute2);
        fvWekaAttributes.addElement(Attribute3);
        fvWekaAttributes.addElement(classAttribute);

        // Create an empty training set
        Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 10);
        // Set class index
        isTrainingSet.setClassIndex(3);

        Random random = new Random();

        for (int i=0; i <10; i++) {
            // Create the instance
            Instance iExample = new DenseInstance(4);
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(0), random.nextInt(5));
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), random.nextInt(2));
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(2), random.nextInt(2));
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(3), random.nextInt(3));
            // add the instance
            isTrainingSet.add(iExample);
        }


        // Create a naïve bayes classifier
        Classifier cModel = new RandomForest();
        cModel.buildClassifier(isTrainingSet);

        // Create an empty training set
        Instances isTestingSet = new Instances("Test", fvWekaAttributes,10);
        // Set class index
        isTestingSet.setClassIndex(3);
        for (int i=0; i <5; i++) {
            // Create the instance
            Instance iExample = new DenseInstance(4);
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(0), random.nextInt(5));
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), random.nextInt(2));
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(2), random.nextInt(2));
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(3), random.nextInt(3));
            // add the instance
            isTestingSet.add(iExample);
        }

        Evaluation eTest = new Evaluation(isTrainingSet);
        eTest.evaluateModel(cModel,isTestingSet);

        String strSummary = eTest.toSummaryString();
        System.out.println(strSummary);


/*        // Test the model
        Evaluation eTest = new Evaluation(isTrainingSet);
        eTest.evaluateModel(cModel, isTestingSet);

        // Print the result à la Weka explorer:
        String strSummary = eTest.toSummaryString();
        System.out.println(strSummary);

        // Get the confusion matrix
        double[][] cmMatrix = eTest.confusionMatrix();

        // Specify that the instance belong to the training set
        // in order to inherit from the set description
        iUse.setDataset(isTrainingSet);

        // Get the likelihood of each classes
        // fDistribution[0] is the probability of being “positive”
        // fDistribution[1] is the probability of being “negative”
        double[] fDistribution = cModel.distributionForInstance(iUse);*/


    }


    private SimpleDataLoader dataLoader;

    @Before
    public void setUp(){
        dataLoader = new SimpleDataLoader();
    }

    @Test
    public void testDataLoad() throws Exception {
        int lookback = 3;
        List<SData> data = dataLoader.loadData("UA");
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

        for (int i=5; i <changes.size()-lookback; i++) {
            // Create the instance
            Instance iExample = new DenseInstance(4);
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(0), changes.get(i));
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(1), changes.get(i+1));
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(2), changes.get(i+2));
            iExample.setValue((Attribute) fvWekaAttributes.elementAt(3), changes.get(i-1));
            // add the instance
            isTrainingSet.add(iExample);
        }

        // Create a Random Forrest classifier
        Classifier cModel = new RandomForest();
        cModel.buildClassifier(isTrainingSet);


        Instance testInstance = new DenseInstance(4);
        testInstance.setValue((Attribute) fvWekaAttributes.elementAt(0), changes.get(0));
        testInstance.setValue((Attribute) fvWekaAttributes.elementAt(1), changes.get(1));
        testInstance.setValue((Attribute) fvWekaAttributes.elementAt(2), changes.get(2));
        testInstance.setDataset(isTrainingSet);

        double[] v = cModel.distributionForInstance(testInstance);

        for (int i=0; i < 3; i++) {
            System.out.println("data " + data.get(i).toString());
            System.out.println("change " + changes.get(i));
        }
        System.out.println(v[0]);


    }
}
