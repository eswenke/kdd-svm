package model;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Simplified Sequential Minimal Optimization (SMO) algorithm implementation for SVM training.
 * 
 * This is a basic implementation of SMO for a linear SVM. It solves the quadratic programming (QP) 
 * problem that arises during SVM training by breaking it down into smaller sub-problems.
 * 
 * Reference: "Fast Training of Support Vector Machines using Sequential Minimal Optimization"
 * by John Platt.
 */
public class SMOOptimizer {
    
    // Regularization parameter (controls trade-off between margin and training error)
    private double C;
    
    // Maximum number of iterations
    private int maxIterations;
    
    // Kernel function to use (no kernel just means 'linear' kernel. we must make one)
    private SVMKernel kernel;
    
    /**
     * Creates an SMO optimizer with the specified parameters.
     * 
     * @param C Regularization parameter (controls how much room we leave for error)
     * @param maxIterations Maximum number of iterations
     * @param kernel Kernel function to use (linear most likely, rbf or poly if necessary)
     */
    public SMOOptimizer(double C, int maxIterations, SVMKernel kernel) {
        this.C = C;
        this.maxIterations = maxIterations;
        this.kernel = kernel;
    }
    
    /**
     * Creates an SMO optimizer with default C value of 1.0.
     * 
     * @param maxIterations Maximum number of iterations
     * @param kernel Kernel function to use
     */
    public SMOOptimizer(int maxIterations, SVMKernel kernel) {
        this(1.0, maxIterations, kernel);
    }
    
    /**
     * Optimizes the SVM model using a simplified SMO algorithm.
     * 
     * @param X Training features
     * @param y Training labels (should be +1 or -1)
     * @return An array containing the Lagrange multipliers and bias term
     */
    public Object[] optimize(double[][] X, double[] y) {
        // EQUATIONS:
        // -----------------------
        // 1. SVM Decision Function (used for predictions and error calculation):
        //    f(x) = Σ αᵢ·yᵢ·K(xᵢ, x) + b
        //    where K is the kernel function (in simple case, dot product: K(x,z) = x·z)
        
        // 2. Error Calculation:
        //    E_i = f(x_i) - y_i
        
        // 3. Alpha Update Steps:
        //    a. Compute η = 2·K(x₁,x₂) - K(x₁,x₁) - K(x₂,x₂)
        //    b. Calculate unconstrained α₂_new = α₂ - y₂·(E₁ - E₂)/η
        //    c. Compute bounds for α₂_new:
        //       If y₁ ≠ y₂: L = max(0, α₂ - α₁), H = min(C, C + α₂ - α₁)
        //       If y₁ = y₂: L = max(0, α₁ + α₂ - C), H = min(C, α₁ + α₂)
        //    d. Clip α₂_new to bounds: α₂_new = min(H, max(L, α₂_new))
        //    e. Update α₁_new = α₁ + y₁·y₂·(α₂ - α₂_new)
        
        // 4. Bias Update:
        //    b_new = b - E₁ - y₁·(α₁_new - α₁)·K(x₁,x₁) - y₂·(α₂_new - α₂)·K(x₁,x₂)
        
        // --- IMPLEMENTATION ---
        // initialize alpha array to zeros, bias to small values, and prepare error cache array
        double[] alphas = new double[y.length];
        double[] errors = new double[y.length];
        double bias = 0.0;
        
        // initialize alphas with small random values to break symmetry
        for (int i = 0; i < y.length; i++) {
            alphas[i] = Math.random() * 0.01;
        }
        
        // initialize error cache
        for (int i = 0; i < y.length; i++) {
            errors[i] = computeOutput(X[i], X, y, alphas, bias) - y[i];
        }
        
        System.out.println("Starting SMO optimization with C=" + C + ", maxIterations=" + maxIterations);
        int num_changed = 0;
        int below_10 = 0;
        
        // main optimization loop with early stopping if no progress is made
        for (int iter = 0; iter < maxIterations; iter++) {
            num_changed = 0;

            // random selection for other iterations (batches in 100 for our dataset)
            for (int attempt = 0; attempt < Math.min(100, y.length); attempt++) {
                int i = (int) (Math.random() * y.length);
                int j = (int) (Math.random() * y.length);
                
                // ensure different indices
                while (j == i) {
                    j = (int) (Math.random() * y.length);
                }
                
                double oldBias = bias;
                bias = optimizePair(i, j, X, y, alphas, errors, bias);
                if (bias != oldBias) {
                    num_changed++;
                }
            }

            System.out.println("Iteration " + iter + ": Random selection, " + num_changed + " alphas changed");
            
            // for early stopping if little progress is made
            if (num_changed < 10) {
                below_10++;
            }

            if (below_10 > 5) {
                System.out.println("Early stopping at iteration " + iter + " - less than 10 alphas changed for 5 iterations");
                break;
            }
        }
        
        // count support vectors
        int supportVectorCount = 0;
        for (int i = 0; i < alphas.length; i++) {
            if (Math.abs(alphas[i]) > 1e-5) {
                supportVectorCount++;
            }
        }
        System.out.println("Optimization completed with " + supportVectorCount + " support vectors out of " + y.length + " training examples");

        return new Object[] {alphas, bias};
    }
    
