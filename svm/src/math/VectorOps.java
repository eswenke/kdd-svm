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
        // TODO: Implement dot product calculation
        // Sum of element-wise multiplication: v1[0]*v2[0] + v1[1]*v2[1] + ...
        return 0.0;
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
        // TODO: Implement vector addition
        // Element-wise addition: [v1[0]+v2[0], v1[1]+v2[1], ...]
        return null;
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
        // TODO: Implement vector subtraction
        // Element-wise subtraction: [v1[0]-v2[0], v1[1]-v2[1], ...]
        return null;
    }
    
    /**
     * Multiplies a vector by a scalar value.
     * 
     * @param vector The input vector
     * @param scalar The scalar value
     * @return A new vector with each element multiplied by the scalar
     */
    public static double[] scalarMultiply(double[] vector, double scalar) {
        // TODO: Implement scalar multiplication
        // Multiply each element by scalar: [vector[0]*scalar, vector[1]*scalar, ...]
        return null;
    }
    
    // Additional functions that may be useful for more advanced implementations:
    
    /*
     * Calculates the Euclidean (L2) norm of a vector.
     * Useful for normalization and distance calculations in more complex kernels.
     */
    // public static double norm(double[] vector) { ... }
    
    /*
     * Calculates the Euclidean distance between two vectors.
     * Useful for RBF kernel and other distance-based calculations.
     */
    // public static double distance(double[] v1, double[] v2) { ... }
}
