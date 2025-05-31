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
        // This function compares indexes of two arrays to check if the same elements
        // match up against each other. Then it returns the overall accuracy of the two arrays

        // Check that the arrays are the same length (this is a requirement)
        if (yTrue.length != yPred.length) {
            throw new IllegalArgumentException("Arrays must be of equal length");
        }

        // Check for the length of the arrays of the elements are equal at each index
        int correct = 0;
        for (int i = 0; i < yPred.length; i++) {
            if (yTrue[i] == yPred[i]) {
                correct++;
            }
        }

        // Returns the correctly predicted amount by the total amount of elements
        return (double) correct / yPred.length;
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
        int truePositives = 0; // TP
        int falsePositives = 0; // FP

        // Check that the arrays are the same length (this is a requirement)
        if (yTrue.length != yPred.length) {
            throw new IllegalArgumentException("Arrays must be of equal length");
        }

        for (int i = 0; i < yPred.length; i++) {
            // If the Element was Predicted as Positive
            if (yPred[i] == positiveClass) {
                //If the Element WAS Positive
                if (yTrue[i] == positiveClass) {
                    truePositives++; // Correctly predicted positive
                } else {
                    falsePositives++; // Incorrectly predicted positive
                }
            }
        }

        // Avoid division by zero: return 0 if no predicted positives
        if (truePositives + falsePositives == 0) {
            return 0.0;
        }

        // Precision = TP / (TP + FP)
        return (double) truePositives / (truePositives + falsePositives);
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
        int truePositives = 0; // Number of correct positive predictions (TP)
        int falseNegatives = 0; // Number of actual positives missed by the model (FN)

        // Check that the arrays are the same length (this is a requirement)
        if (yTrue.length != yPred.length) {
            throw new IllegalArgumentException("Arrays must be of equal length");
        }

        for (int i = 0; i < yTrue.length; i++) {
            // If WAS true
            if (yTrue[i] == positiveClass) {
                // If predicted True
                if (yPred[i] == positiveClass) {
                    truePositives++; // Correctly predicted positive
                } else {
                    falseNegatives++; // Missed Positive
                }
            }
        }

        // Avoid division by zero: return 0 if there are no actual positives
        if (truePositives + falseNegatives == 0) {
            return 0.0;
        }

        // Recall = TP / (TP + FN)
        return (double) truePositives / (truePositives + falseNegatives);
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
        double precision = precision(yTrue, yPred, positiveClass);
        double recall = recall(yTrue, yPred, positiveClass);

        // Avoid division by zero
        if (precision + recall == 0) {
            return 0.0;
        }

        return 2 * (precision * recall) / (precision + recall);
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
        int TP = 0, TN = 0, FP = 0, FN = 0;

        // Check that the arrays are the same length (this is a requirement)
        if (yTrue.length != yPred.length) {
            throw new IllegalArgumentException("Arrays must be of equal length");
        }

        for (int i = 0; i < yPred.length; i++) {
            //If it WAS Positive/Negative
            boolean isTruePositive = (yTrue[i] == positiveClass);
            //If predicted Positive/Negative
            boolean isPredPositive = (yPred[i] == positiveClass);

            //If was a REAL Positive, and Predicted Positive (True Prediction)
            if (isTruePositive && isPredPositive) {
                TP++;
            }
            //If was NEGATIVE, and Predicted Negative (True Prediction)
            else if (!isTruePositive && !isPredPositive) {
                TN++;
            }
            //If was NEGATIVE, but predicted Positive (False Prediction)
            else if (!isTruePositive && isPredPositive) {
                FP++;
            }
            //If was POSITIVE, but predicted Negative (False Prediction)
            else if (isTruePositive && !isPredPositive) {
                FN++;
            }
        }

        System.out.println("TP: " + TP + " TN: " + TN + " FP: " + FP + " FN: " + FN);

        return new int[][]{ {TN, FP},
                            {FN, TP} };
    }
}
