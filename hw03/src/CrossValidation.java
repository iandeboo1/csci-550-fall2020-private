import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrossValidation {

    private double performance;
    private double variance;
    private int eta;
    private double pi;
    private int k;

    public CrossValidation(int eta, double pi, int k) {
        this.eta = eta;
        this.pi = pi;
        this.k = k;
    }

    public void kFoldAlgorithm(int K, List<Point> D, String classifier) {
        Collections.shuffle(D);
        List<List<Point>> partitions = new ArrayList<>();
        int partSize = D.size() / K;
        int remainder = D.size() % K;
        for (int i = 0; i < K; i++) {
            List<Point> part = new ArrayList<>();
            for (int j = 0; j < partSize; j++) {
                part.add(D.get(j + (i * partSize)));
            }
            if (i == K - 1) {
                // is last partition, add leftover points
                for (int k = 0; k < remainder; k++) {
                    part.add(D.get(((i + 1) * partSize) + k));
                }
            }
            partitions.add(part);
        }
        List<Double> assessments = new ArrayList<>();
        for (List<Point> part : partitions) {
            List<Point> trainingSet = new ArrayList<>();
            for (List<Point> otherParts : partitions) {
                if (otherParts != part) {
                    // take every other partition
                    trainingSet.addAll(otherParts);
                }
            }
            if (classifier.equals("dt")) {
                DecisionTree dt = new DecisionTree();
                // train classifier
                dt.decisionTreeAlgorithm(trainingSet, eta, pi);
                F_Measure fm = new F_Measure("dt", dt.getTree());
                // assess classifier
                assessments.add(fm.fMeasureAlgorithm(part));
            } else if (classifier.equals("knn")) {
                // train classifier
                F_Measure fm = new F_Measure("knn", trainingSet, k);
                // assess classifier
                assessments.add(fm.fMeasureAlgorithm(part));
            } else {
                System.out.println("Not a valid classifier");
                return;
            }
        }
        double assessSum = 0.0;
        for (Double d : assessments) {
            assessSum += d;
        }
        performance = assessSum / partitions.size();
        double perfSum = 0.0;
        for (Double d : assessments) {
            perfSum += Math.pow((d - performance), 2);
        }
        variance = perfSum / partitions.size();
    }

    public double getPerformance() {
        return performance;
    }

    public double getVariance() {
        return variance;
    }

}
