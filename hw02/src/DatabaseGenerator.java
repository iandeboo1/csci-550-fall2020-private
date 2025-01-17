import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class DatabaseGenerator {

    public static final String delimiter = ",";
    private final List<Point> database;

    public DatabaseGenerator(String csvFile) {
        database = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line;
            String[] lineArray;                                         //array of columns as strings
            while ((line = br.readLine()) != null) {                    //reads through file line by line
                lineArray = line.split(delimiter);                      //divides line into columns, based on commas
                if (lineArray.length == 4) {
                    Point point = new Point(null);
                    for (String dimensionValue : lineArray) {
                        point.addDimensionValue(Double.parseDouble(dimensionValue));
                    }
                    database.add(point);
                }
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public List<Point> getDatabase() {
        return database;
    }

}
