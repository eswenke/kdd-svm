package processing;

import java.util.Random;

/**
 * DataSplitter class for splitting data into training, validation, and test sets.
 * 
 * This class provides methods to split datasets for proper model evaluation,
 * with options for random shuffling and stratification to maintain class distribution.
 */
public class DataSplitter {
    
    // Random number generator for shuffling
    private Random random;
    
    // Whether to shuffle the data before splitting
    private boolean shuffle;
    
    /**
     * Creates a DataSplitter with the specified shuffling option.
     * 
     * @param shuffle Whether to shuffle the data before splitting
     * @param seed Random seed for reproducibility (use 0 for random seed)
     */
    public DataSplitter(boolean shuffle, long seed) {
        this.shuffle = shuffle;
        this.random = new Random(seed != 0 ? seed : System.currentTimeMillis());
    }
    
    /**
     * Performs stratified splitting, which preserves the class distribution in each set.
     * 
     * @param X Feature matrix
     * @param y Label array
     * @param trainRatio Proportion of data to use for training
     * @param validationRatio Proportion of data to use for validation
     * @return Array containing training features, validation features, test features,
     *         training labels, validation labels, and test labels
     * @throws IllegalArgumentException if ratios don't sum to less than or equal to 1.0
     */
    public Object[] stratifiedSplit(double[][] X, double[] y, double trainRatio, double validationRatio) {
        // TODO: Implement stratified splitting
        // 1. split all data into two arrays, one for each class.
        // 2. shuffle each class array
        // 3. for each class array, split into the train, val, and test ratios.
        // 4. merge the arrays from corresponding sets together from both classes
        // 5. return the merged arrays
        return null;
    }
    
    /**
     * Shuffles the data randomly.
     * 
     * @param X Feature matrix
     * @param y Label array
     * @return Array containing shuffled X and y
     */
    private Object[] shuffleData(double[][] X, double[] y) {
        // TODO: Implement data shuffling
        // Randomly permute the data while keeping X and y aligned
        return null;
    }
    
    /**
     * Splits the data into just training and test sets (no validation set). NOT SURE IF NEEDED,
     * BUT HERE IS A PLACEHOLDER. focus on split() for now, which gives us train, val, test.
     * 
     * @param X Feature matrix
     * @param y Label array
     * @param trainRatio Proportion of data to use for training (e.g., 0.8 for 80%)
     * @return Array containing training features, test features, training labels, and test labels
     */
    public Object[] splitTrainTest(double[][] X, double[] y, double trainRatio) {
        // TODO: Implement train/test splitting
        // Similar to split() but without validation set
        return null;
    }
}