    /**
     * Optimizes a pair of Lagrange multipliers (alphas)
     * 
     * @param i First index
     * @param j Second index
     * @param X Training data
     * @param y Training labels
     * @param alphas Current alpha values
     * @param errors Error cache
     * @param b Current bias
     * @return the new bias
     */
    private double optimizePair(int i, int j, double[][] X, double[] y, double[] alphas, 
                                double[] errors, double bias) {
        // calculate the errors and cache them immediately: E_i = f(x_i) - y_i
        double err_1 = computeOutput(X[i], X, y, alphas, bias) - y[i];
        double err_2 = computeOutput(X[j], X, y, alphas, bias) - y[j]; // Fixed: using y[j] instead of y[i]
        errors[i] = err_1;
        errors[j] = err_2;

        // grab old alphas for future bias update
        double old_a1 = alphas[i];
        double old_a2 = alphas[j];

        // y2 and y2 via labels with corresponding i, j indices
        double y1 = y[i];
        double y2 = y[j];

        // x1 and x2 via X[i_r][i_c] and X[_r][_c]
        // kernel(x1, x2), etc., via compute with ^ indices
        double sim_11 = kernel.compute(X[i], X[i]);
        double sim_12 = kernel.compute(X[i], X[j]);
        double sim_22 = kernel.compute(X[j], X[j]);

        // ALPHA UPDATE:
        // --------------------------------------------
        // compute η = 2·K(x₁,x₂) - K(x₁,x₁) - K(x₂,x₂)
        double eta = 2 * sim_12 - sim_11 - sim_22;
        
        // if eta is not positive, we can't make progress with this pair
        if (eta >= -1e-10) { // using a small negative threshold to account for numerical precision
            return bias; // return without updating
        }

        // calculate unconstrained α₂_new = α₂ - y₂·(E₁ - E₂)/η
        double new_a2 = old_a2 - y2 * (err_1 - err_2) / eta;
        double new_a1 = 0;

        // compute bounds for α₂_new:
        //  - If y₁ ≠ y₂: L = max(0, α₂ - α₁), H = min(C, C + α₂ - α₁)
        //  - If y₁ = y₂: L = max(0, α₁ + α₂ - C), H = min(C, α₁ + α₂)
        double L = 0.0;
        double H = 0.0;
        if (y1 != y2) {
            L = max(0, old_a2 - old_a1);
            H = min(C, C + old_a2 - old_a1);
        } 
        else if (y1 == y2) {
            L = max(0, old_a1 + old_a2 - C);
            H = min(C, old_a1 + old_a2);
        }

        // clip α₂_new to bounds: α₂_new = min(H, max(L, α₂_new))
        new_a2 = min(H, max(L, new_a2));

        // update α₁_new = α₁ + y₁·y₂·(α₂ - α₂_new)
        new_a1 = old_a1 + y1 * y2 * (old_a2 - new_a2);
        
        // check if we're making significant progress (avoid numerical issues)
        double tolerance = 1e-5;
        if (Math.abs(new_a2 - old_a2) < tolerance) {
            return bias; // no significant change, return without updating
        }
        
        // clip alpha1 to [0, C] bounds
        new_a1 = Math.max(0, Math.min(C, new_a1));
        
        // update the alpha values in the array
        alphas[i] = new_a1;
        alphas[j] = new_a2;

        // update bias        
        // bias_new = b - E₁ - y₁·(α₁_new - α₁)·K(x₁,x₁) - y₂·(α₂_new - α₂)·K(x₁,x₂)
        double bias_new = bias - err_1 - y1 * (new_a1 - old_a1) * sim_11 - y2 * ((new_a2 - old_a2)) * sim_12;
        
        // update error cache
        // only update errors for the two changed alphas and any other alphas that need recalculation
        // this is more efficient than recalculating all errors
        for (int k = 0; k < errors.length; k++) {
            if (k == i || k == j) {
                // always update errors for the two alphas we just changed
                errors[k] = computeOutput(X[k], X, y, alphas, bias_new) - y[k];
            } else if (alphas[k] > 0 && alphas[k] < C) {
                // also update errors for non-bound support vectors
                errors[k] = computeOutput(X[k], X, y, alphas, bias_new) - y[k];
            }
        }
        
        return bias_new;
    }

