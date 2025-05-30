package processing;

import java.io.*;
import java.util.*;

public class CSVReader {

    /**
     * Reads a CSV file and returns the data as a 2D array of doubles.
     * 
     * @param filename The path to the CSV file
     * @param hasHeader Whether the CSV file has a header row to skip
     * @param delimiter The delimiter used in the CSV file (e.g., "," or ";")
     * @return A 2D array containing the data from the CSV file
     * @throws IOException If there's an error reading the file
     */
    public static double[][] readCSV(String filename, boolean hasHeader, String delimiter) throws IOException {
        List<double[]> rows = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        
        // Skip header if present
        if (hasHeader && (line = reader.readLine()) != null) {
            // Header line is skipped
        }

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue; // Skip empty lines
            String[] tokens = line.split(delimiter);
            double[] values = new double[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                values[i] = Double.parseDouble(tokens[i].trim());
            }
            rows.add(values);
        }
        reader.close();

        // Convert List to double[][]
        double[][] result = new double[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            result[i] = rows.get(i);
        }

        return result;
    }
    
    /**
     * Reads a CSV file using semicolon as delimiter and assuming it has a header.
     * This is a convenience method for the dataset.csv format.
     * 
     * @param filename The path to the CSV file
     * @return A 2D array containing the data from the CSV file
     * @throws IOException If there's an error reading the file
     */
    public static double[][] readCSV(String filename) throws IOException {
        return readCSV(filename, true, ";");
    }
}


