import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polygon {

    private final int id;
    private final List<RandomPoint> interiorPoints;
    double minX = 0;
    double maxX = 0;
    double minY = 0;
    double maxY = 0;

    public Polygon(int id, List<RandomPoint> interiorPoints) {
        this.id = id;
        this.interiorPoints = interiorPoints;
        createBoundaries();
    }

    private void createBoundaries() {
        for (RandomPoint pt : interiorPoints) {
            if (pt.getX() < minX) {
                minX = pt.getX();
            }
            if (pt.getX() > maxX) {
                maxX = pt.getX();
            }
            if (pt.getY() < minY) {
                minY = pt.getY();
            }
            if (pt.getY() > maxY) {
                maxY = pt.getY();
            }
        }
    }

    public boolean insideBoundary(RandomPoint pt) {
        if (pt.getX() < minX || pt.getX() > maxX) {
            return false;
        } else {
            if (pt.getY() > maxY || pt.getY() < minY) {
                return false;
            } else {
                return true;
            }
        }
    }

    public int getId() { return id; }

    public List<RandomPoint> getInteriorPoints() { return interiorPoints; }

}
