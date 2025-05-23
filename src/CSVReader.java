import java.io.*;
import java.util.*;

public class CSVReader {

    public static List<DataPoint> readCSV(String filePath) {
        List<DataPoint> dataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // skip header
                    continue;
                }

                String[] tokens = line.split(",");
                if (tokens.length >= 3) {
                    double col1 = Double.parseDouble(tokens[0]);
                    double col2 = Double.parseDouble(tokens[1]);
                    double col3 = Double.parseDouble(tokens[2]);
                    dataList.add(new DataPoint(col1, col2, col3));
                }
            }

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return dataList;
    }
}
