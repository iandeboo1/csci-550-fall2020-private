import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main (String[] args) {

        String csvFile = "/Users/ian/Desktop/CSCI 550 - Data Mining/csci-550-fall2020-private/hw01/dept.csv";
        int minsum = 1;

        DatabaseGenerator dbg = new DatabaseGenerator(csvFile);
        Map<String, List<Integer>> database = dbg.createDatabase();
        List<Integer> itemset = dbg.createItemset();

        FrequentItemsetGenerator fig = new FrequentItemsetGenerator();
        Map<List<Integer>, Integer> fItemSet = fig.apriori(database, itemset, minsum);
        System.out.println(fItemSet);
    }

}


