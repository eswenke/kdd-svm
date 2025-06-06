// main SVM class in the default package
// no package declaration needed for the top-level src directory

import java.io.IOException;
import processing.CSVReader;
import processing.DataPreprocessor;
import processing.DataSplitter;
import model.SVMModel;
import model.SVMKernel;
import model.LinearKernel;
import model.PolynomialKernel;
import model.RBFKernel;
import evaluation.ModelEvaluator;
import evaluation.Metrics;

/**
 * Main SVM class that demonstrates the SVM implementation with different kernels
 * and evaluates performance on a dataset.
 */
public class SVM {

    public static void main(String[] args) throws IOException {
        System.out.println("=== SVM Implementation with SMO Optimization ===");
        
        // load and preprocess data
        System.out.println("\n1. Loading and preprocessing data...");
        double[][] data = CSVReader.readCSV("svm/src/data/dataset.csv");
        
        // verify data dimensions
        System.out.println("Loaded data with " + data.length + " rows and " + 
                          (data.length > 0 ? data[0].length : 0) + " columns");
        
        // create preprocessor for data operations
        DataPreprocessor preprocessor = new DataPreprocessor();
        
        // separate features and labels (assuming last column is the label)
        int labelColumn = data[0].length - 1;
        Object[] splitData = preprocessor.splitFeaturesAndLabels(data, labelColumn);
        double[][] X = (double[][]) splitData[0];
        double[] y = (double[]) splitData[1];
        
        // normalize features
        X = preprocessor.normalize(X);
        
        // split data into training, validation, and test sets
        System.out.println("\n2. Splitting data into train/validation/test sets...");
        DataSplitter splitter = new DataSplitter(true, 42); // shuffle with fixed seed for reproducibility
        Object[] splitDatasets = splitter.stratifiedSplit(X, y, 0.8, 0.1); // 70% train, 15% validation, 15% test
        
        double[][] XTrain = (double[][]) splitDatasets[0];
        double[][] XVal = (double[][]) splitDatasets[1];
        double[][] XTest = (double[][]) splitDatasets[2];
        double[] yTrain = (double[]) splitDatasets[3];
        double[] yVal = (double[]) splitDatasets[4];
        double[] yTest = (double[]) splitDatasets[5];
        
        System.out.println("Train set: " + XTrain.length + " samples");
        System.out.println("Validation set: " + XVal.length + " samples");
        System.out.println("Test set: " + XTest.length + " samples");
        
        // create linear kernel
        System.out.println("\n3. Creating kernel function...");
        SVMKernel linearKernel = new LinearKernel();
        
        // train and evaluate model
        System.out.println("\n4. Training and evaluating SVM model with linear kernel...");
        ModelEvaluator evaluator = new ModelEvaluator();
        
        // Hyperparameter tuning using validation set
        System.out.println("\n=== Hyperparameter Tuning with Validation Set ===");
        double[] cValues = {5.0, 5.0, 5.0, 5.0, 5.0};
        double optimalC = evaluator.findOptimalCParameter(
            XTrain, yTrain, XVal, yVal, linearKernel, cValues, 200);
        
        // linear kernel evaluation with optimal C
        System.out.println("\n=== Linear Kernel SVM with Optimal C ===\n");
        SVMModel linearSVM = new SVMModel(optimalC, 200, linearKernel);
        trainAndEvaluate(linearSVM, XTrain, yTrain, XTest, yTest, evaluator);

    }
    
    /**
     * Helper method to train a model and evaluate its performance
     */
    private static void trainAndEvaluate(SVMModel model, double[][] XTrain, double[] yTrain, 
                                         double[][] XTest, double[] yTest, ModelEvaluator evaluator) {
        // Train the model
        long startTime = System.currentTimeMillis();
        model.train(XTrain, yTrain);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Training completed in " + (endTime - startTime) + " ms");
        
        // Evaluate on test set
        evaluator.printEvaluationReport(model, XTest, yTest);
    }
}
