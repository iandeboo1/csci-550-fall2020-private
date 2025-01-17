import java.util.ArrayList;
import java.util.List;

public class Cluster {

    private Point centroid;
    private List<Point> points;

    public Cluster() {
        points = new ArrayList<>();
    }

    public Point getCentroid() {
        return centroid;
    }

    public void setCentroid(Point p) { centroid = p; }

    public void addPoint(Point p) {
        points.add(p);
    }

    public List<Point> getPoints() {
        return points;
    }

    public void clearPoints() { points = new ArrayList<>(); }

    public boolean contains(Point p) {
        for (Point point : points) {
            if (point.equals(p)) {
                return true;
            }
        }
        return false;
    }

}
