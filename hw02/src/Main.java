import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        int coordBounds = 100;      //all points for synthetic dataset are between 0 and this number (for both x and y values)
        int pointPerPoly = 50;      //how many points make up the polygon boundary and the random points inside
        int numberOfPoly = 3;       //number of polygons to generate for synthetic dataset
        int noiseNumber = 50;       //how many random noise points will be generated

        final String csvFile = "iris.csv";

        int k = 2;                  //how many clusters to generate for k-means
        double kMeansEps = 0.25;    //represents the min change in centroid change before stopping iteration

        double dbsEps = 0.25;       //represents the size of the neighborhood for DBScan
        int minpts = 5;             //minimum amount of neighbors to be considered a core point
        //TODO: I HAVE NO IDEA WHAT TO SET THEM TOO SO THESE ARE JUST PLACEHOLDERS

        SyntheticDataGenerator syn = new SyntheticDataGenerator(coordBounds, pointPerPoly, numberOfPoly, noiseNumber);
        List<Polygon> dataSet = syn.createDataset();

        DatabaseGenerator dbg = new DatabaseGenerator(csvFile);
        List<Point> database = dbg.getDatabase();

        K_Means_Algorithm kma = new K_Means_Algorithm();
        Map<Point, Cluster> clusters = kma.k_means(database, k, kMeansEps);

        DB_Scan_Algorithm dbs = new DB_Scan_Algorithm();
        dbs.dbScan(database, dbsEps, minpts);
        Map<Integer, Cluster> clusterSet = dbs.getClusterSet();
        Cluster corePoints = dbs.getCorePoints();
        Cluster borderPoints = dbs.getBorderPoints();
        Cluster noisePoints = dbs.getNoisePoints();

    }

}
