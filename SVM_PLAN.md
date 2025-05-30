# SVM Implementation Plan

## Project Overview
This document outlines a plan for implementing a Support Vector Machine (SVM) from scratch in Java. The SVM will be designed to classify sensor data with 10 feature columns indicating intensity of a sensor submersed in either oil or water (binary classification).

## Understanding the Problem

### Dataset Structure
- 10 columns of sensor intensity data (features)
- 1 column for binary classification (oil or water)
- Linear kernel will be sufficient for this classification task

## Implementation Approach

### Phase 1: Data Handling and Preprocessing
1. **Data Loading**
   - Extend the existing `CSVReader` class to handle the specific dataset format
   - Add functionality to split data into features and labels
   - Implement data normalization/standardization (crucial for SVM performance)

2. **Data Preprocessing**
   - Create a `DataPreprocessor` class to:
     - Normalize features (scale to [0,1] or standardize to mean=0, std=1)
     - Split data into training and testing sets
     - Handle any missing values if present

### Phase 2: Core SVM Implementation

1. **Vector and Matrix Operations**
   - Create utility classes for vector and matrix operations:
     - `VectorOps.java`: dot products, vector addition, scalar multiplication
     - `MatrixOps.java`: matrix multiplication, transpose, etc.

2. **Linear Kernel Implementation**
   - Create a `Kernel` interface and implement the linear kernel
   - Design for extensibility (to add other kernels later if needed)

3. **SVM Optimizer**
   - Implement Sequential Minimal Optimization (SMO) algorithm:
     - `SMOOptimizer.java`: Core optimization algorithm
     - Implement Lagrange multipliers calculation
     - Handle KKT conditions

4. **SVM Model**
   - Create the main `SVMModel` class:
     - Training method using the optimizer
     - Prediction method for new data points
     - Methods to calculate the hyperplane (w, b)
     - Support vector identification

### Phase 3: Evaluation and Visualization

1. **Model Evaluation**
   - Implement metrics:
     - Precision, Recall, F1 score
     - Confusion matrix
     - Accuracy on testing set

2. **Optional: Simple Visualization**
   - If time permits, create simple visualization of the decision boundary
   - Could use external libraries or generate data for plotting elsewhere

## File Structure

```
svm/
├── src/
│   ├── SVM.java                [Existing - Main class]
│   ├── processing/
│   │   ├── CSVReader.java
│   │   ├── DataPreprocessor.java
│   │   └── DataSplitter.java
│   ├── model/
│   │   ├── SVMModel.java
│   │   ├── SMOOptimizer.java
│   │   └── Kernel.java
│   ├── math/
│   │   ├── VectorOps.java
│   │   └── MatrixOps.java
│   └── evaluation/
│       ├── Metrics.java
│       └── ModelEvaluator.java
```

## Implementation Order

1. Start with data handling and preprocessing:
   - Extend `CSVReader.java`
   - Implement `DataPreprocessor.java`

2. Implement the math utilities:
   - `VectorOps.java`
   - `MatrixOps.java`

3. Implement the core SVM components:
   - `Kernel.java` (interface + linear implementation)
   - `SMOOptimizer.java`
   - `SVMModel.java`

4. Implement evaluation components:
   - `Metrics.java`
   - `CrossValidator.java`

5. Update the main `SVM.java` class to use all components

## Research Topics

1. **Sequential Minimal Optimization (SMO)**
   - The standard algorithm for SVM training
   - Platt's paper: "Fast Training of Support Vector Machines using Sequential Minimal Optimization"
   - SMO essentially just picks two parameters to optimize at a time, and solves this problem analytically by picking these parameters through heuristics. It repeats this process until all
   parameters are optimized and converge. The idea of an SVM is to find the optimal values for the hyperplan and the margins, and SMO is a way to do this more efficiently without solving all parameters at once.

2. **Lagrangian Duality and KKT Conditions**
   - Mathematical foundation of SVM optimization. Lagrange multipliers are used to find the optimal solution to the SVM problem, simplifying the problem into an alternative form that is easier
   to solve.
   - Understanding the dual form of the SVM problem. Instead of looking directly for the hyperplane,
   we look for the training point that are most important (the support vectors). The 'dual form' is what allows you to use the kernel trick, which transforms the data into higher dimensions in order to allow a hyperplane / support vectors to exist.

3. **Linear Algebra for SVM**
   - Dot products, vector operations
   - Hyperplane mathematics (basic mathematics needed throughout the process)

4. **SVM Regularization**
   - The C parameter (regularization strength). This controls how much we penalize misclassifications. Used in the lagrange multipliers as an upper bound and in SMO
   to constrain how much each alpha is adjusted.
   - Soft margin vs. hard margin. Soft margin allows for some misclassifications, while hard margin does not.

## Testing Strategy

1. Unit test each component:
   - Test vector and matrix operations
   - Test kernel calculations
   - Test optimizer with simple cases

2. Integration test:
   - Test with small synthetic datasets with known solutions
   - Verify against expected decision boundaries

3. Performance test:
   - Test with the full sensor dataset
   - Measure training time and prediction accuracy

## Resources

- "Support Vector Machines" by Cristianini and Shawe-Taylor
- "Sequential Minimal Optimization: A Fast Algorithm for Training Support Vector Machines" by John Platt
- "Pattern Recognition and Machine Learning" by Christopher Bishop (Chapter on SVMs)
- The resources listed in the project README.md

## Next Steps

1. Acquire and understand the sensor dataset
2. Implement the data preprocessing components
3. Begin implementing the core SVM algorithm
