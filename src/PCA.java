package your.package;

import org.apache.commons.math3.linear.*;

public class PCA {
    public static RealMatrix computeCovarianceMatrix(double[][] data) {
        RealMatrix matrix = MatrixUtils.createRealMatrix(data);
        RealMatrix meanAdjusted = matrix.copy();

        // Center the data (subtract column mean)
        for (int col = 0; col < matrix.getColumnDimension(); col++) {
            double mean = 0;
            for (int row = 0; row < matrix.getRowDimension(); row++) {
                mean += matrix.getEntry(row, col);
            }
            mean /= matrix.getRowDimension();
            for (int row = 0; row < matrix.getRowDimension(); row++) {
                meanAdjusted.addToEntry(row, col, -mean);
            }
        }

        // Covariance matrix = (X^T * X) / (n - 1)
        RealMatrix transposed = meanAdjusted.transpose();
        RealMatrix covariance = transposed.multiply(meanAdjusted).scalarMultiply(1.0 / (matrix.getRowDimension() - 1));
        return covariance;
    }

    public static void runPCA(double[][] data) {
        RealMatrix cov = computeCovarianceMatrix(data);
        EigenDecomposition ed = new EigenDecomposition(cov);

        System.out.println("Eigenvalues:");
        for (double val : ed.getRealEigenvalues()) {
            System.out.println(val);
        }

        System.out.println("\nEigenvectors:");
        RealMatrix V = ed.getV();
        for (int i = 0; i < V.getColumnDimension(); i++) {
            System.out.println("PC" + (i + 1) + ": " + V.getColumnVector(i));
        }
    }
}
