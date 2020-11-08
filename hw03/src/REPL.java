import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class REPL {

    /*---------------------------------------------------Settings----------------------------------------------------*/
    // DatabaseGenerator
    String csvFileTrain = "iris_train.csv";    //cannot modify from REPL program
    String csvFileTest = "iris_test.csv";    //cannot modify from REPL program
    String csvFileAll = "iris_full.csv";    //cannot modify from REPL program

    // DecisionTree
    int eta = 4;
    double pi = 0.95;

    // Knn
    int k = 7;

    // CrossValidation
    int K = 5;

    /*-----------------------------------------------Generated Values------------------------------------------------*/
    List<Point> dataset = null;

    DecisionTree dt = null;

    KNN knn = null;

    F_Measure fm = null;

    CrossValidation cv = null;

    /*-------------------------------------------------REPL Program--------------------------------------------------*/
    public void runREPL() {
        System.out.println("\nWelcome to the supervised learning program...\n\nCommand list:\nsettings - allows for editing of algorithm parameters" +
                "\ngetDataset - generates dataset\ngetDecisionTree - classifies a point using the decision tree classifier" +
                "\ngetKNN - classifies a point using the KNN classifier\ngetFMeasure - gives the F-Measure assessment of a specified classifier" +
                "\nperformCross - performs cross validation of specified classifier\n? - see these commands again\nexit - quit the program");

        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\nCommand:");
            String line = scan.nextLine();
            if (line.equals("exit")) {
                System.out.println("\nExiting supervised learning program...");
                break;
            } else if (line.equals("getDataset")) {
                DatabaseGenerator dbg = new DatabaseGenerator(csvFileTrain);
                dataset = dbg.getDatabase();
                System.out.println("\nDataset created!");
            } else if (line.equals("getDecisionTree")) {
                dt = new DecisionTree();
                dt.decisionTreeAlgorithm(dataset, eta, pi);
                Point pt = new Point();
                System.out.println("Which point would you like to classify?");
                int i = 0;
                while(i < 4) {
                    line = scan.nextLine();
                    pt.addDimensionValue(Double.parseDouble(line));
                    i++;
                }
                System.out.println(dt.getTree().apply(pt));
            } else if (line.equals("getKNN")) {
                knn = new KNN();
                Point pt = new Point();
                System.out.println("Which point would you like to classify?");
                int i = 0;
                while(i < 4) {
                    line = scan.nextLine();
                    pt.addDimensionValue(Double.parseDouble(line));
                    i++;
                }
                System.out.println(knn.knnAlgorithm(dataset, k, pt));
            } else if (line.equals("getFMeasure")) {
                System.out.println("Which classifier would you like (decisionTree or KNN)?");
                line = scan.nextLine();
                if (line.equals("decisionTree")) {
                    if (dt == null) {
                        dt = new DecisionTree();
                        if (dataset == null) {
                            DatabaseGenerator dbg = new DatabaseGenerator(csvFileTrain);
                            dataset = dbg.getDatabase();
                        }
                        dt.decisionTreeAlgorithm(dataset, eta, pi);
                    }
                    fm = new F_Measure("dt", dt.getTree());
                    DatabaseGenerator dbg2 = new DatabaseGenerator(csvFileTest);
                    List<Point> testSet = dbg2.getDatabase();
                    System.out.println(fm.fMeasureAlgorithm(testSet));
                } else if (line.equals("KNN")) {
                    if (knn == null) {
                        knn = new KNN();
                        if (dataset == null) {
                            DatabaseGenerator dbg = new DatabaseGenerator(csvFileTrain);
                            dataset = dbg.getDatabase();
                        }
                    }
                    fm = new F_Measure("knn", dataset, k);
                    DatabaseGenerator dbg3 = new DatabaseGenerator(csvFileTest);
                    List<Point> testSet2 = dbg3.getDatabase();
                    System.out.println(fm.fMeasureAlgorithm(testSet2));
                } else {
                    System.out.println("Classifier not recognized!");
                }
            } else if (line.equals("performCross")) {
                cv = new CrossValidation(eta, pi, k);
                DatabaseGenerator dbg4 = new DatabaseGenerator(csvFileAll);
                List<Point> testSet3 = dbg4.getDatabase();
                System.out.println("Which classifier would you like (decisionTree or KNN)?");
                line = scan.nextLine();
                if (line.equals("decisionTree")) {
                    cv.kFoldAlgorithm(K, testSet3, "dt");
                    System.out.println("Performance: " + cv.getPerformance());
                    System.out.println("Variance: " + cv.getVariance());
                } else if (line.equals("KNN")) {
                    cv.kFoldAlgorithm(K, testSet3, "knn");
                    System.out.println("Performance: " + cv.getPerformance());
                    System.out.println("Variance: " + cv.getVariance());
                } else {
                    System.out.println("Classifier not recognized!");
                }
            } else if (line.equals("?")) {
                System.out.println("\nWelcome to the supervised learning program...\n\nCommand list:\nsettings - allows for editing of algorithm parameters" +
                        "\ngetDataset - generates dataset\ngetDecisionTree - classifies a point using the decision tree classifier" +
                        "\ngetKNN - classifies a point using the KNN classifier\ngetFMeasure - gives the F-Measure assessment of a specified classifier" +
                        "\nperformCross - performs cross validation of specified classifier\n? - see these commands again\nexit - quit the program");
            } else if (line.equals("settings")) {
                System.out.println("\nCurrent Settings:\n\nint eta = " + eta + "   //decision tree will stop if number of points in split section is below this value" +
                        "\ndouble pi = " + pi + "   //purity needed for decision tree to stop dividing a split section further\n\nint k = " + k + " //number of neighbors to evaluate for KNN algorithm" +
                        "\n\nint K = " + K + " //number of folds for cross validation");
                System.out.println("\nWhich setting would you like to change?");
                line = scan.nextLine();
                switch (line) {
                    case "eta" -> {
                        System.out.println("What value would you like to set it to?");
                        line = scan.nextLine();
                        try {
                            eta = Integer.parseInt(line);
                            System.out.println("eta has been successfully set to: " + eta);
                        } catch (NumberFormatException e) {
                            System.out.println("The value you entered is not an integer!");
                        }
                    }
                    case "pi" -> {
                        System.out.println("What value would you like to set it to?");
                        line = scan.nextLine();
                        try {
                            pi = Double.parseDouble(line);
                            System.out.println("pi has been successfully set to: " + pi);
                        } catch (NumberFormatException e) {
                            System.out.println("The value you entered is not an double!");
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
                    case "K" -> {
                        System.out.println("What value would you like to set it to?");
                        line = scan.nextLine();
                        try {
                            K = Integer.parseInt(line);
                            System.out.println("K has been successfully set to: " + K);
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

