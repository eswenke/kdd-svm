package evaluation;

/**
 * Metrics class for evaluating SVM model performance.
 * 
 * This class provides methods to calculate various performance metrics
 * for binary classification, such as accuracy, precision, recall, and F1 score.
 */
public class Metrics {
    
    /**
     * Calculates the accuracy of predictions.
     * 
     * Accuracy = (TP + TN) / (TP + TN + FP + FN)
     * 
     * @param yTrue True labels
     * @param yPred Predicted labels
     * @return Accuracy value between 0 and 1
     */
    public static double accuracy(double[] yTrue, double[] yPred) {
        // TODO: Implement accuracy calculation
        // Count how many predictions match the true labels
        return 0.0;
    }
    
    /**
     * Calculates the precision for the positive class.
     * 
     * Precision = TP / (TP + FP)
     * 
     * @param yTrue True labels
     * @param yPred Predicted labels
     * @param positiveClass The value representing the positive class (e.g., +1)
     * @return Precision value between 0 and 1
     */
    public static double precision(double[] yTrue, double[] yPred, double positiveClass) {
        // TODO: Implement precision calculation
        // Of all instances predicted as positive, how many are actually positive
        return 0.0;
    }
    
    /**
     * Calculates the recall (sensitivity) for the positive class.
     * 
     * Recall = TP / (TP + FN)
     * 
     * @param yTrue True labels
     * @param yPred Predicted labels
     * @param positiveClass The value representing the positive class (e.g., +1)
     * @return Recall value between 0 and 1
     */
    public static double recall(double[] yTrue, double[] yPred, double positiveClass) {
        // TODO: Implement recall calculation
        // Of all actual positive instances, how many were correctly predicted
        return 0.0;
    }
    
    /**
     * Calculates the F1 score for the positive class.
     * 
     * F1 = 2 * (Precision * Recall) / (Precision + Recall)
     * 
     * @param yTrue True labels
     * @param yPred Predicted labels
     * @param positiveClass The value representing the positive class (e.g., +1)
     * @return F1 score between 0 and 1
     */
    public static double f1Score(double[] yTrue, double[] yPred, double positiveClass) {
        // TODO: Implement F1 score calculation
        // Harmonic mean of precision and recall
        return 0.0;
    }
    
    /**
     * Calculates the confusion matrix for binary classification.
     * 
     * @param yTrue True labels
     * @param yPred Predicted labels
     * @param positiveClass The value representing the positive class (e.g., +1)
     * @return 2x2 confusion matrix: [[TN, FP], [FN, TP]]
     */
    public static int[][] confusionMatrix(double[] yTrue, double[] yPred, double positiveClass) {
        // TODO: Implement confusion matrix calculation
        // Count TP, TN, FP, FN
        return null;
    }
}
