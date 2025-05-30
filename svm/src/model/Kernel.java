package model;

import math.VectorOps;

/**
 * Interface for SVM kernel functions. Can be extended if we want to add RBF or
 * polynomial kernels (or any others in the future)
 * 
 * Kernel functions are used to transform the input space into a higher-dimensional
 * feature space, allowing SVMs to find non-linear decision boundaries.
 */
public interface Kernel {
    
    /**
     * Computes the kernel function value for two vectors. a vector, in our case, is a list of
     * sensor values (10 values)
     * 
     * @param x First vector
     * @param y Second vector
     * @return The kernel function value K(x, y)
     */
    double compute(double[] x, double[] y);
    
    /**
     * Gets the name of the kernel.
     * 
     * @return The kernel name
     */
    String getName();
}

/**
 * Linear kernel implementation.
 * 
 * The linear kernel is defined as K(x, y) = x · y (dot product).
 * This is the simplest kernel and is suitable for linearly separable data.
 */
class LinearKernel implements Kernel {
    
    @Override
    public double compute(double[] x, double[] y) {
        // TODO: Implement linear kernel (dot product)
        // Use VectorOps.dotProduct(x, y)
        return 0.0;
    }
    
    @Override
    public String getName() {
        return "Linear";
    }
}

// Additional kernel implementations for more advanced SVM variants:

/*
 * Polynomial kernel implementation.
 * Useful for data that has non-linear decision boundaries.
 * K(x, y) = (x · y + c)^d where c is a constant and d is the degree.
 */
// class PolynomialKernel implements Kernel { ... }

/*
 * Radial Basis Function (RBF) kernel implementation.
 * Useful for complex non-linear classification tasks.
 * K(x, y) = exp(-gamma * ||x - y||^2)
 */
// class RBFKernel implements Kernel { ... }
