import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

//        REPL repl = new REPL();
//        repl.runREPL();

        /*---------------------------------------------------Settings----------------------------------------------------*/
        // DatabaseGenerator
        String csvFileTrain = "iris_train.csv";    //cannot modify from REPL program
        String csvFileTest = "iris_test.csv";    //cannot modify from REPL program
        //DecisionTree
        int eta = 5;
        double pi = 0.75;
        //Knn
        int k = 8;

        DatabaseGenerator dbg1 = new DatabaseGenerator(csvFileTrain);
        List<Point> dataset1 = dbg1.getDatabase();

        DatabaseGenerator dbg2 = new DatabaseGenerator(csvFileTest);
        List<Point> dataset2 = dbg2.getDatabase();

        DecisionTree dtg = new DecisionTree();
        dtg.decisionTreeAlgorithm(dataset1, eta, pi);
        Tree decTree = dtg.getTree();

        KNN knn = new KNN();
        String label = knn.knnAlgorithm(dataset1, k, new Point());

    }

}
