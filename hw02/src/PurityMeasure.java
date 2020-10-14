import java.util.List;
import java.util.Map;

public class PurityMeasure {

    private final int[][] contingencyTable;
    private final Map<Integer, Cluster> expResults;
    private final List<Polygon> truth;
    private final List<Point> dataset;

    public PurityMeasure(Map<Integer, Cluster> expResults, List<Polygon> truth, List<Point> dataset) {
        contingencyTable = new int[expResults.size()][truth.size()];
        this.expResults = expResults;
        this.truth = truth;
        this.dataset = dataset;
        fillContTable();
    }

    public void fillContTable() {
        for (Point pt : dataset) {
            int clusterLabel = 0;
            int partitionLabel = 0;
            clusterLoop:
            for (Map.Entry<Integer, Cluster> cluster : expResults.entrySet()) {
                for (Point p : cluster.getValue().getPoints()) {
                    if (pt.equals(p)) {
                        clusterLabel = cluster.getKey();
                        break clusterLoop;
                    }
                }
            }
            partitionLoop:
            for (Polygon poly : truth) {
                for (Point p : poly.getInteriorPoints()) {
                    if (pt.equals(p)) {
                        partitionLabel = poly.getId();
                        break partitionLoop;
                    }
                }
            }
            if (clusterLabel != 0 && partitionLabel != 0) {
                contingencyTable[clusterLabel - 1][partitionLabel - 1]++;
            }
        }
    }

    public double getPurity() {
        int purity = 0;
        for (int i = 0; i < expResults.size(); i++) {
            int maxSimilarity = 0;
            for (int j = 0; j < truth.size(); j++) {
                if (contingencyTable[i][j] > maxSimilarity) {
                    maxSimilarity = contingencyTable[i][j];
                }
            }
            purity = purity + maxSimilarity;
        }
        return (double)(purity / dataset.size());
    }

}
