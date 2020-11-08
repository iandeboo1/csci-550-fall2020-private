import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class F_Measure {

    private String classifierName;
    private Tree classifier;
    private List<Point> trainD;
    private int k;

    public F_Measure(String classifierName, List<Point> trainD, int k) {
        this.classifierName = classifierName;
        this.trainD = trainD;
        this.k = k;
    }

    public F_Measure(String classifierName, Tree classifier) {
        this.classifierName = classifierName;
        this.classifier = classifier;
    }

    // parameter D is the test set
    public double fMeasureAlgorithm(List<Point> D) {
        KNN knn = new KNN();
        Map<String, Integer> correctlyGuessed = new HashMap<>();    // Nii
        Map<String, Integer> predictedLabels = new HashMap<>(); // Mi
        Map<String, Integer> trueLabels = new HashMap<>();  // Ni
        for (Point pt : D) {
            String predictedLabel = "";
            // record true label value of pt (Ni)
            if (trueLabels.containsKey(pt.getLabel())) {
                int freq = trueLabels.get(pt.getLabel());
                trueLabels.put(pt.getLabel(), freq + 1);
            } else {
                trueLabels.put(pt.getLabel(), 1);
            }
            // get predicted label
            if (classifierName.equals("dt")) {
                predictedLabel = classifier.apply(pt);
            } else if (classifierName.equals("knn")) {
                predictedLabel = knn.knnAlgorithm(trainD, k, pt);
            } else {
                System.out.println("Classifier not recognized");
            }
            // record predicted label value of pt (Mi)
            if (predictedLabels.containsKey(predictedLabel)) {
                int freq = predictedLabels.get(predictedLabel);
                predictedLabels.put(predictedLabel, freq + 1);
            } else {
                predictedLabels.put(predictedLabel, 1);
            }
            // record correctly guessed label value of pt (Nii)
            if (predictedLabel.equals(pt.getLabel())) {
                if (correctlyGuessed.containsKey(pt.getLabel())) {
                    int freq = correctlyGuessed.get(pt.getLabel());
                    correctlyGuessed.put(pt.getLabel(), freq + 1);
                } else {
                    correctlyGuessed.put(pt.getLabel(), 1);
                }
            }
        }
        double fMeasureSum = 0.0;
        for (Map.Entry<String, Integer> entry : trueLabels.entrySet()) {
            fMeasureSum += (double)((2 * correctlyGuessed.get(entry.getKey())) /
                    (entry.getValue() + predictedLabels.get(entry.getKey())));
        }
        return fMeasureSum / trueLabels.size();
    }

}
