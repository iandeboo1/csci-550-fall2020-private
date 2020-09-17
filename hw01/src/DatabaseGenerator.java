import java.util.*;

public class DatabaseGenerator {

    private Map<String, List<Integer>> database;
    private List<int[]> itemsets;

    public DatabaseGenerator() {
        database = new HashMap<String, List<Integer>>();        //txn database
        itemsets = new ArrayList<int[]>();                      //all possible itemsets

        //everything below here in this function is just testing code and needs to be removed later
        int[] arr = {1, 2, 3, 4};
        int[] arr2 = {5, 6, 7, 8};
        int[] arr3 = {9, 10, 11, 12};
        List<Integer> ls1 = new ArrayList<Integer>();
        List<Integer> ls2 = new ArrayList<Integer>();
        List<Integer> ls3 = new ArrayList<Integer>();
        for (int u = 0; u < 4; u++) {
            ls1.add(arr[u]);
            ls2.add(arr2[u]);
            ls3.add(arr3[u]);
        }
        database.put("001", ls1);
        database.put("002", ls2);
        database.put("003", ls3);

        int[] array = {1};
        int[] array2 = {5};
        int[] array3 = {12};
        itemsets.add(array);
        itemsets.add(array2);
        itemsets.add(array3);
        //TODO: REMEMBER WHEN SETTING THESE IN THE REAL THING, IT'S IMPERATIVE TO ADD THEM IN ORDER FROM LEAST TO GREATEST VALUE
    }

    public Map<String, List<Integer>> createDatabase() {
        return database;
    }

    public List<int[]> createItemset() {
        return itemsets;
    }

}
