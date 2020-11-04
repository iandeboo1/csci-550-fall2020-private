import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

//        REPL repl = new REPL();
//        repl.runREPL();

        String csvFile = "iris_train.csv";

        DatabaseGenerator dbg = new DatabaseGenerator(csvFile);
        List<Point> dataset = dbg.getDatabase();

    }

}
