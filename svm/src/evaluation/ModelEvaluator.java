package evaluation;

import model.SVMModel;
import model.SVMKernel;

/**
 * ModelEvaluator class for evaluating SVM model performance.
 * 
 * This class provides methods to evaluate a trained SVM model using
 * various performance metrics on a test dataset.
 */
public class ModelEvaluator {
    
    /**
     * Finds the optimal C parameter for an SVM model using validation data.
     * 
     * @param XTrain Training feature matrix
     * @param yTrain Training label array
     * @param XVal Validation feature matrix
     * @param yVal Validation label array
     * @param kernel Kernel to use for the SVM
     * @param cValues Array of C values to try
     * @param maxIterations Maximum iterations for each model
     * @return The optimal C value
     */
    public double findOptimalCParameter(double[][] XTrain, double[] yTrain, 
                                      double[][] XVal, double[] yVal,
                                      SVMKernel kernel, double[] cValues, int maxIterations) {
        // Use a map to track unique C values and their metrics
        java.util.Map<Double, java.util.List<double[]>> cMetricsMap = new java.util.HashMap<>();
        
        System.out.println("\n===== Hyperparameter Tuning =====");
        System.out.println("Finding optimal C parameter using validation data...");
        System.out.println("Running " + cValues.length + " total experiments...");
        
        // First pass: collect metrics for each run
        for (int i = 0; i < cValues.length; i++) {
            double c = cValues[i];
            
            // Create and train model with current C value
            SVMModel model = new SVMModel(c, maxIterations, kernel);
            model.train(XTrain, yTrain);
            
            // Evaluate on validation set
            double[] metrics = evaluate(model, XVal, yVal);
            
            // Store metrics for this C value
            if (!cMetricsMap.containsKey(c)) {
                cMetricsMap.put(c, new java.util.ArrayList<>());
            }
            cMetricsMap.get(c).add(metrics);
            
            // Print individual run results
            System.out.println("Run " + (i + 1) + ": C = " + c + 
                             ", Accuracy: " + String.format("%.4f", metrics[0]) +
                             ", Precision: " + String.format("%.4f", metrics[1]) +
                             ", Recall: " + String.format("%.4f", metrics[2]) +
                             ", F1: " + String.format("%.4f", metrics[3]));
        }
        
        // Second pass: calculate averages for each unique C value
        System.out.println("\n----- Average Metrics by C Value -----");
        
        double bestC = 0.0;
        double bestAvgAccuracy = 0.0;
        
        for (double c : cMetricsMap.keySet()) {
            java.util.List<double[]> metricsList = cMetricsMap.get(c);
            int runs = metricsList.size();
            
            // Calculate averages
            double avgAccuracy = 0.0;
            double avgPrecision = 0.0;
            double avgRecall = 0.0;
            double avgF1 = 0.0;
            
            for (double[] metrics : metricsList) {
                avgAccuracy += metrics[0];
                avgPrecision += metrics[1];
                avgRecall += metrics[2];
                avgF1 += metrics[3];
            }
            
            avgAccuracy /= runs;
            avgPrecision /= runs;
            avgRecall /= runs;
            avgF1 /= runs;
            
            // Print average metrics for this C value
            System.out.println("C = " + c + " (" + runs + " runs)" +
                             "\n  Avg Accuracy: " + String.format("%.4f", avgAccuracy) +
                             "\n  Avg Precision: " + String.format("%.4f", avgPrecision) +
                             "\n  Avg Recall: " + String.format("%.4f", avgRecall) +
                             "\n  Avg F1 Score: " + String.format("%.4f", avgF1));
            
            // Update best C if current average accuracy is better
            if (avgAccuracy > bestAvgAccuracy) {
                bestAvgAccuracy = avgAccuracy;
                bestC = c;
            }
        }
        
        System.out.println("\nOptimal C value: " + bestC + 
                         " (Average Validation Accuracy: " + String.format("%.4f", bestAvgAccuracy) + ")");
        System.out.println("===================================\n");
        
        return bestC;
    }
    
    /**
     * Evaluates the model on the provided test data and returns various performance metrics.
     * 
     * @param model Trained SVM model
     * @param XTest Test feature matrix
     * @param yTest Test label array
     * @return Array of performance metrics [accuracy, precision, recall, f1Score]
     */
    public double[] evaluate(SVMModel model, double[][] XTest, double[] yTest) {
        // Get predictions from the model
        double[] predictions = model.predict(XTest);
        
        // Calculate metrics using the Metrics class
        double accuracy = Metrics.accuracy(yTest, predictions);
        double precision = Metrics.precision(yTest, predictions, 1.0); // Assuming positive class is 1.0
        double recall = Metrics.recall(yTest, predictions, 1.0);
        double f1Score = Metrics.f1Score(yTest, predictions, 1.0);
        
        // Return array of metrics
        return new double[] {accuracy, precision, recall, f1Score};
    }
    
    /**
     * Calculates the confusion matrix for the model predictions.
     * 
     * @param model Trained SVM model
     * @param XTest Test feature matrix
     * @param yTest Test label array
     * @return 2x2 confusion matrix: [[TN, FP], [FN, TP]]
     */
    public int[][] confusionMatrix(SVMModel model, double[][] XTest, double[] yTest) {
        // Get predictions from the model
        double[] predictions = model.predict(XTest);
        
        // Calculate confusion matrix using the Metrics class
        return Metrics.confusionMatrix(yTest, predictions, 1.0); // Assuming positive class is 1.0
    }
    
    /**
     * Prints a formatted evaluation report with all metrics.
     * 
     * @param model Trained SVM model
     * @param XTest Test feature matrix
     * @param yTest Test label array
     */
    public void printEvaluationReport(SVMModel model, double[][] XTest, double[] yTest) {
        double[] metrics = evaluate(model, XTest, yTest);
        int[][] confMatrix = confusionMatrix(model, XTest, yTest);
        
        System.out.println("\n===== SVM Model Evaluation Report =====");
        System.out.println("Accuracy:  " + String.format("%.4f", metrics[0]));
        System.out.println("Precision: " + String.format("%.4f", metrics[1]));
        System.out.println("Recall:    " + String.format("%.4f", metrics[2]));
        System.out.println("F1 Score:  " + String.format("%.4f", metrics[3]));
        
        System.out.println("\nConfusion Matrix:");
        System.out.println("             Predicted");
        System.out.println("             Neg    Pos");
        System.out.println("Actual Neg | " + String.format("%4d", confMatrix[0][0]) + 
                         " | " + String.format("%4d", confMatrix[0][1]) + " |");
        System.out.println("       Pos | " + String.format("%4d", confMatrix[1][0]) + 
                         " | " + String.format("%4d", confMatrix[1][1]) + " |");
        System.out.println("======================================\n");
    }
}
