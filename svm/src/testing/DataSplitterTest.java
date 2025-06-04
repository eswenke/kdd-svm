package testing;

import processing.CSVReader;
import processing.DataPreprocessor;
import processing.DataSplitter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Test class for DataSplitter functionality
 */
public class DataSplitterTest {
    
    public static void main(String[] args) throws IOException {
        // load test data
        double[][] data = CSVReader.readCSV("svm/src/data/dataset.csv");
        System.out.println("Loaded data with " + data.length + " rows and " + 
                          (data.length > 0 ? data[0].length : 0) + " columns");
        
        // prepare features and labels
        DataPreprocessor preprocessor = new DataPreprocessor();
        int labelColumn = data[0].length - 1; // assume last column is the label
        Object[] splitData = preprocessor.splitFeaturesAndLabels(data, labelColumn);
        double[][] features = (double[][]) splitData[0];
        double[] labels = (double[]) splitData[1];
        
        System.out.println("Features shape: " + features.length + "x" + features[0].length);
        System.out.println("Labels shape: " + labels.length);
        
        // test with shuffling enabled
        testSplitter(features, labels, true);
        
        // test with shuffling disabled
        testSplitter(features, labels, false);
    }
    
    /**
     * Test DataSplitter with different configurations
     */
    private static void testSplitter(double[][] features, double[] labels, boolean shuffle) {
        System.out.println("\n--- Testing DataSplitter (shuffle=" + shuffle + ") ---");
        
        // create splitter with specified shuffle setting and a fixed seed for reproducibility
        DataSplitter splitter = new DataSplitter(shuffle, 42);
        
        // test stratified split
        System.out.println("\nTesting stratified split (60% train, 20% validation, 20% test):");
        Object[] stratifiedSplit = splitter.stratifiedSplit(features, labels, 0.6, 0.2);
        
        // extract results
        double[][] trainFeatures = (double[][]) stratifiedSplit[0];
        double[][] valFeatures = (double[][]) stratifiedSplit[1];
        double[][] testFeatures = (double[][]) stratifiedSplit[2];
        double[] trainLabels = (double[]) stratifiedSplit[3];
        double[] valLabels = (double[]) stratifiedSplit[4];
        double[] testLabels = (double[]) stratifiedSplit[5];
        
        // verify dimensions
        System.out.println("Train set: " + trainFeatures.length + " samples (" + 
                          (100.0 * trainFeatures.length / features.length) + "%)");
        System.out.println("Validation set: " + valFeatures.length + " samples (" + 
                          (100.0 * valFeatures.length / features.length) + "%)");
        System.out.println("Test set: " + testFeatures.length + " samples (" + 
                          (100.0 * testFeatures.length / features.length) + "%)");
        
        // verify class distribution
        System.out.println("\nClass distribution:");
        Map<Double, Integer> originalDist = getClassDistribution(labels);
        Map<Double, Integer> trainDist = getClassDistribution(trainLabels);
        Map<Double, Integer> valDist = getClassDistribution(valLabels);
        Map<Double, Integer> testDist = getClassDistribution(testLabels);
        
        System.out.println("Original: " + originalDist);
        System.out.println("Train: " + trainDist);
        System.out.println("Validation: " + valDist);
        System.out.println("Test: " + testDist);
        
        // verify stratification
        boolean isStratified = verifyStratification(labels, trainLabels, valLabels, testLabels);
        if (isStratified) {
            System.out.println("PASS: Stratification preserved class distribution");
        } else {
            System.out.println("FAIL: Stratification failed to preserve class distribution");
        }
        
        // test train/test split
        System.out.println("\nTesting train/test split (80% train, 20% test):");
        Object[] trainTestSplit = splitter.splitTrainTest(features, labels, 0.8);
        
        // extract results
        double[][] trainFeaturesSimple = (double[][]) trainTestSplit[0];
        double[][] testFeaturesSimple = (double[][]) trainTestSplit[1];
        double[] trainLabelsSimple = (double[]) trainTestSplit[2];
        double[] testLabelsSimple = (double[]) trainTestSplit[3];
        
        // verify dimensions
        System.out.println("Train set: " + trainFeaturesSimple.length + " samples (" + 
                          (100.0 * trainFeaturesSimple.length / features.length) + "%)");
        System.out.println("Test set: " + testFeaturesSimple.length + " samples (" + 
                          (100.0 * testFeaturesSimple.length / features.length) + "%)");
        
        // verify total count
        int totalSamples = trainFeaturesSimple.length + testFeaturesSimple.length;
        if (totalSamples == features.length) {
            System.out.println("PASS: All samples accounted for in train/test split");
        } else {
            System.out.println("FAIL: Sample count mismatch in train/test split");
        }
    }
    
    /**
     * Get distribution of classes in a label array
     */
    private static Map<Double, Integer> getClassDistribution(double[] labels) {
        Map<Double, Integer> distribution = new HashMap<>();
        for (double label : labels) {
            distribution.put(label, distribution.getOrDefault(label, 0) + 1);
        }
        return distribution;
    }
    
    /**
     * Verify that stratification preserved class distribution
     */
    private static boolean verifyStratification(double[] originalLabels, 
                                              double[] trainLabels, 
                                              double[] valLabels, 
                                              double[] testLabels) {
        Map<Double, Integer> originalDist = getClassDistribution(originalLabels);
        Map<Double, Integer> trainDist = getClassDistribution(trainLabels);
        Map<Double, Integer> valDist = getClassDistribution(valLabels);
        Map<Double, Integer> testDist = getClassDistribution(testLabels);
        
        // check that all classes are represented in all splits
        for (Double classLabel : originalDist.keySet()) {
            if (!trainDist.containsKey(classLabel) || 
                !valDist.containsKey(classLabel) || 
                !testDist.containsKey(classLabel)) {
                return false;
            }
        }
        
        // check that proportions are roughly preserved (within 5% tolerance)
        double tolerance = 0.05;
        for (Double classLabel : originalDist.keySet()) {
            double originalProp = (double) originalDist.get(classLabel) / originalLabels.length;
            double trainProp = (double) trainDist.get(classLabel) / trainLabels.length;
            double valProp = (double) valDist.get(classLabel) / valLabels.length;
            double testProp = (double) testDist.get(classLabel) / testLabels.length;
            
            if (Math.abs(trainProp - originalProp) > tolerance ||
                Math.abs(valProp - originalProp) > tolerance ||
                Math.abs(testProp - originalProp) > tolerance) {
                return false;
            }
        }
        
        return true;
    }
}
