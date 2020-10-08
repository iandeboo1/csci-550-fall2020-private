import java.util.*;

public class K_Means_Algorithm {

    public K_Means_Algorithm() {}

    public Map<Point, Cluster> k_means(List<Point> D, int k, double E) {
        int t = 0;
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
        Map<Point, Cluster> clusters = new HashMap<>();
        for (int i = 0; i < k; i++) {
            Point centroid = new Point(null);
            for (int j = 0; j < dim; j++) {
                //generates random value within the range of the given dimension
                Double pointDimValue = (new Random().nextDouble() * (dimRanges[j][1] - dimRanges[j][0])) + dimRanges[j][0];
                centroid.addDimensionValue(pointDimValue);
            }
            clusters.put(centroid, null);       //creates a randomly generated point within the bounds of the provided dataset values
        }
        double overallCentroidImprovement = 0;      //stores value of how much the centroid shifted as a whole in the last cycle
        while (overallCentroidImprovement < E) {
            t++;
            clusters.replaceAll((centroid, cluster) -> null);       //resets all clusters to contain no points
            //cluster assignment
            for (Point point : D) {
                Point minDistCentroid = null;       //closest centroid
                double minDist = 0;                 //distance to closest centroid
                for (Point centroid : clusters.keySet()) {
                    //calculates Euclidean distance
                    if (point.getDistanceBetween(centroid) < minDist || minDist == 0) {      //if centroid is closer than current closest, replace it
                        minDist = Math.pow(point.getDistanceBetween(centroid), 2);
                        minDistCentroid = centroid;
                    }
                }
                Cluster assignedCluster = clusters.get(minDistCentroid);
                assignedCluster.addPoint(point);
                clusters.put(minDistCentroid, assignedCluster);         //add point to closest cluster
            }
            //centroid update
            Map<Point, Cluster> newClusters = new HashMap<>();        //will store new cluster values
            double cumulativeCentroidDrift = 0;     //stores value to be compared to epsilon value for while loop
            for (Map.Entry<Point, Cluster> cluster : clusters.entrySet()) {
                Point newCentroid = new Point(null);       //the new centroid
                //summation of each dimension value across all points in cluster
                for (Point point : cluster.getValue().getPoints()) {
                    if (newCentroid.isNull()) {
                        newCentroid = point;
                    } else {
                        for (int i = 0; i < dim; i++) {
                            newCentroid.getDimensionValues().set(i, newCentroid.getDimensionValues().get(i) + point.getDimensionValues().get(i));
                        }
                    }
                }
                //division of summation by cluster size to get new centroid value
                for (int i = 0; i < dim; i++) {
                    newCentroid.getDimensionValues().set(i, newCentroid.getDimensionValues().get(i) / cluster.getValue().getPoints().size());
                }
                newClusters.put(newCentroid, cluster.getValue());
                //calculate centroid drift for this cluster and add to total drift for all clusters
                cumulativeCentroidDrift = cumulativeCentroidDrift + Math.pow(newCentroid.getDistanceBetween(cluster.getKey()), 2);      //add to total centroid drift of all clusters for this cycle
            }
            clusters = newClusters;
            overallCentroidImprovement = cumulativeCentroidDrift;       //gives access to drift summation value outside of while loop, for comparison to epsilon in while loop condition
        }
        return clusters;
    }

}
