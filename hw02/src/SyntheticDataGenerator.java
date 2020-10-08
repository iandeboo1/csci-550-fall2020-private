import java.util.*;

public class SyntheticDataGenerator {

    private final int coordBounds;
    private final int pointsPerPoly;
    private final int numberOfPoly;
    private final int noiseNumber;

    public SyntheticDataGenerator(int coordBounds, int pointsPerPoly, int numberOfPoly, int noiseNumber) {
        this.coordBounds = coordBounds;
        this.pointsPerPoly = pointsPerPoly;
        this.numberOfPoly = numberOfPoly;
        this.noiseNumber = noiseNumber;
    }

    public List<Polygon> createDataset() {
        int regionBounds = coordBounds / numberOfPoly;      //dividing the range of points into separate sections so polygons don't overlap
        List<Polygon> polygonList = new ArrayList<>();
        for (int i = 0; i < numberOfPoly; i++) {
            Random rand = new Random();
            List<RandomPoint> polyPoints = new ArrayList<>();
            for (int j = 0; j < pointsPerPoly; j++) {
                double xVal = rand.nextDouble() * ((regionBounds * (j + 1)) - (regionBounds * j)) + (regionBounds * j);
                double yVal = rand.nextDouble() * (coordBounds);
                RandomPoint point = new RandomPoint(xVal, yVal);
                polyPoints.add(point);
            }
            List<RandomPoint> convexBorder = ConvexHull.makeHull(polyPoints);       //list of border points
            for (RandomPoint borderPt : convexBorder) {
                polyPoints.remove(borderPt);        //creates list of only interior points
            }
            polygonList.add(new Polygon(i, polyPoints));
        }
        //create noise points
        List<RandomPoint> noisePoints = new ArrayList<>();
        outerloop:
        while (noisePoints.size() < noiseNumber) {
            Random rand = new Random();
            double xVal = rand.nextDouble() * (coordBounds);
            double yVal = rand.nextDouble() * (coordBounds);
            RandomPoint noisePt = new RandomPoint(xVal, yVal);
            for (Polygon polygon : polygonList) {
                if (polygon.insideBoundary(noisePt)) {
                    continue outerloop;
                }
            }
            noisePoints.add(noisePt);
        }
        polygonList.add(new Polygon(0, noisePoints));       //storing noise points as a polygon for convenience
        return polygonList;
    }

}
