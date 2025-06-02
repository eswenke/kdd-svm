package math;

/**
 * Vector operations utility class for SVM implementation.
 * 
 * This class provides basic vector operations needed for SVM calculations:
 * - Dot product
 * - Vector addition and subtraction
 * - Scalar multiplication
 */
public class VectorOps {
    
    /**
     * Calculates the dot product of two vectors. a vector, in our case, is a list of
     * sensor values (10 values)
     * 
     * @param v1 First vector
     * @param v2 Second vector
     * @return The dot product result
     * @throws IllegalArgumentException if vectors have different lengths
     */
    public static double dotProduct(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors must be of the same length.");
        }

        double sum = 0.0;
        for (int i = 0; i < v1.length; i++) {
            sum += v1[i] * v2[i];
        }

        return sum;
    }

    /**
     * Adds two vectors element-wise.
     * 
     * @param v1 First vector
     * @param v2 Second vector
     * @return A new vector that is the sum of v1 and v2
     * @throws IllegalArgumentException if vectors have different lengths
     */
    public static double[] add(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors must be of the same length.");
        }

        double[] result = new double[v1.length];

        for (int i = 0; i < v1.length; i++) {
            result[i] = v1[i] + v2[i];
        }

        return result;
    }


    /**
     * Subtracts the second vector from the first element-wise.
     * 
     * @param v1 First vector
     * @param v2 Second vector
     * @return A new vector that is v1 - v2
     * @throws IllegalArgumentException if vectors have different lengths
     */
    public static double[] subtract(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors must be of the same length.");
        }

        double[] result = new double[v1.length];

        for (int i = 0; i < v1.length; i++) {
            result[i] = v1[i] - v2[i];
        }

        return result;
    }
    
    /**
     * Multiplies a vector by a scalar value.
     * 
     * @param vector The input vector
     * @param scalar The scalar value
     * @return A new vector with each element multiplied by the scalar
     */
    public static double[] scalarMultiply(double[] vector, double scalar) {
        double[] result = new double[vector.length];

        for (int i = 0; i < vector.length; i++) {
            result[i] = vector[i] * scalar;
        }

        return result;
    }


    // Additional functions that may be useful for more advanced implementations:
    
    /*
     * Calculates the Euclidean (L2) norm of a vector.
     * Useful for normalization and distance calculations in more complex kernels.
     */
    public static double norm(double[] vector) {
        double sumSquares = 0.0;

        for (double v : vector) {
            sumSquares += v * v;
        }

        return Math.sqrt(sumSquares);
    }
    /*
     * Calculates the Euclidean distance between two vectors.
     * Useful for RBF kernel and other distance-based calculations.
     */
    public static double distance(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors must be of the same length.");
        }

        double sumSquares = 0.0;

        for (int i = 0; i < v1.length; i++) {
            double diff = v1[i] - v2[i];
            sumSquares += diff * diff;
        }

        return Math.sqrt(sumSquares);
    }

}
