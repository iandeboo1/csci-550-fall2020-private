import java.util.*;

public class SyntheticDataGenerator {

    private final int coordBounds;
    private final int pointsPerPoly;
    private final int numberOfPoly;
    private final int noiseNumber;
    List<Polygon> polygonList;

    public SyntheticDataGenerator(int coordBounds, int pointsPerPoly, int numberOfPoly, int noiseNumber) {
        this.coordBounds = coordBounds;
        this.pointsPerPoly = pointsPerPoly;
        this.numberOfPoly = numberOfPoly;
        this.noiseNumber = noiseNumber;
        polygonList = new ArrayList<>();
    }

    public List<Point> createDataset() {
        int regionBounds = coordBounds / numberOfPoly;      //dividing the range of points into separate sections so polygons don't overlap
        List<Point> pointList = new ArrayList<>();
        for (int i = 0; i < numberOfPoly; i++) {
            Random rand = new Random();
            List<Point> polyPoints = new ArrayList<>();
            for (int j = 0; j < pointsPerPoly; j++) {
                //create all random points in partition
                double xVal = rand.nextDouble() * ((regionBounds * (i + 1)) - (regionBounds * i)) + (regionBounds * i);
                double yVal = rand.nextDouble() * (coordBounds);
                List<Double> values = new ArrayList<>();
                values.add(xVal);
                values.add(yVal);
                Point point = new Point(values);
                polyPoints.add(point);
                pointList.add(point);
            }
            //draw polygon around partition
            List<Point> convexBorder = ConvexHull.makeHull(polyPoints);       //list of border points
            for (Point borderPt : convexBorder) {
                //creates list of only interior points
                polyPoints.remove(borderPt);
                pointList.remove(borderPt);
            }
            polygonList.add(new Polygon(i + 1, polyPoints));
        }
        //create noise points
        List<Point> noisePoints = new ArrayList<>();
        outerloop:
        while (noisePoints.size() < noiseNumber) {
            Random rand = new Random();
            double xVal = rand.nextDouble() * (coordBounds);
            double yVal = rand.nextDouble() * (coordBounds);
            List<Double> values = new ArrayList<>();
            values.add(xVal);
            values.add(yVal);
            Point noisePt = new Point(values);
            for (Polygon polygon : polygonList) {
                if (polygon.insideBoundary(noisePt)) {
                    //noise point lies within a polygon, discard it
                    continue outerloop;
                }
            }
            noisePoints.add(noisePt);
            pointList.add(noisePt);
        }
        polygonList.add(new Polygon(0, noisePoints));       //storing noise points as a polygon for convenience
        return pointList;
    }

    public List<Polygon> getGroundTruth() {
        return polygonList;
    }

}
