package your.package;

import java.util.List;

public class SVMModel {
    private double[] weights;      // weight vector for the hyperplane
    private double bias;           // bias term for the hyperplane
    private double learningRate;   // learning rate for gradient descent
    private int maxIterations;     // maximum number of iterations
    
    // default constructor
    public SVMModel() {
        this(0.01, 1000);
    }
    
    // svm constructor for manual params
    public SVMModel(double learningRate, int maxIterations) {
        this.learningRate = learningRate;
        this.maxIterations = maxIterations;
    }
    
    // train svm given data
    public void train(List<DataPoint> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Training data cannot be empty");
        }
        
        int featureCount = data.get(0).getFeatures().length;
        weights = new double[featureCount];
        bias = 0.0;
        
        // Initialize weights to small random values
        for (int i = 0; i < featureCount; i++) {
            weights[i] = 0.01 * Math.random();
        }
        
        // TODO: need to implement training algorithm

        System.out.println("Training completed. Weights: " + arrayToString(weights) + ", Bias: " + bias);
    }
    
    // predict class of datapoints
    public int predict(double[] features) {
        double decision = dotProduct(weights, features) + bias;
        return decision >= 0 ? 1 : -1;
    }
    
    // compute dot product
    private double dotProduct(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vectors must have the same length");
        }
        
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
        }
        return sum;
    }
    
    // convert array to string repr
    private String arrayToString(double[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(String.format("%.4f", array[i]));
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
