import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main (String[] args) {

//        final String csvFile = "txn_by_dept.csv";
        int minsum = 4;
        double minconf = 0.7;
        int k = 20;

//        DatabaseGenerator dbg = new DatabaseGenerator(csvFile);
//        Map<String, List<Integer>> database = dbg.createDatabase();
//        List<Integer> itemset = dbg.createItemset();
//
//        FrequentItemsetGenerator fig = new FrequentItemsetGenerator();
//        Map<List<Integer>, Integer> fItemSet = fig.apriori(database, itemset, minsum);
//        System.out.println("\nFrequent Itemset: ");
//        for (Map.Entry<List<Integer>, Integer> entry : fItemSet.entrySet()) {
//            System.out.println(entry);
//        }
//
//        StrongRuleGenerator srg = new StrongRuleGenerator();
//        List<Rule> strongRuleSet = srg.AssociationRules(fItemSet, minconf);
//        System.out.println("\nStrong rules: ");
//        for (Rule rule : strongRuleSet) {
//            System.out.println(rule.getAntecedent() + " --> " + rule.getConsequent());
//        }
//
//        RankedRulesGenerator rrg = new RankedRulesGenerator();
//        List<Rule> interstingList = rrg.scoringAlgorithm(strongRuleSet, k, fItemSet, dbg.getSize());
//        System.out.println("\nInteresting rules: ");
//        for (Rule rule : interstingList) {
//            System.out.println(rule.getAntecedent() + " --> " + rule.getConsequent());
        }
    }

}


