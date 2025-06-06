package model;

/**
 * Interface for SVM kernel functions. Can be extended if we want to add RBF or
 * polynomial kernels (or any others in the future)
 * 
 * Kernel functions are used to transform the input space into a higher-dimensional
 * feature space, allowing SVMs to find non-linear decision boundaries.
 */
public interface SVMKernel {
    
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
