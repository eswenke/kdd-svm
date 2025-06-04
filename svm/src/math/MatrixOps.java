package math;

/**
 * Matrix operations utility class for SVM implementation.
 * 
 * This class provides basic matrix operations needed for SVM calculations:
 * - Matrix-vector operations
 */
public class MatrixOps {

    /**
     * Multiplies a matrix by a vector.
     * 
     * @param matrix The input matrix (m x n)
     * @param vector The input vector (n)
     * @return The resulting vector (m)
     * @throws IllegalArgumentException if dimensions are incompatible
     */
    public static double[] multiply(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        if (vector.length != cols) {
            throw new IllegalArgumentException("Matrix column count must match vector length.");
        }

        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            double sum = 0.0;
            for (int j = 0; j < cols; j++) {
                sum += matrix[i][j] * vector[j];
            }
            result[i] = sum;
        }

        return result;
    }


    // Additional functions that may be useful for more advanced implementations:
    
    /*
     * Multiplies two matrices.
     * Useful for batch processing or when working with kernel matrices.
     */
    public static double[][] multiply(double[][] a, double[][] b) {
        int aRows = a.length;
        int aCols = a[0].length;
        int bRows = b.length;
        int bCols = b[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("Matrix A's columns must match Matrix B's rows.");
        }

        double[][] result = new double[aRows][bCols];

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {
                double sum = 0.0;
                for (int k = 0; k < aCols; k++) {
                    sum += a[i][k] * b[k][j];
                }
                result[i][j] = sum;
            }
        }

        return result;
    }


    /*
     * Transposes a matrix.
     * Useful for certain matrix operations and kernel calculations.
     */
    public static double[][] transpose(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        double[][] result = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }

        return result;
    }


    /*
     * Adds two matrices element-wise.
     * Useful for more complex matrix operations.
     */
    public static double[][] add(double[][] a, double[][] b) {
        int rows = a.length;
        int cols = a[0].length;

        if (b.length != rows || b[0].length != cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions.");
        }

        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }

        return result;
    }


    /*
     * Subtracts the second matrix from the first element-wise.
     * Useful for more complex matrix operations.
     */
    // public static double[][] subtract(double[][] a, double[][] b) { ... }
    
    /*
     * Multiplies a matrix by a scalar value.
     * Useful for scaling operations in more complex implementations.
     */
    public static double[][] scalarMultiply(double[][] matrix, double scalar) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix[i][j] * scalar;
            }
        }

        return result;
    }

}
