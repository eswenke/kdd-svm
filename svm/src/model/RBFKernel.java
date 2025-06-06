package model;

/**
 * Radial Basis Function (RBF) kernel implementation.
 * Useful for complex non-linear classification tasks.
 * K(x, y) = exp(-gamma * ||x - y||^2)
 */
public class RBFKernel implements SVMKernel {
    private final double gamma;
    
    /**
     * Constructor with custom gamma parameter
     * 
     * @param gamma The gamma parameter in exp(-gamma * ||x - y||^2)
     */
    public RBFKernel(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public double compute(double[] x, double[] y) {
        // Calculate squared Euclidean distance ||x - y||^2
        if (x.length != y.length) {
            throw new IllegalArgumentException("Vectors must have the same length");
        }
        
        double squaredDistance = 0.0;
        for (int i = 0; i < x.length; i++) {
            double diff = x[i] - y[i];
            squaredDistance += diff * diff;
        }
        
        // K(x, y) = exp(-gamma * ||x - y||^2)
        return Math.exp(-this.gamma * squaredDistance);
    }

    @Override
    public String getName() {
        return "RBF Kernel";
    }
}
