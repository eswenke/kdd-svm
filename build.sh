#!/bin/bash
# use if maven giving issues

echo "Building SVM project..."

# Create target directory if it doesn't exist
mkdir -p target

# Compile all Java files
javac -d target svm/src/*.java svm/src/*/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "Compilation complete. Run with: java -cp target SVM"
else
    echo "Compilation failed."
fi
