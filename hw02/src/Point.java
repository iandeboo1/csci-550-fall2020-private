import java.util.List;

public class Point {

    private List<Double> dimensionValues;
    private List<Point> neighbors;
    private int identifier;

    public Point(List<Double> values) {
        dimensionValues = values;
    }

    public void setIdentifier(int id) {
        identifier = id;
    }

    public int getIdentifier() {
        return identifier;
    }

    public int getNeighborhoodSize() {
        return neighbors.size();
    }

    public List<Point> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Point p) {
        neighbors.add(p);
    }

    public List<Double> getDimensionValues() {
        return dimensionValues;
    }

    public void addDimensionValue(Double value) {
        dimensionValues.add(value);
    }

    public boolean isNull() {
        return dimensionValues.isEmpty();
    }

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

}
