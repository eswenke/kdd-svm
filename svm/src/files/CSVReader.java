package files;

import java.io.*;
import java.util.*;


public class CSVReader {

        public static double[][] readCSV(String filename) throws IOException {
            List<double[]> rows = new ArrayList<>();

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip empty lines
                String[] tokens = line.split(",");
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
    }


