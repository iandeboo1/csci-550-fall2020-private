import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB_Scan_Algorithm {

    private final Map<Integer, Cluster> clusterSet;
    private final Cluster corePoints;
    private final Cluster borderPoints;
    private final Cluster noisePoints;

    public DB_Scan_Algorithm() {
        clusterSet = new HashMap<>();
        corePoints = new Cluster();
        borderPoints = new Cluster();
        noisePoints = new Cluster();
    }

    public void dbScan(List<Point> D, double E, int minpts) {
        for (Point point : D) {
            findNeighbors(point, D, E);
            point.setIdentifier(0);
            if (point.getNeighborhoodSize() >= minpts) {
                corePoints.addPoint(point);
            }
        }
        int k = 0;
        for (Point corePoint : corePoints.getPoints()) {
            if (corePoint.getIdentifier() == 0) {
                k++;
                corePoint.setIdentifier(k);
                Cluster c = new Cluster();
                c.addPoint(corePoint);
                clusterSet.put(k, c);
                densityConnected(corePoint, k);
            }
        }
        for (Point point : D) {
            if (point.getIdentifier() == 0) {
                noisePoints.addPoint(point);
            }
        }
        for (Point point : D) {
            if (!corePoints.contains(point) && !noisePoints.contains(point)) {
                borderPoints.addPoint(point);
            }
        }
    }

    public void findNeighbors(Point point, List<Point> D, double E) {
        for (Point eachOtherPoint : D) {
            if (point.getDistanceBetween(eachOtherPoint) < E && !point.equals(eachOtherPoint)) {
                   point.addNeighbor(eachOtherPoint);
            }
        }
    }

    public void densityConnected(Point point, int k) {
        for (Point neighbor : point.getNeighbors()) {
            neighbor.setIdentifier(k);
            clusterSet.get(k).addPoint(neighbor);
            if (corePoints.contains(neighbor)) {
                densityConnected(neighbor, k);
            }
        }
    }

    public Map<Integer, Cluster> getClusterSet() {
        return clusterSet;
    }

    public Cluster getCorePoints() {
        return corePoints;
    }

    public Cluster getBorderPoints() {
        return borderPoints;
    }

    public Cluster getNoisePoints() { return noisePoints; }

}
