// main SVM class in the default package
// no package declaration needed for the top-level src directory

import java.io.IOException;
import processing.CSVReader;

public class SVM {

    public static void main(String[] args) throws IOException {
        CSVReader reader = new CSVReader();
        double[][] data = reader.readCSV("somedataset.csv"); // read some data

        // branch init for processing

        // Main SVM logic will go here
        System.out.println("Loaded data with " + data.length + " rows and " + 
                          (data.length > 0 ? data[0].length : 0) + " columns");
    }
}
