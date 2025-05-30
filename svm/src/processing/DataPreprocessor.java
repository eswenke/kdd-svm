package processing;

/**
 * DataPreprocessor class for handling data preprocessing tasks for SVM.
 * 
 * This class is responsible for:
 * - Normalizing/standardizing feature data
 * - Handling missing values
 * - Preparing data for SVM training and testing
 */
public class DataPreprocessor {
    
    /**
     * Normalizes the input data to have values between 0 and 1.
     * 
     * @param data The input data matrix to normalize
     * @return Normalized data matrix
     */
    public double[][] normalize(double[][] data) {
        // TODO: Implement normalization logic
        // For each feature (column):
        // 1. Find min and max values
        // 2. Scale values to [0,1] using (x - min) / (max - min)
        return null;
    }
    
    /**
     * Standardizes the input data to have zero mean and unit variance. helps with outliers of
     * sorts. think about the lecture we had on collaborative filtering where we needed to account
     * for people who dislike or like movies more on average. just more regularization.
     * 
     * @param data The input data matrix to standardize
     * @return Standardized data matrix
     */
    public double[][] standardize(double[][] data) {
        // TODO: Implement standardization logic
        // For each feature (column):
        // 1. Calculate mean and standard deviation
        // 2. Scale values using (x - mean) / stdDev
        return null;
    }
    
    /**
     * Splits the data into features and labels. one return is the 2D array of datapoints (row
     * is a data point, column is a feature), the other return is the 1D array of labels (row is a
     * data point, column is the label for that point)
     * 
     * @param data The input data matrix
     * @param labelColumn The column index containing the labels
     * @return An array with two elements: features matrix and labels array
     */
    public Object[] splitFeaturesAndLabels(double[][] data, int labelColumn) {
        // TODO: Implement feature/label splitting
        // 1. Extract features (all columns except labelColumn)
        // 2. Extract labels (only labelColumn)
        return null;
    }
}
