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
        if (data == null || data.length == 0) {
            return new double[0][0];
        }
        
        int rows = data.length;
        int cols = data[0].length;
        double[][] normalized = new double[rows][cols];
        
        // process each feature (column)
        for (int col = 0; col < cols; col++) {
            // find min and max values
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            
            for (int row = 0; row < rows; row++) {
                min = Math.min(min, data[row][col]);
                max = Math.max(max, data[row][col]);
            }
            
            // normalize values to [0,1]
            double range = max - min;
            for (int row = 0; row < rows; row++) {
                // handle case where all values are the same
                if (range == 0) {
                    normalized[row][col] = 0.5; // set to middle of range
                } else {
                    normalized[row][col] = (data[row][col] - min) / range;
                }
            }
        }
        
        return normalized;
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
        if (data == null || data.length == 0) {
            return new double[0][0];
        }
        
        int rows = data.length;
        int cols = data[0].length;
        double[][] standardized = new double[rows][cols];
        
        // process each feature (column)
        for (int col = 0; col < cols; col++) {
            // calculate mean
            double sum = 0;
            for (int row = 0; row < rows; row++) {
                sum += data[row][col];
            }
            double mean = sum / rows;
            
            // calculate standard deviation
            double variance = 0;
            for (int row = 0; row < rows; row++) {
                double diff = data[row][col] - mean;
                variance += diff * diff;
            }
            variance /= rows;
            double stdDev = Math.sqrt(variance);
            
            // standardize values
            for (int row = 0; row < rows; row++) {
                // handle case where stdDev is zero
                if (stdDev < 1e-10) {
                    standardized[row][col] = 0;
                } else {
                    standardized[row][col] = (data[row][col] - mean) / stdDev;
                }
            }
        }
        
        return standardized;
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
        if (data == null || data.length == 0) {
            return new Object[]{new double[0][0], new double[0]};
        }
        
        int rows = data.length;
        int cols = data[0].length;
        
        // validate labelColumn
        if (labelColumn < 0 || labelColumn >= cols) {
            throw new IllegalArgumentException("label column index out of bounds");
        }
        
        // create feature matrix (all columns except labelColumn)
        double[][] features = new double[rows][cols - 1];
        double[] labels = new double[rows];
        
        for (int row = 0; row < rows; row++) {
            int featureCol = 0;
            for (int col = 0; col < cols; col++) {
                if (col == labelColumn) {
                    // extract label
                    labels[row] = data[row][col];
                } else {
                    // extract feature
                    features[row][featureCol++] = data[row][col];
                }
            }
        }
        
        return new Object[]{features, labels};
    }
}
