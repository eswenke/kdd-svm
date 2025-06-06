package model;

/**
 * Support Vector Machine (SVM) model implementation.
 * 
 * This class represents a basic SVM model and provides methods for training
 * and prediction. It uses a simplified SMO algorithm for training.
 */
public class SVMModel {
    
    // Lagrange multipliers (alpha values)
    private double[] alphas;
    
    // Bias term (b)
    private double bias;
    
    // Support vectors (subset of training data)
    private double[][] supportVectors;
    
    // Labels for support vectors
    private double[] supportVectorLabels;
    
    // Kernel function
    private SVMKernel kernel;
    
    // Regularization parameter
    private double C;
    
    // SMO optimizer
    private SMOOptimizer optimizer;
    
    /**
     * Creates an SVM model with the specified parameters.
     * 
     * @param kernel Kernel function to use
     * @param C Regularization parameter
     * @param maxIterations Maximum number of iterations for optimization
     */
    public SVMModel(double C, int maxIterations, SVMKernel kernel) {
        this.kernel = kernel;
        this.C = C;
        this.optimizer = new SMOOptimizer(C, maxIterations, kernel);
    }
    
    /**
     * Trains the SVM model on the provided data.
     * 
     * @param X Training features
     * @param y Training labels (should be +1 or -1)
     * @return This model instance (for method chaining)
     */
    public SVMModel train(double[][] X, double[] y) {
        // 1. Use SMO optimizer to find alphas and bias
        Object[] parameters = optimizer.optimize(X, y);
        
        if (parameters != null && parameters.length >= 2) {
            this.alphas = (double[]) parameters[0];
            this.bias = (Double) parameters[1];
        } else {
            // Fallback initialization if optimizer returns null
            this.alphas = new double[X.length];
            this.bias = 0.0;
            // Initialize with small random values for basic functionality
            for (int i = 0; i < alphas.length; i++) {
                alphas[i] = Math.random() * 0.01;
            }
        }
        
        // 2. Identify support vectors (data points with non-zero alphas)
        // 3. Store support vectors and their labels
        int supportVectorCount = 0;
        double threshold = 1e-8;
        
        // Count support vectors
        for (int i = 0; i < alphas.length; i++) {
            if (Math.abs(alphas[i]) > threshold) {
                supportVectorCount++;
            }
        }
        
        // Extract support vectors and their labels
        supportVectors = new double[supportVectorCount][];
        supportVectorLabels = new double[supportVectorCount];
        
        int svIndex = 0;
        for (int i = 0; i < alphas.length; i++) {
            if (Math.abs(alphas[i]) > threshold) {
                supportVectors[svIndex] = X[i].clone();
                supportVectorLabels[svIndex] = y[i];
                svIndex++;
            }
        }

        return this;
    }
    
    /**
     * Predicts the class label for a single input vector.
     * 
     * @param x Input feature vector
     * @return Predicted class label (+1 or -1)
     */
    public double predict(double[] x) {
        // 1. Compute SVM output: f(x) = sum(alpha_i * y_i * K(x_i, x)) + b
        if (supportVectors == null || supportVectorLabels == null || alphas == null) {
            return 0.0; // Model not trained
        }
        
        double sum = 0.0;
        int svIndex = 0;
        
        // Use original alphas array but only for support vectors
        for (int i = 0; i < alphas.length; i++) {
            if (Math.abs(alphas[i]) > 1e-8) { // This is a support vector
                if (svIndex < supportVectors.length) {
                    double kernelValue = kernel.compute(supportVectors[svIndex], x);
                    sum += alphas[i] * supportVectorLabels[svIndex] * kernelValue;
                    svIndex++;
                }
            }
        }
        
        double output = sum + bias;
        
        // 2. Return sign of the output
        return output >= 0 ? 1.0 : -1.0;
    }
    
    /**
     * Predicts class labels for multiple input vectors.
     * 
     * @param X Input feature matrix (each row is a feature vector)
     * @return Array of predicted class labels
     */
    public double[] predict(double[][] X) {
        double[] predictions = new double[X.length];
        
        // Apply predict() to each row of X
        for (int i = 0; i < X.length; i++) {
            predictions[i] = predict(X[i]);
        }
        
        return predictions;
    }
    
    // Additional methods for more advanced SVM implementations:
    
    /*
     * Computes the decision function values (raw SVM outputs before taking the sign).
     * Useful for getting confidence scores or implementing multi-class classification.
     */
    // public double[] decisionFunction(double[][] X) { ... }
    
    /*
     * Gets the support vectors.
     * Useful for model inspection and visualization.
     */
    // public double[][] getSupportVectors() { ... }
    
    /*
     * Gets the number of support vectors.
     * Useful for model complexity analysis.
     */
    // public int getNumSupportVectors() { ... }
    
    /*
     * Gets the weight vector (only applicable for linear kernel).
     * Useful for feature importance analysis and visualization.
     */
    // public double[] getWeightVector() { ... }
}
