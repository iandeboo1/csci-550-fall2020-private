import java.util.List;

public class Cluster {

    private Point centroid;
    private List<Point> points;

    public Cluster() {}

    public Point getCentroid() {
        return centroid;
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public List<Point> getPoints() {
        return points;
    }

    public boolean contains(Point p) {
        for (Point point : points) {
            if (point.equals(p)) {
                return true;
            }
        }
        return false;
    }

    public void remove(Point p) {
        points.remove(p);
    }

}
