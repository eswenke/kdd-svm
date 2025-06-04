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
        if (X == null || y == null || X.length == 0 || X.length != y.length) {
            throw new IllegalArgumentException("invalid input data");
        }
        
        if (trainRatio + validationRatio > 1.0 || trainRatio <= 0 || validationRatio < 0) {
            throw new IllegalArgumentException("invalid split ratios");
        }
        
        // double testRatio = 1.0 - trainRatio - validationRatio;
        
        // find unique classes
        java.util.Set<Double> uniqueClasses = new java.util.HashSet<>();
        for (double label : y) {
            uniqueClasses.add(label);
        }
        
        // create indices for each class
        java.util.Map<Double, java.util.List<Integer>> classIndices = new java.util.HashMap<>();
        for (Double classLabel : uniqueClasses) {
            classIndices.put(classLabel, new java.util.ArrayList<>());
        }
        
        // populate indices
        for (int i = 0; i < y.length; i++) {
            classIndices.get(y[i]).add(i);
        }
        
        // prepare result containers
        java.util.List<double[]> trainX = new java.util.ArrayList<>();
        java.util.List<double[]> valX = new java.util.ArrayList<>();
        java.util.List<double[]> testX = new java.util.ArrayList<>();
        java.util.List<Double> trainY = new java.util.ArrayList<>();
        java.util.List<Double> valY = new java.util.ArrayList<>();
        java.util.List<Double> testY = new java.util.ArrayList<>();
        
        // process each class separately
        for (Double classLabel : uniqueClasses) {
            java.util.List<Integer> indices = classIndices.get(classLabel);
            
            // shuffle indices if needed
            if (shuffle) {
                java.util.Collections.shuffle(indices, random);
            }
            
            int numSamples = indices.size();
            int trainSize = (int) Math.round(numSamples * trainRatio);
            int valSize = (int) Math.round(numSamples * validationRatio);
            
            // split indices
            for (int i = 0; i < numSamples; i++) {
                int idx = indices.get(i);
                if (i < trainSize) {
                    // add to training set
                    trainX.add(X[idx]);
                    trainY.add(y[idx]);
                } else if (i < trainSize + valSize) {
                    // add to validation set
                    valX.add(X[idx]);
                    valY.add(y[idx]);
                } else {
                    // add to test set
                    testX.add(X[idx]);
                    testY.add(y[idx]);
                }
            }
        }
        
        // convert lists to arrays
        double[][] trainXArray = listToArray(trainX);
        double[][] valXArray = listToArray(valX);
        double[][] testXArray = listToArray(testX);
        double[] trainYArray = listToDoubleArray(trainY);
        double[] valYArray = listToDoubleArray(valY);
        double[] testYArray = listToDoubleArray(testY);
        
        return new Object[] {
            trainXArray, valXArray, testXArray,
            trainYArray, valYArray, testYArray
        };
    }
    
    /**
     * Shuffles the data randomly.
     * 
     * @param X Feature matrix
     * @param y Label array
     * @return Array containing shuffled X and y
     */
    private Object[] shuffleData(double[][] X, double[] y) {
        if (X == null || y == null || X.length == 0 || X.length != y.length) {
            throw new IllegalArgumentException("invalid input data");
        }
        
        int n = X.length;
        double[][] shuffledX = new double[n][];
        double[] shuffledY = new double[n];
        
        // create index array
        int[] indices = new int[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        
        // shuffle indices
        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            // swap indices[i] and indices[j]
            int temp = indices[i];
            indices[i] = indices[j];
            indices[j] = temp;
        }
        
        // reorder X and y using shuffled indices
        for (int i = 0; i < n; i++) {
            shuffledX[i] = X[indices[i]];
            shuffledY[i] = y[indices[i]];
        }
        
        return new Object[] {shuffledX, shuffledY};
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
        if (X == null || y == null || X.length == 0 || X.length != y.length) {
            throw new IllegalArgumentException("invalid input data");
        }
        
        if (trainRatio <= 0 || trainRatio >= 1.0) {
            throw new IllegalArgumentException("train ratio must be between 0 and 1");
        }
        
        // shuffle data if needed
        if (shuffle) {
            Object[] shuffled = shuffleData(X, y);
            X = (double[][]) shuffled[0];
            y = (double[]) shuffled[1];
        }
        
        int n = X.length;
        int trainSize = (int) Math.round(n * trainRatio);
        
        // create train and test arrays
        double[][] trainX = new double[trainSize][];
        double[] trainY = new double[trainSize];
        double[][] testX = new double[n - trainSize][];
        double[] testY = new double[n - trainSize];
        
        // split data
        for (int i = 0; i < n; i++) {
            if (i < trainSize) {
                trainX[i] = X[i];
                trainY[i] = y[i];
            } else {
                testX[i - trainSize] = X[i];
                testY[i - trainSize] = y[i];
            }
        }
        
        return new Object[] {trainX, testX, trainY, testY};
    }
    
    // helper method to convert list of double arrays to 2D array
    private double[][] listToArray(java.util.List<double[]> list) {
        if (list.isEmpty()) {
            return new double[0][0];
        }
        
        double[][] array = new double[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
    
    // helper method to convert list of doubles to array
    private double[] listToDoubleArray(java.util.List<Double> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
