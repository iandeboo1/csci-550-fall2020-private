import java.util.ArrayList;
import java.util.List;

public class Point implements Comparable<Point> {

    private final List<Double> dimensionValues;
    private final List<Point> neighbors;
    private int identifier;
    private boolean visited;

    public Point(List<Double> values) {
        if (values == null) {
            dimensionValues = new ArrayList<>();
        } else {
            dimensionValues = values;
        }
        neighbors = new ArrayList<>();
        visited = false;
    }

    public void setIdentifier(int id) {
        identifier = id;
    }

    public int getIdentifier() {
        return identifier;
    }

    public int getNeighborhoodSize() { return neighbors.size(); }

    public void setVisited(boolean status) { visited = status; }

    public boolean getVisited() {return visited; }

    public List<Point> getNeighbors() { return neighbors; }

    public void addNeighbor(Point p) { neighbors.add(p); }

    public List<Double> getDimensionValues() { return dimensionValues; }

    public void addDimensionValue(Double value) { dimensionValues.add(value); }

    public boolean isNull() { return dimensionValues.isEmpty(); }

    public boolean equals(Point other) {
        List<Double> otherDimValues = other.getDimensionValues();
        for (int i = 0; i < dimensionValues.size(); i++) {
            if (!dimensionValues.get(i).equals(otherDimValues.get(i))) {
                return false;
            }
        }
        return true;
    }

    public double getDistanceBetween(Point p) {
        double cumulativeDist = 0;
        List<Double> pDimValues = p.getDimensionValues();
        for (int i = 0; i < dimensionValues.size(); i++) {
            cumulativeDist = cumulativeDist + Math.pow(pDimValues.get(i) - dimensionValues.get(i), 2);
        }
        return Math.sqrt(cumulativeDist);
    }

    public int compareTo(Point other) {
        if (!dimensionValues.get(0).equals(other.getDimensionValues().get(0))) {
            return Double.compare(dimensionValues.get(0), other.getDimensionValues().get(0));
        } else {
            return Double.compare(dimensionValues.get(1), other.getDimensionValues().get(1));
        }
    }

}
