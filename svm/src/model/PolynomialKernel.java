package model;

import math.VectorOps;

/**
 * Polynomial kernel implementation.
 * Useful for data that has non-linear decision boundaries.
 * K(x, y) = (x · y + c)^d where c is a constant and d is the degree.
 */
public class PolynomialKernel implements SVMKernel {
    private final double constant;
    private final int degree;
    
    /**
     * Constructor with custom parameters
     * 
     * @param constant The constant term c in (x · y + c)^d
     * @param degree The degree d in (x · y + c)^d
     */
    public PolynomialKernel(double constant, int degree) {
        this.constant = constant;
        this.degree = degree;
    }

    @Override
    public double compute(double[] x, double[] y) {
        double dotProduct = VectorOps.dotProduct(x, y);
        // K(x, y) = (x · y + c)^d
        return Math.pow(dotProduct + this.constant, this.degree);
    }

    @Override
    public String getName() {
        return "Polynomial Kernel";
    }
}
