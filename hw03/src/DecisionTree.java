import com.sun.source.tree.BinaryTree;

import java.util.*;

public class DecisionTree {

    private double purity;
    private String majorityClass;
    private Map<String, Integer> classLabels;
    private Tree tree;

    public DecisionTree() {
        tree = new Tree();
        tree.setRoot(new Node());
    }

    public void decisionTreeAlgorithm(List<Point> D, int eta, double pi) {
        this.decisionTreeAlgorithm(D, eta, pi, tree.getRoot());
    }

    public void decisionTreeAlgorithm(List<Point> D, int eta, double pi, Node parent) {
        int n = D.size();
        getPurity(D, n);
        if (n < eta || purity >= pi) {
            parent.setIsLeaf(true);
            parent.setLabel(majorityClass);
            return;
        }
        Map<String, Double> splitPoint = new HashMap<>();
        splitPoint.put("Point", 0.0);   // chosen dimension value of split point
        splitPoint.put("Score", 0.0);
        int dim = 0;   // chosen dimension of split point
        for (int i = 0; i < D.get(0).getDimensions().size(); i++) {
            Map<String, Double> bestSplitForDimensionX = evaluateNumericAttribute(D, i);
            if (bestSplitForDimensionX.get("Score") > splitPoint.get("Score")) {
                splitPoint.put("Point", bestSplitForDimensionX.get("Point"));
                splitPoint.put("Score", bestSplitForDimensionX.get("Score"));
                dim = i;
            }
        }
        // partition D using split point
        List<Point> Dy = new ArrayList<>();
        List<Point> Dn = new ArrayList<>();
        for (Point pt : D) {
            List<Double> dimensions = pt.getDimensions();
            if (dimensions.get(dim) > splitPoint.get("Point")) {
                Dn.add(pt);
            } else {
                Dy.add(pt);
            }
        }
        parent.setSplitDimension(dim);
        parent.setSplitValue(splitPoint.get("Point"));
        Node LChild = new Node();
        Node RChild = new Node();
        parent.setLChild(LChild);
        parent.setRChild(RChild);

        if (Dy.size() < eta || Dn.size() < eta) {
            parent.setIsLeaf(true);
            parent.setLabel(majorityClass);
            return;
        }

        decisionTreeAlgorithm(Dy, eta, pi, LChild);
        decisionTreeAlgorithm(Dn, eta, pi, RChild);
    }

    private void getPurity(List<Point> D, int n) {
        Map<String, Integer> labels = new HashMap<>();
        for (Point pt : D) {
            // create a list of all labels in dataset and count number of points with each label
            String ptLabel = pt.getLabel();
            if (!labels.containsKey(ptLabel)) {
                labels.put(ptLabel, 0);
            } else {
                int currentCount = labels.get(ptLabel);
                labels.put(ptLabel, currentCount + 1);
            }
        }
        // find label with most points
        double majorityLabelCount = 0;
        String majorityLabel = "";
        for (Map.Entry<String, Integer> label : labels.entrySet()) {
            if (label.getValue() > majorityLabelCount) {
                majorityLabelCount = label.getValue();
                majorityLabel = label.getKey();
            }
        }
        purity = (majorityLabelCount / n);
        majorityClass = majorityLabel;
        classLabels = labels;
    }

    private Map<String, Double> evaluateNumericAttribute(List<Point> D, int dimensionX) {
        D.sort(new SortByDim(dimensionX));
        // set of midpoints
        Map<Double, Map<String, Integer>> M = new HashMap<>();  // <Split Value, <Class Label, Nvi>>
        Map<String, Integer> niCounts = new HashMap<>(); // <Class Label, Num Pts for Class>
        for (Map.Entry<String, Integer> cLabel : classLabels.entrySet()) {
            // set all classes to contain 0 points initially
            niCounts.put(cLabel.getKey(), 0);
        }
        // iterate through all points
        for (int i = 0; i < D.size(); i++) {
            // add point to total number of points for class
            int count = niCounts.get(D.get(i).getLabel());
            niCounts.put(D.get(i).getLabel(), count + 1);
            if (i < D.size() - 1) {
                // if not last point
                double ptDimVal = D.get(i).getDimensions().get(dimensionX);
                double nextPtDimVal = D.get(i + 1).getDimensions().get(dimensionX);
                if (ptDimVal != nextPtDimVal) {
                    double v = ptDimVal + ((nextPtDimVal - ptDimVal) / 2);  // split point value
                    M.put(v, classLabels);
                }
            }
        }
        // add final point
        int count = niCounts.get(D.get(D.size() - 1).getLabel());
        niCounts.put(D.get(D.size() - 1).getLabel(), count + 1);
        // initialize best split point
        Map<String, Double> bestSplit = new HashMap<>();
        bestSplit.put("Point", 0.0);   // chosen dimension value of split point
        bestSplit.put("Score", 0.0);
        for (Map.Entry<Double, Map<String, Integer>> sPt : M.entrySet()) {
            // iterate through split points
            int ptsInDy = 0;    // total points less than or equal to split point
            int ptsInDn = 0;    // total points greater than split point
            for (Map.Entry<String, Integer> cLabel : classLabels.entrySet()) {
                // iterate through class labels
                ptsInDy += sPt.getValue().get(cLabel.getKey());
                ptsInDn += (niCounts.get(cLabel.getKey()) - sPt.getValue().get(cLabel.getKey()));
            }
            List<Double> dProbs = new ArrayList<>();    // prob. of label i in dataset, for all labels
            List<Double> dyProbs = new ArrayList<>();   // prob. of label i in points that satisfy split, for all labels
            List<Double> dnProbs = new ArrayList<>();   // prob. of label i in points that don't satisfy split, for all labels
            for (Map.Entry<String, Integer> cLabel : classLabels.entrySet()) {
                // iterate through class labels
                dProbs.add(cLabel.getValue() / (double)D.size());
                dyProbs.add(sPt.getValue().get(cLabel.getKey()) / (double)ptsInDy);
                dnProbs.add(niCounts.get(cLabel.getKey()) - (double)sPt.getValue().get(cLabel.getKey()));
            }
            // calculate gain
            double score = getEntropy(dProbs) - (((ptsInDy / (double)D.size()) * getEntropy(dyProbs)) + ((ptsInDn / (double)D.size()) * getEntropy(dnProbs)));
            if (score > bestSplit.get("Score")) {
                bestSplit.put("Point", sPt.getKey());
                bestSplit.put("Score", score);
            }
        }
        return bestSplit;
    }

    private double getEntropy(List<Double> probs) {
        double probSum = 0;
        for (Double d : probs) {
            probSum += (d * Math.log(d));
        }
        return -probSum;
    }

    public Tree getTree() {
        return tree;
    }





    private static class SortByDim implements Comparator<Point> {

        private int dim;

        SortByDim(int dim) {
            this.dim = dim;
        }

        @Override
        public int compare(Point pt1, Point pt2) {
            return pt1.getDimensions().get(dim).compareTo(pt2.getDimensions().get(dim));
        }

    }

}


