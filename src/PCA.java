import java.util.Arrays;

public class PCA {

    private double[][] data; // rows: observations, cols: variables

    public PCA(double[][] data) {
        this.data = data;
    }

    public Result runPCA(int numComponents) {
        double[][] meanCentered = meanCenter(data);
        double[][] covarianceMatrix = computeCovarianceMatrix(meanCentered);
        EigenDecomposition eig = new EigenDecomposition(covarianceMatrix);
        
        // Select top N eigenvectors
        double[][] topEigenvectors = new double[numComponents][];
        for (int i = 0; i < numComponents; i++) {
            topEigenvectors[i] = eig.getEigenvector(i);
        }

        // Project data onto new basis
        double[][] transformedData = multiply(meanCentered, transpose(topEigenvectors));
        return new Result(transformedData, topEigenvectors, eig.getEigenvalues());
    }

    private double[][] meanCenter(double[][] data) {
        int rows = data.length, cols = data[0].length;
        double[] means = new double[cols];

        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                means[j] += data[i][j];
            }
            means[j] /= rows;
        }

        double[][] centered = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                centered[i][j] = data[i][j] - means[j];
            }
        }
        return centered;
    }

    private double[][] computeCovarianceMatrix(double[][] data) {
        int n = data.length;
        int dim = data[0].length;
        double[][] cov = new double[dim][dim];

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                double sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += data[k][i] * data[k][j];
                }
                cov[i][j] = sum / (n - 1); // Sample covariance
            }
        }
        return cov;
    }

    private double[][] multiply(double[][] A, double[][] B) {
        int aRows = A.length, aCols = A[0].length, bCols = B[0].length;
        double[][] result = new double[aRows][bCols];

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {
                for (int k = 0; k < aCols; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return result;
    }

    private double[][] transpose(double[][] A) {
        int rows = A.length, cols = A[0].length;
        double[][] T = new double[cols][rows];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                T[j][i] = A[i][j];
        return T;
    }

    public static class Result {
        public double[][] transformedData;
        public double[][] principalComponents;
        public double[] eigenvalues;

        public Result(double[][] transformedData, double[][] principalComponents, double[] eigenvalues) {
            this.transformedData = transformedData;
            this.principalComponents = principalComponents;
            this.eigenvalues = eigenvalues;
        }
    }
}
