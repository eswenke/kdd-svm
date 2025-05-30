package evaluation;

import model.SVMModel;

/**
 * ModelEvaluator class for evaluating SVM model performance.
 * 
 * This class provides methods to evaluate a trained SVM model using
 * various performance metrics on a test dataset.
 */
public class ModelEvaluator {
    
    /**
     * Evaluates the model on the provided test data and returns various performance metrics.
     * 
     * @param model Trained SVM model
     * @param XTest Test feature matrix
     * @param yTest Test label array
     * @return Array of performance metrics [accuracy, precision, recall, f1Score]
     */
    public double[] evaluate(SVMModel model, double[][] XTest, double[] yTest) {
        // TODO: Implement model evaluation
        // 1. Get predictions from the model
        // 2. Calculate various metrics (accuracy, precision, recall, F1)
        // 3. Return array of metrics
        return null;
    }
    
    /**
     * Calculates the confusion matrix for the model predictions. wrapper that predicts, then
     * calls the confusionMatrix function from Metrics.java
     * 
     * @param model Trained SVM model
     * @param XTest Test feature matrix
     * @param yTest Test label array
     * @return 2x2 confusion matrix: [[TN, FP], [FN, TP]]
     */
    public int[][] confusionMatrix(SVMModel model, double[][] XTest, double[] yTest) {
        // TODO: Implement confusion matrix calculation
        // 1. Get predictions from the model
        // 2. Compare with true labels to count TP, TN, FP, FN
        // 3. Return the confusion matrix
        return null;
    }
    
    /**
     * Evaluates the model with different hyperparameter values and returns the best configuration.
     * Call the evaluate method on a tuned model to predict on a test set and see how well it does.
     * WE MAY BE ABLE TO SKIP THIS MODEL AND TEST DIFFERENT THINGS MANUALLY IN MAIN, or make this
     * a useful method that eliminates some duplication in main (SVM.java in svm/src)
     * 
     * @param XTrain Training feature matrix
     * @param yTrain Training label array
     * @param XValidation Validation feature matrix
     * @param yValidation Validation label array
     * @param CValues Array of C values to try
     * @param kernels Array of kernel functions to try
     * @return The best model configuration based on validation performance
     */
    public SVMModel tuneHyperparameters(double[][] XTrain, double[] yTrain, 
                                      double[][] XValidation, double[] yValidation,
                                      double[] CValues, Object[] kernels) {
        // TODO: Implement hyperparameter tuning
        // 1. Train models with different combinations of hyperparameters
        // 2. Evaluate each model on the validation set
        // 3. Return the model with the best performance
        return null;
    }
}
