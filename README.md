# Support Vector Machine Implementation

A comprehensive Java implementation of Support Vector Machine (SVM) with Sequential Minimal Optimization (SMO) algorithm, featuring multiple kernel functions and complete evaluation metrics.

## Overview

This project implements a Support Vector Machine from scratch in Java, demonstrating core machine learning concepts including optimization algorithms, kernel methods, and model evaluation. The implementation features the SMO algorithm for efficient quadratic programming optimization and supports multiple kernel functions for non-linear classification tasks.

The SVM is applied to a binary classification problem using sensor signal data, where the goal is to identify whether a sensor is immersed in water or oil based on signal characteristics.

## Dataset
- https://www.kaggle.com/datasets/mexwell/binary-classification-for-sensor-signals/data

## Tech Stack

- **Language**: Java 8+
- **Build Tools**: Maven, Custom Build Script
- **Dependencies**: Apache Commons Math3, JUnit
- **Architecture**: Modular package-based design

## Key Features

- **SMO Optimization**: Sequential Minimal Optimization algorithm for efficient SVM training
- **Multiple Kernels**: Linear, Polynomial, and RBF (Radial Basis Function) kernel implementations
- **Data Processing**: Complete preprocessing pipeline with normalization and stratified splitting
- **Model Evaluation**: Comprehensive metrics including accuracy, precision, recall, F1-score, and confusion matrix
- **Mathematical Operations**: Custom vector and matrix operations for SVM computations

## Package Architecture

The project follows a modular architecture with specialized packages:

- **`model/`** - Core SVM implementation and kernel functions
  - `SVMModel.java` - Main SVM classifier with SMO optimization
  - `SMOOptimizer.java` - Sequential Minimal Optimization algorithm
  - `LinearKernel.java`, `PolynomialKernel.java`, `RBFKernel.java` - Kernel implementations
  - `SVMKernel.java` - Kernel interface

- **`processing/`** - Data preprocessing and manipulation
  - `CSVReader.java` - Dataset loading and parsing
  - `DataPreprocessor.java` - Feature normalization and data preparation
  - `DataSplitter.java` - Train/validation/test set splitting with stratification

- **`evaluation/`** - Model performance assessment
  - `ModelEvaluator.java` - Comprehensive model evaluation framework
  - `Metrics.java` - Classification metrics calculation

- **`math/`** - Mathematical operations
  - `VectorOps.java` - Vector operations for SVM computations
  - `MatrixOps.java` - Matrix operations and linear algebra

- **`testing/`** - Unit tests and validation
- **`data/`** - Dataset storage

## Running The Project

### Using Maven (Recommended)
```bash
mvn compile exec:java -Dexec.mainClass="SVM"
```

### Using Build Script (Alternative)
If Maven is not available or causing issues, use the provided build script:

```bash
# On Unix/Linux/macOS
./build.sh

# Then run the compiled program
java -cp target SVM
```

The build script compiles all Java files (excluding test files requiring JUnit dependencies) and places them in the `target` directory.

### Manual Compilation
```bash
# Compile all source files
find svm/src -name "*.java" -type f | xargs javac -d target

# Run the main class
java -cp target SVM
```

## Contributors

- **Ethan Swenke**
- **Leah Sarles** 
- **Ronan Valadez**
- **Gannon Bardasian**

