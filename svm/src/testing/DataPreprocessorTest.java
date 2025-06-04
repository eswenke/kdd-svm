package testing;

import processing.CSVReader;
import processing.DataPreprocessor;
import java.io.IOException;

/**
 * Test class for DataPreprocessor functionality
 */
public class DataPreprocessorTest {
    
    public static void main(String[] args) throws IOException {
        // load test data
        double[][] data = CSVReader.readCSV("svm/src/data/dataset.csv");
        System.out.println("Loaded data with " + data.length + " rows and " + 
                          (data.length > 0 ? data[0].length : 0) + " columns");
        
        // create preprocessor
        DataPreprocessor preprocessor = new DataPreprocessor();
        
        // test normalization
        System.out.println("\n--- Testing Normalization ---");
        double[][] normalizedData = preprocessor.normalize(data);
        printDataStats(normalizedData, "Normalized");
        
        // test standardization
        System.out.println("\n--- Testing Standardization ---");
        double[][] standardizedData = preprocessor.standardize(data);
        printDataStats(standardizedData, "Standardized");
        
        // test feature/label splitting
        System.out.println("\n--- Testing Feature/Label Splitting ---");
        // assume last column is the label
        int labelColumn = data[0].length - 1;
        Object[] split = preprocessor.splitFeaturesAndLabels(data, labelColumn);
        double[][] features = (double[][]) split[0];
        double[] labels = (double[]) split[1];
        
        System.out.println("Original data dimensions: " + data.length + "x" + data[0].length);
        System.out.println("Features dimensions: " + features.length + "x" + features[0].length);
        System.out.println("Labels dimensions: " + labels.length);
        
        // verify dimensions
        if (features.length == data.length && features[0].length == data[0].length - 1 && labels.length == data.length) {
            System.out.println("PASS: Feature/label splitting dimensions are correct");
        } else {
            System.out.println("FAIL: Feature/label splitting dimensions are incorrect");
        }
        
        // print some sample values
        if (data.length > 0) {
            System.out.println("\nSample values from first row:");
            System.out.print("Original: ");
            for (int i = 0; i < data[0].length; i++) {
                System.out.print(data[0][i] + " ");
            }
            
            System.out.print("\nFeatures: ");
            for (int i = 0; i < features[0].length; i++) {
                System.out.print(features[0][i] + " ");
            }
            
            System.out.println("\nLabel: " + labels[0]);
        }
    }
    
    /**
     * Print statistics about the data
     */
    private static void printDataStats(double[][] data, String label) {
        if (data == null || data.length == 0) {
            System.out.println(label + " data is empty");
            return;
        }
        
        // find min and max values
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double sum = 0;
        int count = 0;
        
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                min = Math.min(min, data[i][j]);
                max = Math.max(max, data[i][j]);
                sum += data[i][j];
                count++;
            }
        }
        
        double mean = sum / count;
        
        // calculate standard deviation
        double variance = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                double diff = data[i][j] - mean;
                variance += diff * diff;
            }
        }
        variance /= count;
        double stdDev = Math.sqrt(variance);
        
        System.out.println(label + " data statistics:");
        System.out.println("  Min: " + min);
        System.out.println("  Max: " + max);
        System.out.println("  Mean: " + mean);
        System.out.println("  Standard Deviation: " + stdDev);
        
        // for normalized data, min should be close to 0 and max close to 1
        if (label.equals("Normalized")) {
            if (Math.abs(min) < 0.001 && Math.abs(max - 1.0) < 0.001) {
                System.out.println("PASS: Normalization range is correct (approximately [0,1])");
            } else {
                System.out.println("FAIL: Normalization range is incorrect: [" + min + ", " + max + "]");
            }
        }
        
        // for standardized data, mean should be close to 0 and stdDev close to 1
        if (label.equals("Standardized")) {
            if (Math.abs(mean) < 0.001 && Math.abs(stdDev - 1.0) < 0.1) {
                System.out.println("PASS: Standardization parameters are correct (approximately mean=0, stdDev=1)");
            } else {
                System.out.println("FAIL: Standardization parameters are incorrect: mean=" + mean + ", stdDev=" + stdDev);
            }
        }
    }
}
