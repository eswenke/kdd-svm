package model;

import math.VectorOps;

/**
 * Linear kernel implementation.
 * 
 * The linear kernel is defined as K(x, y) = x · y (dot product).
 * This is the simplest kernel and is suitable for linearly separable data.
 */
public class LinearKernel implements SVMKernel {
    
    @Override
    public double compute(double[] x, double[] y) {
        // Use VectorOps.dotProduct(x, y)
        return VectorOps.dotProduct(x, y);
    }
    
    @Override
    public String getName() {
        return "Linear";
    }
}
