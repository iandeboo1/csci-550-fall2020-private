import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        final String csvFile = "iris.csv";
        int k = 2;
        double E = 0.25;

        DatabaseGenerator dbg = new DatabaseGenerator(csvFile);
        List<List<Double>> database = dbg.getDatabase();

        K_Means_Algorithm kma = new K_Means_Algorithm();
        Map<List<Double>, List<List<Double>>> clusters = kma.k_means(database, k, E);

    }

}
