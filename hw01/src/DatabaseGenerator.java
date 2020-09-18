import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DatabaseGenerator {

    public static final String delimiter = ",";
    private Map<String, List<Integer>> database;
    private List<Integer> itemset;
    private List<String> txns = new ArrayList<>();
    private List<Integer> ids = new ArrayList<>();

    public DatabaseGenerator(String csvFile) {
        database = new HashMap<>();                          //txn database
        itemset = new ArrayList<>();                         //all possible itemsets

        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line = "";
            int count = 0;
            String[] lineArray;                                                  //array of 4 columns in each line
            while ((line = br.readLine()) != null) {                             //reads through file line by line
                if (count != 0) {
                    lineArray = line.split(delimiter);                           //divides line into 4 columns, based on commas
                    txns.add(lineArray[0]);
                    ids.add(Integer.parseInt(lineArray[2]));
                }
                count++;
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Map<String, List<Integer>> createDatabase() {
        if (txns.size() == ids.size()) {
            for (int i = 0; i < txns.size(); i++) {                             //loop through each row in csv file, but now represented in 2 separate lists
                if (database.containsKey(txns.get(i))) {                        //txn number already in database, the new item ID will be added to its item list
                    List<Integer> itemset = database.get(txns.get(i));
                    itemset.add(ids.get(i));
                    Collections.sort(itemset);
                    database.replace(txns.get(i), itemset);
                } else {                                                        //a new txn number is found and is added to database along with first item ID
                    List<Integer> newTxnItemList = new ArrayList<>();
                    newTxnItemList.add(ids.get(i));
                    database.put(txns.get(i), newTxnItemList);
                }
            }
        } else {
            System.out.println("\nUnevequal amount of transaction IDs and item IDs!");
        }
        return database;
    }

    public List<Integer> createItemset() {
        List<Integer> duplicateFreeList = new ArrayList<>();
        for (Integer value : ids) {
            if (!duplicateFreeList.contains(value)) {
                duplicateFreeList.add(value);
            }
        }
        Collections.sort(duplicateFreeList);
        itemset = duplicateFreeList;
        return itemset;
    }

}
