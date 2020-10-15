import java.util.*;

public class K_Means_Algorithm {

    public K_Means_Algorithm() {}

    public Map<Integer, Cluster> k_means(List<Point> D, int k, double E) {
        int t = 0;      //iteration count
        int dim = D.get(0).getDimensionValues().size();
        double[][] dimRanges = new double[dim][2];      //array of the ranges for each point dimension
        for (Point point : D) {
            for (int i = 0; i < dim; i++) {
                if (point.getDimensionValues().get(i) < dimRanges[i][0] || dimRanges[i][0] == 0) {       //finds min of each dimension of D
                    dimRanges[i][0] = point.getDimensionValues().get(i);
                }
                if (point.getDimensionValues().get(i) > dimRanges[i][1] || dimRanges[i][1] == 0) {       //finds max of each dimension of D
                    dimRanges[i][1] = point.getDimensionValues().get(i);
                }
            }
        }
        Map<Integer, Cluster> clusters = new HashMap<>();
        for (int i = 0; i < k; i++) {
            Point centroid = new Point(null);
            for (int j = 0; j < dim; j++) {
                //generates random value within the range of the given dimension
                Double pointDimValue = (new Random().nextDouble() * (dimRanges[j][1] - dimRanges[j][0])) + dimRanges[j][0];
                centroid.addDimensionValue(pointDimValue);
            }
            Cluster c = new Cluster();
            c.setCentroid(centroid);
            clusters.put(i + 1, c);
        }
        double overallCentroidImprovement;      //stores value of how much the centroid shifted as a whole in the last iteration
        do {
            t++;
            for (Cluster cluster : clusters.values()) {
                cluster.clearPoints();          //resets all clusters to contain no points
            }
            //cluster assignment
            for (Point point : D) {
                Integer minDistClust = null;
                double minDist = 0;
                for (Map.Entry<Integer, Cluster> cluster : clusters.entrySet()) {
                    //calculates Euclidean distance
                    double sqDist = Math.pow(point.getDistanceBetween(cluster.getValue().getCentroid()), 2);
                    if (sqDist < minDist || minDist == 0) {
                        minDist = sqDist;
                        minDistClust = cluster.getKey();
                    }
                }
                Cluster assignedCluster = clusters.get(minDistClust);
                assignedCluster.addPoint(point);
                clusters.put(minDistClust, assignedCluster);        //overwrites cluster entry with new cluster object containing the new point
            }
            //centroid update
            Map<Integer, Cluster> newClusters = new HashMap<>();        //will store new cluster values
            double cumulativeCentroidDrift = 0;     //stores value to be compared to epsilon value for while loop
            for (Map.Entry<Integer, Cluster> cluster : clusters.entrySet()) {
                Point newCentroid = new Point(null);       //the new centroid
                //summation of each dimension value across all points in cluster
                for (Point point : cluster.getValue().getPoints()) {
                    if (newCentroid.isNull()) {
                        for (double d : point.getDimensionValues()) {
                            newCentroid.addDimensionValue(d);
                        }
                    } else {
                        for (int i = 0; i < dim; i++) {
                            newCentroid.getDimensionValues().set(i, newCentroid.getDimensionValues().get(i) + point.getDimensionValues().get(i));
                        }
                    }
                }
                //division of summation by cluster size to get new centroid value
                if (!newCentroid.getDimensionValues().isEmpty()) {
                    //if the cluster had points in it
                    for (int i = 0; i < dim; i++) {
                        newCentroid.getDimensionValues().set(i, newCentroid.getDimensionValues().get(i) / cluster.getValue().getPoints().size());
                    }
                    Cluster newCluster = new Cluster();
                    for (Point pt : cluster.getValue().getPoints()) {
                        newCluster.addPoint(pt);
                    }
                    newCluster.setCentroid(newCentroid);
                    newClusters.put(cluster.getKey(), newCluster);
                    //calculate centroid drift for this cluster and add to total drift for all clusters
                    cumulativeCentroidDrift = cumulativeCentroidDrift + Math.pow(newCentroid.getDistanceBetween(cluster.getValue().getCentroid()), 2);      //add to total centroid drift of all clusters for this cycle
                } else {
                    //cluster had no points
                    newClusters.put(cluster.getKey(), cluster.getValue());
                }
            }
            clusters = newClusters;
            overallCentroidImprovement = cumulativeCentroidDrift;       //gives access to drift summation value outside of while loop, for comparison to epsilon in while loop condition
        } while (overallCentroidImprovement > E && t < 20);
        return clusters;
    }

}
