#!/bin/bash
# use if maven giving issues

echo "Building SVM project..."

# Create target directory if it doesn't exist
mkdir -p target

# Compile all Java files except MetricsTesting (because i don't have junit deps installed yet oops)
find svm/src -name "*.java" ! -name "MetricsTesting.java" -type f | xargs javac -d target

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "Compilation complete. Run with: java -cp target SVM"
else
    echo "Compilation failed."
fi
