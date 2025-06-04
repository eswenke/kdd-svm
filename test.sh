#!/bin/bash
# Script for building and running test files

echo "Building and running SVM tests..."

# Create target directory if it doesn't exist
mkdir -p target

# Compile all Java files except MetricsTesting (because i don't have junit deps installed yet oops)
find svm/src -name "*.java" ! -name "MetricsTesting.java" -type f | xargs javac -d target

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "Compilation complete."
    
    # Ask which test to run
    echo ""
    echo "Available tests:"
    echo "1. DataPreprocessorTest"
    echo "2. DataSplitterTest"
    echo "3. All tests"
    echo ""
    read -p "Enter test number to run (or press Enter to exit): " choice
    
    case $choice in
        1)
            echo "Running DataPreprocessorTest..."
            java -cp target testing.DataPreprocessorTest
            ;;
        2)
            echo "Running DataSplitterTest..."
            java -cp target testing.DataSplitterTest
            ;;
        3)
            echo "Running all tests..."
            echo ""
            echo "=== DataPreprocessorTest ==="
            java -cp target testing.DataPreprocessorTest
            echo ""
            echo "=== DataSplitterTest ==="
            java -cp target testing.DataSplitterTest
            ;;
        *)
            echo "No test selected. Exiting."
            ;;
    esac
else
    echo "Compilation failed."
fi
