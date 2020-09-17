import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main (String[] args) {

        DatabaseGenerator dbg = new DatabaseGenerator();
        Map<String, List<Integer>> database = dbg.createDatabase();
        List<int[]> itemsets = dbg.createItemset();
        int minsum = 3;

        FrequentItemsetGenerator fig = new FrequentItemsetGenerator();
        Map<List<Integer>, Integer> fItemSet = fig.apriori(database, itemsets, minsum);

    }

}


