import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class REPL {

    /*---------------------------------------------------Settings----------------------------------------------------*/
    //SyntheticDatabaseGenerator
    private int coordBounds = 100;      //all points for synthetic dataset are between 0 and this number (for both x and y values)
    private int pointPerPoly = 50;      //how many points make up the polygon boundary and the random points inside
    private int numberOfPoly = 3;       //number of polygons to generate for synthetic dataset
    private int noiseNumber = 50;       //how many random noise points will be generated

    //DatabaseGenerator
    private final String csvFile = "iris.csv";      //cannot modify from REPL program

    //Kmeans
    private int k = 3;                  //how many clusters to generate for k-means
    private double kMeansEps = 5;       //represents the min change in centroid change before stopping iteration
    // these values are set to work with the synthetic dataset, but should be 3 and 0.5 for iris dataset

    //DBscan
    private double dbsEps = 5;          //minimum distance to be considered a neighbor
    private int minpts = 5;             //minimum amount of neighbors to be considered a core point
    //these values are set to work with the synthetic dataset, but should be 0.3 and 3 for iris dataset

    /*-----------------------------------------------Generated Values------------------------------------------------*/
    List<Point> synthDataset = null;
    List<Polygon> truth = null;

    List<Point> dataset = null;

    K_Means_Algorithm kma1 = null;
    Map<Integer, Cluster> clusteringSynthK = null;
    K_Means_Algorithm kma2 = null;
    Map<Integer, Cluster> clusteringIrisK = null;

    DB_Scan_Algorithm dbs1 =null;
    Map<Integer, Cluster> clusteringSynthDB = null;
    DB_Scan_Algorithm dbs2 = null;
    Map<Integer, Cluster> clusteringIrisDB = null;

    PurityMeasure pm1 = null;
    Double resultsPurityK = null;
    PurityMeasure pm2 = null;
    Double resultsPurityDB = null;

    SilhouetteCoefficientMeasure scm1 = null;
    Double resultsSilK_Iris = null;
    SilhouetteCoefficientMeasure scm2 = null;
    Double resultsSilK_Synth = null;
    SilhouetteCoefficientMeasure scm3 = null;
    Double resultsSilDB_Iris = null;
    SilhouetteCoefficientMeasure scm4 = null;
    Double resultsSilDB_Synth = null;

    /*-------------------------------------------------REPL Program--------------------------------------------------*/
    public void runREPL() {
        System.out.println("\nWelcome to the unsupervised learning program...\n\nCommand list:\nsettings - allows for editing of algorithm parameters" +
                "\ngetDataset - generates dataset of your choice\ngetClusters - calculates a clustering based on a chosen dataset" +
                "\nrunAssessment - performs an assessment metric of based on a clustering of choice\n? - see these commands again\nexit - quit the program");

        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\nCommand:");
            String line = scan.nextLine();
            if (line.equals("exit")) {
                System.out.println("\nExiting unsupervised learning program...");
                break;
            } else if (line.equals("getDataset")) {
                System.out.println("Which data would you like (iris or synthetic)?");
                line = scan.nextLine();
                if (line.equals("synthetic")) {
                    SyntheticDataGenerator syn = new SyntheticDataGenerator(coordBounds, pointPerPoly, numberOfPoly, noiseNumber);
                    synthDataset = syn.createDataset();
                    truth = syn.getGroundTruth();
                    System.out.println("Created synthetic dataset!");
                    for(Point p : synthDataset) {
                        for (Double d : p.getDimensionValues()) {
                            System.out.print("[" + d + "]  ");
                        }
                        System.out.print("\n");
                    }
                } else if (line.equals("iris")) {
                    DatabaseGenerator dbg = new DatabaseGenerator(csvFile);
                    dataset = dbg.getDatabase();
                    System.out.println("Created iris dataset!");
                    for(Point p : dataset) {
                        for (Double d : p.getDimensionValues()) {
                            System.out.print("[" + d + "]  ");
                        }
                        System.out.print("\n");
                    }
                } else {
                    System.out.println("Dataset not recognized!");
                }
            } else if (line.equals("getClusters")) {
                System.out.println("Which clustering algorithm would you like (dbscan or kmeans)?");
                line = scan.nextLine();
                if (line.equals("kmeans")) {
                    System.out.println("On which dataset (iris or synthetic)?");
                    line = scan.nextLine();
                    if (line.equals("iris")) {
                        if (dataset != null) {
                            kma2 = new K_Means_Algorithm();
                            clusteringIrisK = kma2.k_means(dataset, k, kMeansEps);
                            System.out.println("Performed kmeans on iris dataset!");
                            for(Map.Entry<Integer, Cluster> entry : clusteringIrisK.entrySet()) {
                                System.out.print("Cluster " + entry.getKey() + ": \n");
                                for (Point p : entry.getValue().getPoints()) {
                                    System.out.print("Point: ");
                                    for (Double d : p.getDimensionValues()) {
                                        System.out.print("[" + d + "]  ");
                                    }
                                    System.out.print("\n");
                                }
                            }
                        } else {
                            System.out.println("You must create the dataset first (getDataset command)!");
                        }
                    } else if (line.equals("synthetic")) {
                        if (synthDataset != null) {
                            kma1 = new K_Means_Algorithm();
                            clusteringSynthK = kma1.k_means(synthDataset, k, kMeansEps);
                            System.out.println("Performed kmeans on synthetic dataset!");
                            for(Map.Entry<Integer, Cluster> entry : clusteringSynthK.entrySet()) {
                                System.out.print("Cluster " + entry.getKey() + ": \n");
                                System.out.print("Centroid: ");
                                for (Double d : entry.getValue().getCentroid().getDimensionValues()) {
                                    System.out.print("[" + d + "]  ");
                                }
                                System.out.print("\n");
                                for (Point p : entry.getValue().getPoints()) {
                                    System.out.print("Point: ");
                                    for (Double d : p.getDimensionValues()) {
                                        System.out.print("[" + d + "]  ");
                                    }
                                    System.out.print("\n");
                                }
                            }
                        } else {
                            System.out.println("You must create the dataset first (getDataset command)!");
                        }
                    } else {
                        System.out.println("Dataset not recognized!");
                    }
                } else if (line.equals("dbscan")) {
                    System.out.println("On which dataset (iris or synthetic)?");
                    line = scan.nextLine();
                    if (line.equals("iris")) {
                        if (dataset != null) {
                            dbs2 = new DB_Scan_Algorithm();
                            dbs2.dbScan(dataset, dbsEps, minpts);
                            clusteringIrisDB = dbs2.getClusterSet();
                            System.out.println("Performed dbscan on iris dataset!");
                            for(Map.Entry<Integer, Cluster> entry : clusteringIrisDB.entrySet()) {
                                System.out.print("Cluster " + entry.getKey() + ": \n");
                                for (Point p : entry.getValue().getPoints()) {
                                    System.out.print("Point: ");
                                    for (Double d : p.getDimensionValues()) {
                                        System.out.print("[" + d + "]  ");
                                    }
                                    System.out.print("\n");
                                }
                            }
                        } else {
                            System.out.println("You must create the dataset first (getDataset command)!");
                        }
                    } else if (line.equals("synthetic")) {
                        if (synthDataset != null) {
                            dbs1 = new DB_Scan_Algorithm();
                            dbs1.dbScan(synthDataset, dbsEps, minpts);
                            clusteringSynthDB = dbs1.getClusterSet();
                            System.out.println("Performed dbscan on synthetic dataset!");
                            for(Map.Entry<Integer, Cluster> entry : clusteringSynthDB.entrySet()) {
                                System.out.print("Cluster " + entry.getKey() + ": \n");
                                for (Point p : entry.getValue().getPoints()) {
                                    System.out.print("Point: ");
                                    for (Double d : p.getDimensionValues()) {
                                        System.out.print("[" + d + "]  ");
                                    }
                                    System.out.print("\n");
                                }
                            }
                        } else {
                            System.out.println("You must create the dataset first (getDataset command)!");
                        }
                    } else {
                        System.out.println("Dataset not recognized!");
                    }
                } else {
                    System.out.println("Algorithm not recognized!");
                }
            } else if (line.equals("runAssessment")) {
                System.out.println("Which assessment would you like (purity or silhouette)?");
                line = scan.nextLine();
                if (line.equals("purity")) {
                    System.out.println("With which clustering algorithm (dbscan or kmeans)?");
                    line = scan.nextLine();
                    if (line.equals("kmeans")) {
                        if (clusteringSynthK != null) {
                            pm1 = new PurityMeasure(clusteringSynthK, truth, synthDataset);
                            resultsPurityK = pm1.getPurity();
                            System.out.println("Ran purity assessment of kmeans clustering on synthetic dataset!");
                            System.out.println("Purity: " + resultsPurityK);
                            System.out.println("Values range from 0 to 1.");
                            System.out.println("Values closer to 1 indicate the clustering results are more similar to the ground truth for the dataset.");
                        } else {
                            System.out.println("You must calculate clusters first (getClusters command)!");
                        }
                    } else if (line.equals("dbscan")) {
                        if (clusteringSynthDB != null) {
                            pm2 = new PurityMeasure(clusteringSynthDB, truth, synthDataset);
                            resultsPurityDB = pm2.getPurity();
                            System.out.println("Ran purity assessment of dbscan clustering on synthetic dataset!");
                            System.out.println("Purity: " + resultsPurityDB);
                            System.out.println("Values range from 0 to 1.");
                            System.out.println("Values closer to 1 indicate the clustering results are more similar to the ground truth for the dataset.");
                        } else {
                            System.out.println("You must calculate clusters first (getClusters command)!");
                        }
                    } else {
                        System.out.println("Algorithm not recognized!");
                    }
                } else if (line.equals("silhouette")) {
                    System.out.println("With which clustering algorithm (dbscan or kmeans)?");
                    line = scan.nextLine();
                    if (line.equals("kmeans")) {
                        System.out.println("On which dataset (iris or synthetic)?");
                        line = scan.nextLine();
                        if (line.equals("iris")) {
                            if (clusteringIrisK != null) {
                                scm1 = new SilhouetteCoefficientMeasure(clusteringIrisK);
                                resultsSilK_Iris = scm1.getSilCoef();
                                System.out.println("Ran silhouette assessment of kmeans clustering on iris dataset!");
                                System.out.println("Silhouette Coefficient: " + resultsSilK_Iris);
                                System.out.println("Values range from -1 to 1.");
                                System.out.println("Values closer to 1 indicate that more of the points are closer to their clustering than other clustering.");
                            } else {
                                System.out.println("You must calculate clusters first (getClusters command)!");
                            }
                        } else if (line.equals("synthetic")) {
                            if (clusteringSynthK != null) {
                                scm2 = new SilhouetteCoefficientMeasure(clusteringSynthK);
                                resultsSilK_Synth = scm2.getSilCoef();
                                System.out.println("Ran silhouette assessment of kmeans clustering on synthetic dataset!");
                                System.out.println("Silhouette Coefficient: " + resultsSilK_Synth);
                                System.out.println("Values range from -1 to 1.");
                                System.out.println("Values closer to 1 indicate that more of the points are closer to their clustering than other clustering.");
                            } else {
                                System.out.println("You must calculate clusters first (getClusters command)!");
                            }
                        } else {
                            System.out.println("Dataset not recognized!");
                        }
                    } else if (line.equals("dbscan")) {
                        System.out.println("On which dataset (iris or synthetic)?");
                        line = scan.nextLine();
                        if (line.equals("iris")) {
                            if (clusteringIrisDB != null) {
                                scm3 = new SilhouetteCoefficientMeasure(clusteringIrisDB);
                                resultsSilDB_Iris = scm3.getSilCoef();
                                System.out.println("Ran silhouette assessment of dbscan clustering on iris dataset!");
                                System.out.println("Silhouette Coefficient: " + resultsSilDB_Iris);
                                System.out.println("Values range from -1 to 1.");
                                System.out.println("Values closer to 1 indicate that more of the points are closer to their clustering than other clustering.");
                            } else {
                                System.out.println("You must calculate clusters first (getClusters command)!");
                            }
                        } else if (line.equals("synthetic")) {
                            if (clusteringSynthDB != null) {
                                scm4 = new SilhouetteCoefficientMeasure(clusteringSynthDB);
                                resultsSilDB_Synth = scm4.getSilCoef();
                                System.out.println("Ran silhouette assessment of dbscan clustering on synthetic dataset!");
                                System.out.println("Silhouette Coefficient: " + resultsSilDB_Synth);
                                System.out.println("Values range from -1 to 1.");
                                System.out.println("Values closer to 1 indicate that more of the points are closer to their clustering than other clustering.");
                            } else {
                                System.out.println("You must calculate clusters first (getClusters command)!");
                            }
                        } else {
                            System.out.println("Dataset not recognized!");
                        }
                    } else {
                        System.out.println("Algorithm not recognized!");
                    }
                } else {
                    System.out.println("Assessment not recognized!");
                }
            } else if (line.equals("?")) {
                System.out.println("\nCommand list:\nsettings - allows for editing of algorithm parameters" +
                        "\ngetDataset - generates dataset of your choice\ngetClusters - calculates a clustering based on a chosen dataset" +
                        "\nrunAssessment - performs an assessment metric of based on a clustering of choice\n? - see these commands again\nexit - quit the program");
            } else if (line.equals("settings")) {
                System.out.println("\nCurrent Settings:\n\nint coordBounds = " + coordBounds + "   //all points for synthetic dataset are between 0 and this number (for both x and y values)" +
                        "\nint pointsPerPoly = " + pointPerPoly + "   //how many points make up the polygon boundary and the random points inside\nint numberOfPoly = " + numberOfPoly + " //number of polygons to generate for synthetic dataset" +
                        "\nint noiseNumber = " + noiseNumber + " //how many random noise points will be generated\n\nint k = " + k + " //how many clusters to generate for k-means\ndouble kMeansEps = " + kMeansEps + " //represents the min change in centroid change before stopping iteration" +
                        "\n\ndouble dbsEps = " + dbsEps + " ////minimum distance to be considered a neighbor\nint minpts = " + minpts + " //minimum amount of neighbors to be considered a core point\n\nWhich setting would you like to change?");
                line = scan.nextLine();
                switch (line) {
                    case "coordBounds" -> {
                        System.out.println("What value would you like to set it to?");
                        line = scan.nextLine();
                        try {
                            coordBounds = Integer.parseInt(line);
                            System.out.println("coordBounds has been successfully set to: " + coordBounds);
                        } catch (NumberFormatException e) {
                            System.out.println("The value you entered is not an integer!");
                        }
                    }
                    case "pointPerPoly" -> {
                        System.out.println("What value would you like to set it to?");
                        line = scan.nextLine();
                        try {
                            pointPerPoly = Integer.parseInt(line);
                            System.out.println("pointPerPoly has been successfully set to: " + pointPerPoly);
                        } catch (NumberFormatException e) {
                            System.out.println("The value you entered is not an integer!");
                        }
                    }
                    case "numberOfPoly" -> {
                        System.out.println("What value would you like to set it to?");
                        line = scan.nextLine();
                        try {
                            numberOfPoly = Integer.parseInt(line);
                            System.out.println("numberOfPoly has been successfully set to: " + numberOfPoly);
                        } catch (NumberFormatException e) {
                            System.out.println("The value you entered is not an integer!");
                        }
                    }
                    case "noiseNumber" -> {
                        System.out.println("What value would you like to set it to?");
                        line = scan.nextLine();
                        try {
                            noiseNumber = Integer.parseInt(line);
                            System.out.println("noiseNumber has been successfully set to: " + noiseNumber);
                        } catch (NumberFormatException e) {
                            System.out.println("The value you entered is not an integer!");
                        }
                    }
                    case "k" -> {
                        System.out.println("What value would you like to set it to?");
                        line = scan.nextLine();
                        try {
                            k = Integer.parseInt(line);
                            System.out.println("k has been successfully set to: " + k);
                        } catch (NumberFormatException e) {
                            System.out.println("The value you entered is not an integer!");
                        }
                    }
                    case "kMeansEps" -> {
                        System.out.println("What value would you like to set it to?");
                        line = scan.nextLine();
                        try {
                            kMeansEps = Double.parseDouble(line);
                            System.out.println("kMeansEps has been successfully set to: " + kMeansEps);
                        } catch (NumberFormatException e) {
                            System.out.println("The value you entered is not a double!");
                        }
                    }
                    case "dbsEps" -> {
                        System.out.println("What value would you like to set it to?");
                        line = scan.nextLine();
                        try {
                            dbsEps = Double.parseDouble(line);
                            System.out.println("dbsEps has been successfully set to: " + dbsEps);
                        } catch (NumberFormatException e) {
                            System.out.println("The value you entered is not a double!");
                        }
                    }
                    case "minpts" -> {
                        System.out.println("What value would you like to set it to?");
                        line = scan.nextLine();
                        try {
                            minpts = Integer.parseInt(line);
                            System.out.println("minpts has been successfully set to: " + minpts);
                        } catch (NumberFormatException e) {
                            System.out.println("The value you entered is not an integer!");
                        }
                    }
                    default -> System.out.println("Setting not recognized!");
                }
            } else {
                System.out.println("Command not recognized!");
            }
        }
    }
}
