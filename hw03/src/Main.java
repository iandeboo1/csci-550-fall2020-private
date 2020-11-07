import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

//        REPL repl = new REPL();
//        repl.runREPL();

        /*---------------------------------------------------Settings----------------------------------------------------*/
        //DatabaseGenerator
        String csvFile = "iris_train.csv";    //cannot modify from REPL program
        //DecisionTree
        int eta = 5;
        double pi = 0.75;

        DatabaseGenerator dbg = new DatabaseGenerator(csvFile);
        List<Point> dataset = dbg.getDatabase();

        DecisionTree dtg = new DecisionTree();
        dtg.decisionTreeAlgorithm(dataset, eta, pi);
        Tree decTree = dtg.getTree();
    }

}
