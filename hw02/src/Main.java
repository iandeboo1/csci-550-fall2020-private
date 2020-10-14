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
        List<Point> synthDataset = syn.createDataset();
        List<Polygon> truth = syn.getGroundTruth();

        DatabaseGenerator dbg = new DatabaseGenerator(csvFile);
        List<Point> dataset = dbg.getDatabase();

        /*---------------------------------------------Extrinsic------------------------------------------------*/

        K_Means_Algorithm kma1 = new K_Means_Algorithm();
        Map<Integer, Cluster> resultsSynthK_Ex = kma1.k_means(synthDataset, k, kMeansEps);

        PurityMeasure pm1 = new PurityMeasure(resultsSynthK_Ex, truth, synthDataset);
        Double kmeansPurity = pm1.getPurity();

        DB_Scan_Algorithm dbs1 = new DB_Scan_Algorithm();
        dbs1.dbScan(synthDataset, dbsEps, minpts);
        Map<Integer, Cluster> resultsSynthDB_Ex = dbs1.getClusterSet();

        PurityMeasure pm2 = new PurityMeasure(resultsSynthDB_Ex, truth, synthDataset);
        Double dbscanPurity = pm2.getPurity();

        /*---------------------------------------------Intrinsic------------------------------------------------*/

        K_Means_Algorithm kma2 = new K_Means_Algorithm();
        Map<Integer, Cluster> resultsSynthK_In = kma2.k_means(synthDataset, k, kMeansEps);

        SilhouetteCoefficientMeasure scm1 = new SilhouetteCoefficientMeasure(resultsSynthK_In);
        Double kmeansSilCoef_Synth = scm1.getSilCoef();

        DB_Scan_Algorithm dbs2 = new DB_Scan_Algorithm();
        dbs2.dbScan(synthDataset, dbsEps, minpts);
        Map<Integer, Cluster> resultsSynthDB_In = dbs2.getClusterSet();

        SilhouetteCoefficientMeasure scm2 = new SilhouetteCoefficientMeasure(resultsSynthDB_In);
        Double dbscanSilCoef_Synth = scm2.getSilCoef();

        K_Means_Algorithm kma3 = new K_Means_Algorithm();
        Map<Integer, Cluster> resultsRealK_In = kma3.k_means(dataset, k, kMeansEps);

        SilhouetteCoefficientMeasure scm3 = new SilhouetteCoefficientMeasure(resultsRealK_In);
        Double kmeansSilCoef_Real = scm3.getSilCoef();

        DB_Scan_Algorithm dbs3 = new DB_Scan_Algorithm();
        dbs3.dbScan(dataset, dbsEps, minpts);
        Map<Integer, Cluster> resultsRealDB_In = dbs3.getClusterSet();

        SilhouetteCoefficientMeasure scm4 = new SilhouetteCoefficientMeasure(resultsRealDB_In);
        Double dbscanSilCoef_Real = scm4.getSilCoef();

    }

}
