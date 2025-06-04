// main SVM class in the default package
// no package declaration needed for the top-level src directory

import java.io.IOException;
import processing.CSVReader;

public class SVM {

    public static void main(String[] args) throws IOException {
        // Using the static method directly without creating an instance
        double[][] data = CSVReader.readCSV("svm/src/data/dataset.csv"); // read some data

        // verify data dims
        System.out.println("Loaded data with " + data.length + " rows and " + 
                          (data.length > 0 ? data[0].length : 0) + " columns");

        // Main SVM logic will go here


    }
}
