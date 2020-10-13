import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polygon {

    private final int id;
    private final List<Point> interiorPoints;
    double minX = 0;
    double maxX = 0;
    double minY = 0;
    double maxY = 0;

    public Polygon(int id, List<Point> interiorPoints) {
        this.id = id;
        this.interiorPoints = interiorPoints;
        createBoundaries();
    }

    private void createBoundaries() {
        for (Point pt : interiorPoints) {
            if (pt.getDimensionValues().get(0) < minX) {
                minX = pt.getDimensionValues().get(0);
            }
            if (pt.getDimensionValues().get(0) > maxX) {
                maxX = pt.getDimensionValues().get(0);
            }
            if (pt.getDimensionValues().get(1) < minY) {
                minY = pt.getDimensionValues().get(1);
            }
            if (pt.getDimensionValues().get(1) > maxY) {
                maxY = pt.getDimensionValues().get(1);
            }
        }
    }

    public boolean insideBoundary(Point pt) {
        if (pt.getDimensionValues().get(0) < minX || pt.getDimensionValues().get(0) > maxX) {
            return false;
        } else {
            if (pt.getDimensionValues().get(1) > maxY || pt.getDimensionValues().get(1) < minY) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean contains(Point pt) {
        for (Point truthPoint : interiorPoints) {
            return pt.equals(truthPoint);
        }
        return false;
    }

    public List<Point> getInteriorPoints() { return interiorPoints; }

    public int getId() { return id; }

}
