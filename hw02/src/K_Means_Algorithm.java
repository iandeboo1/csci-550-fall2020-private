import java.util.*;

public class K_Means_Algorithm {

    public K_Means_Algorithm() {}

    public Map<List<Double>, List<List<Double>>> k_means(List<List<Double>> D, int k, double E) {
        int t = 0;
        int dim = D.get(0).size();
        double[][] dimRanges = new double[dim][2];      //array of the ranges for each point dimension
        for (List<Double> point : D) {
            for (int i = 0; i < dim; i++) {
                if (point.get(i) < dimRanges[i][0] || dimRanges[i][0] == 0) {       //finds min of each dimension of D
                    dimRanges[i][0] = point.get(i);
                }
                if (point.get(i) > dimRanges[i][1] || dimRanges[i][1] == 0) {       //finds max of each dimension of D
                    dimRanges[i][1] = point.get(i);
                }
            }
        }
        Map<List<Double>, List<List<Double>>> clusters = new HashMap<>();
        for (int i = 0; i < k; i++) {
            List<Double> centroid = new ArrayList<>();
            for (int j = 0; j < dim; j++) {
                //generates random value within the range of the given dimension
                Double pointDimValue = (new Random().nextDouble() * (dimRanges[j][1] - dimRanges[j][0])) + dimRanges[j][0];
                centroid.add(pointDimValue);
            }
            clusters.put(centroid, null);       //creates a randomly generated point within the bounds of the provided dataset values
        }
        double overallCentroidImprovement = 0;      //stores value of how much the centroid shifted as a whole in the last cycle
        while (overallCentroidImprovement < E) {
            t++;
            clusters.replaceAll((centroid, cluster) -> null);       //resets all clusters to contain no points
            //cluster assignment
            for (List<Double> point : D) {
                List<Double> minDistCentroid = null;       //closest centroid
                double minDist = 0;                        //distance to closest centroid
                for (List<Double> centroid : clusters.keySet()) {
                    //calculates Euclidean distance
                    double cumulativeDist = 0;
                    for (int i = 0; i < dim; i++) {
                        cumulativeDist = cumulativeDist + Math.pow(point.get(i) - centroid.get(i), 2);
                    }
                    if (Math.sqrt(cumulativeDist) < minDist || minDist == 0) {      //if centroid is closer than current closest, replace it
                        minDist = Math.sqrt(cumulativeDist);
                        minDistCentroid = centroid;
                    }
                }
                List<List<Double>> assignedCluster = clusters.get(minDistCentroid);
                assignedCluster.add(point);
                clusters.put(minDistCentroid, assignedCluster);         //add point to closest cluster
            }
            //centroid update
            Map<List<Double>, List<List<Double>>> newClusters = new HashMap<>();        //will store new cluster values
            double cumulativeCentroidDrift = 0;     //stores value to be compared to epsilon value for while loop
            for (Map.Entry<List<Double>, List<List<Double>>> cluster : clusters.entrySet()) {
                List<Double> newCentroid = new ArrayList<>();       //the new centroid
                //summation of each dimension value across all points in cluster
                for (List<Double> point : cluster.getValue()) {
                    if (newCentroid.isEmpty()) {
                        newCentroid = point;
                    } else {
                        for (int i = 0; i < dim; i++) {
                            newCentroid.set(i, newCentroid.get(i) + point.get(i));
                        }
                    }
                }
                //division of summation by cluster size to get new centroid value
                for (int i = 0; i < dim; i++) {
                    newCentroid.set(i, newCentroid.get(i) / cluster.getValue().size());
                }
                newClusters.put(newCentroid, cluster.getValue());
                //calculate centroid drift for this cluster
                double cumulativeDistance = 0;
                for (int i = 0; i < dim; i++) {
                    cumulativeDistance = cumulativeDistance + Math.pow(newCentroid.get(i) - cluster.getKey().get(i), 2);
                }
                cumulativeCentroidDrift = cumulativeCentroidDrift + Math.sqrt(cumulativeDistance);      //add to total centroid drift of all clusters for this cycle
            }
            clusters = newClusters;
            overallCentroidImprovement = cumulativeCentroidDrift;       //gives access to drift summation value outside of while loop, for comparison to epsilon in while loop condition
        }
        return clusters;
    }

}
