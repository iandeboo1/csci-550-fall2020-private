import java.lang.reflect.Array;
import java.util.*;

public class KNN {

    public KNN() { }

    public String knnAlgorithm(List<Point> D, int k, Point tp) {
        // sort D by Euclidean distance to test point
        D.sort(new SortByDist(tp));
        List<Point> nearestNeighbors = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            nearestNeighbors.add(D.get(i));
        }
        Map<String, Integer> nLabelFreq = new HashMap<>();
        for (Point p : nearestNeighbors) {
            if (nLabelFreq.containsKey(p.getLabel())) {
                // points have already been found with said label
                int freq = nLabelFreq.get(p.getLabel());
                nLabelFreq.put(p.getLabel(), freq + 1);
            } else {
                // first point found with said label
                nLabelFreq.put(p.getLabel(), 1);
            }
        }
        // find most common label in knn
        String maxLabel = "";
        int maxFreq = 0;
        for (Map.Entry<String, Integer> entry : nLabelFreq.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                maxLabel = entry.getKey();
            }
        }
        return maxLabel;
    }





    private static class SortByDist implements Comparator<Point> {

        private Point pt;

        SortByDist(Point pt) {
            this.pt = pt;
        }

        @Override
        public int compare(Point pt1, Point pt2) {
            double pt1Dist = 0.0;
            double pt2Dist = 0.0;
            for (int i = 0; i < pt.getDimensions().size(); i++) {
                pt1Dist += Math.pow((pt.getDimensions().get(i) - pt1.getDimensions().get(i)), 2);
                pt2Dist += Math.pow((pt.getDimensions().get(i) - pt2.getDimensions().get(i)), 2);
            }
            return Double.compare(Math.sqrt(pt1Dist), Math.sqrt(pt2Dist));
        }

    }

}