    /**
     * Computes the SVM output for a given input vector. determines what side of the decision
     * boundary a point falls on (classifies new data points)
     * 
     * @param x Input vector
     * @param X Training features
     * @param y Training labels
     * @param alphas Lagrange multipliers
     * @param b Bias term
     * @return The SVM output
     */
    private double computeOutput(double[] x, double[][] X, double[] y, double[] alphas, double b) {
        double sum = 0.0;
        for (int i = 0; i < y.length; i++) {
            // f(x) = sum(alpha_i * y_i * K(x_i, x)) + b
            sum += alphas[i] * y[i] * kernel.compute(X[i], x);
        }

        return sum + b;
    }
    
    
    // === ADVANCED OPTIMIZATION TECHNIQUES (REFERENCE) ===
    
    /*
     * === ADVANCED POINT SELECTION STRATEGIES ===
     * 
     * 1. First Point Selection (I1):
     *    - Scan through non-bound examples (0 < alpha < C) to find points that violate KKT conditions
     *    - If none found, scan through all points
     *    - KKT violation check: |y_i * f(x_i) - 1| > tolerance
     * 
     * 2. Second Point Selection (I2):
     *    - Choose point that maximizes |E_i1 - E_i2|
     *    - Ensures maximum step size in optimization
     *    
     * Reference implementation:
     * private int[] selectWorkingSet(double[] alphas, double[][] X, double[] y, double[] errors) {
     *     // First find i1 - point that violates KKT conditions
     *     int i1 = findKKTViolator(alphas, y, errors);
     *     
     *     // Then find i2 - point with maximum |E_i1 - E_i2|
     *     int i2 = findMaximumViolatingPair(i1, alphas, errors);
     *     
     *     return new int[] {i1, i2};
     * }
     * 
     * === KKT CONDITION CHECKING ===
     * 
     * KKT Conditions (more detailed):
     * - If α_i = 0: y_i·f(x_i) ≥ 1     (point is correctly classified with margin)
     * - If 0 < α_i < C: y_i·f(x_i) = 1  (point is exactly on margin)
     * - If α_i = C: y_i·f(x_i) ≤ 1     (point may be within margin or misclassified)
     * 
     * A point violates KKT conditions when:
     * - α_i = 0 and y_i·f(x_i) < 1
     * - 0 < α_i < C and y_i·f(x_i) ≠ 1
     * - α_i = C and y_i·f(x_i) > 1
     * 
     * Code example:
     * private boolean checkKKTViolation(double alpha, double y, double fx, double tolerance) {
     *     if (alpha < 1e-8) {  // Effectively zero
     *         return y * fx < 1 - tolerance;
     *     } else if (alpha > C - 1e-8) {  // Effectively at C
     *         return y * fx > 1 + tolerance;
     *     } else {
     *         return Math.abs(y * fx - 1) > tolerance;
     *     }
     * }
     * 
     * === SHRINKING HEURISTIC ===
     * 
     * Shrinking speeds up optimization by focusing on non-bound examples:
     * - Skip optimization for points where alphas are at bounds (0 or C)
     *   and have remained unchanged for several iterations
     * - Periodically check if bound examples still satisfy KKT conditions
     * 
     * === ADVANCED BIAS UPDATES ===
     * 
     * More precise bias selection:
     * - If 0 < α₁_new < C: b_new = b₁_new
     * - Else if 0 < α₂_new < C: b_new = b₂_new
     * - Else: b_new = (b₁_new + b₂_new)/2
     * 
     * Where:
     * - b₁_new = b - E₁ - y₁·(α₁_new - α₁)·K(x₁,x₁) - y₂·(α₂_new - α₂)·K(x₁,x₂)
     * - b₂_new = b - E₂ - y₁·(α₁_new - α₁)·K(x₁,x₂) - y₂·(α₂_new - α₂)·K(x₂,x₂)
     */

    
    /*
     * Maintains an error cache to speed up computations.
     * Useful for larger datasets where recomputing outputs is expensive.
     */
    // private double[] initializeErrorCache(double[][] X, double[] y, double[] alphas, double b) { ... }
}
