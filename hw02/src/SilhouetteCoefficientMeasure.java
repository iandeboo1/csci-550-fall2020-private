import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SilhouetteCoefficientMeasure {

    List<Point> allPoints;
    Map<Integer, Cluster> results;
    double[][] pairwiseMatrix;

    public SilhouetteCoefficientMeasure(Map<Integer, Cluster> results) {
        this.results = results;
        allPoints = new ArrayList<>();
        createPtsList();
        fillPairMatrix();
    }

    public void createPtsList() {
        //converts map of clusters to list of all points, for use in pairwise matrix
        for (Map.Entry<Integer, Cluster> entry : results.entrySet()) {
            for (Point pt : entry.getValue().getPoints()) {
                pt.setIdentifier(entry.getKey());       //ensures each point has a set clusterID
                allPoints.add(pt);
            }
        }
    }

    public void fillPairMatrix() {
        pairwiseMatrix = new double[allPoints.size()][allPoints.size()];
        for (Point pt : allPoints) {
            for (Point p : allPoints) {
                if (allPoints.indexOf(p) == allPoints.indexOf(pt)) {
                    //sets matrix diagonal to 0s
                    pairwiseMatrix[allPoints.indexOf(pt)][allPoints.indexOf(p)] = 0;
                } else if (allPoints.indexOf(p) > allPoints.indexOf(pt)) {
                    //only fills upper triangle of matrix
                    pairwiseMatrix[allPoints.indexOf(pt)][allPoints.indexOf(p)] = pt.getDistanceBetween(p);
                }
            }
        }
    }

    public double getSilCoef() {
        double silCoef = 0;
        for (Point pt : allPoints) {
            double cohesion = 0;
            double separation = 0;
            for (Point p : results.get(pt.getIdentifier()).getPoints()) {
                //all points in pt's cluster
                if (!p.equals(pt)) {
                    if (allPoints.indexOf(p) > allPoints.indexOf(pt)) {
                        //in upper triangular matrix
                        cohesion = cohesion + pairwiseMatrix[allPoints.indexOf(pt)][allPoints.indexOf(p)];
                    } else {
                        //in lower triangular matrix
                        cohesion = cohesion + pairwiseMatrix[allPoints.indexOf(p)][allPoints.indexOf(pt)];
                    }
                }
            }
            cohesion = cohesion / results.get(pt.getIdentifier()).getPoints().size();
            for (Map.Entry<Integer, Cluster> cluster : results.entrySet()) {
                if (cluster.getKey() != pt.getIdentifier()) {
                    //all clusters besides the cluster of pt
                    double clusterSeparation = 0;
                    for (Point p : cluster.getValue().getPoints()) {
                        if (allPoints.indexOf(p) > allPoints.indexOf(pt)) {
                            //in upper triangular matrix
                            clusterSeparation = clusterSeparation + pairwiseMatrix[allPoints.indexOf(pt)][allPoints.indexOf(p)];
                        } else {
                            //in lower triangular matrix
                            clusterSeparation = clusterSeparation + pairwiseMatrix[allPoints.indexOf(p)][allPoints.indexOf(pt)];
                        }
                    }
                    clusterSeparation = clusterSeparation / cluster.getValue().getPoints().size();
                    if (separation == 0 || clusterSeparation < separation) {
                        //finds closest cluster to pt, other than it's own
                        separation = clusterSeparation;
                    }
                }
            }
            if (cohesion > separation) {
                silCoef = silCoef + ((separation - cohesion) / cohesion);
            } else {
                silCoef = silCoef + ((separation - cohesion) / separation);
            }
        }
        return silCoef / allPoints.size();
    }

}
