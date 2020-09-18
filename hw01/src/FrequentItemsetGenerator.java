import java.util.*;

public class FrequentItemsetGenerator {

    private int k = 1;
    List<List<Integer>> subsetList = new ArrayList<>();

    public Map<List<Integer>, Integer> apriori(Map<String, List<Integer>> DB, List<Integer> I, int minsup) {
        Map<List<Integer>, Integer> F = new HashMap<>();
        Map<List<Integer>, Integer> C = new HashMap<>();
        for (Integer item : I) {
            List<Integer> temp = new ArrayList<>();
            temp.add(item);
            C.put(temp, 0);
        }
        while (!C.isEmpty()) {
            computeSupport(C, DB);
            List<List<Integer>> toRemove = new ArrayList<>();
            for (Map.Entry<List<Integer>, Integer> entry : C.entrySet()) {      //loops through C, now with support values calculated
                if (entry.getValue() >= minsup) {
                    F.put(entry.getKey(), entry.getValue());                    //adds itemset to F if it's support meets minsup
                } else {
                    toRemove.add(entry.getKey());                               //adds to seperate toRemove list in order to prevent concurrent mod exception
                }
            }
            for (List<Integer> entryToRemove : toRemove) {                      //actually removes entries from C
                C.remove(entryToRemove);
            }
            C = extendPrefixTree(C);
            System.out.println("F is: " + F);
            k = k + 1;
        }
        return F;
    }

    public void computeSupport(Map<List<Integer>, Integer> C, Map<String, List<Integer>> DB) {
        for (Map.Entry<String, List<Integer>> entry : DB.entrySet()) {          //loops through each database txn
            List<List<Integer>> kSubsets = getSubsets(entry.getValue());       //gets all subsets of the txn itemset
            for (List<Integer> subset : kSubsets) {                             //loops through each subset
                if (C.containsKey(subset)) {
                    int sup = C.get(subset);
                    C.replace(subset, sup + 1);                                 //increments support value for that itemset
                }
            }
        }
    }

    public Map<List<Integer>, Integer> extendPrefixTree(Map<List<Integer>, Integer> oldC) {
        Map<List<Integer>, Integer> newC = new HashMap<>();
        Iterator<Map.Entry<List<Integer>, Integer>> iter1 = oldC.entrySet().iterator();
        int place = 1;
        while (iter1.hasNext()) {                                               //for each value in C
            Map.Entry<List<Integer>, Integer> entry1 = iter1.next();            //set value of first iterator
            Iterator<Map.Entry<List<Integer>, Integer>> iter2 = oldC.entrySet().iterator();
            for (int i = place; i > 0; i--) {
                iter2.next();                                                   //move 2nd iterator to value after the value in 1st iterator
            }
            while (iter2.hasNext()) {                                           //for each other value in C greater than the 1st value
                Map.Entry<List<Integer>, Integer> entry2 = iter2.next();
                System.out.println(oldC);
                List<Integer> adjoinedList = adjoinList(entry1.getKey(), entry2.getKey(), newC);
                System.out.println(oldC);
                if (!adjoinedList.isEmpty()) {
                    List<List<Integer>> kSubsets = getSubsets(adjoinedList);   //gets all subsets of the adjoined itemset
                    boolean containsAll = true;
                    for (List<Integer> entry : kSubsets) {                      //check all subsets inclusion in C
                        if (!oldC.containsKey(entry)) {
                            containsAll = false;                                //detects any subsets not in C
                            break;
                        }
                    }
                    if (containsAll) {
                        newC.put(adjoinedList, 0);                              //adds adjoined itemset to next level of tree with support = 0
                    }
                }   //TODO: COULD POSSIBLY IMPROVE EFFICIENCY HERE BY SKIPPING THE REST IF IT FINDS NON-MATCHES, NOT SURE YET
                place++;
            }
        }
        return newC;
    }

    public List<Integer> adjoinList(List<Integer> list1, List<Integer> list2, Map<List<Integer>, Integer> newC) {
        List<Integer> adjoinedList = new ArrayList<>();
        int commonElements = 0;
        if (k != 1) {                                                           //check to see how many elements they have in common
            for (Integer entry : list2) {
                if (list1.contains(entry)) {
                    commonElements++;
                }
            }
        }
        if (k == 1 || commonElements == (k - 1)) {                              //confirms they can be added
            for (Integer entry : list2) {                                       //adds lists together without duplicates
                if (!list1.contains(entry)) {
                    list1.add(entry);
                }
            }
            Collections.sort(list1);
        }
        if (!newC.containsKey(list1)) {                                         //ensures no duplicate entries in newC
            adjoinedList = list1;
        }
        return adjoinedList;
    }

    public List<List<Integer>> getSubsets(List<Integer> input) {
        int[] arrayFromList = new int[input.size()];
        for (int i = 0; i < input.size(); i++) {                                //convert list to array
            arrayFromList[i] = input.get(i);
        }
        int length = arrayFromList.length;
        int[] data = new int[k];
        createSubsets(arrayFromList, length, k, 0, data, 0);

        List<List<Integer>> tempList = subsetList;
        subsetList = new ArrayList<>();
        return tempList;
    }

    public void createSubsets(int[] arr, int n, int r, int index, int[] data, int i) {
        if (index == r) {
            List<Integer> subset = new ArrayList<>();
            for (int j = 0; j < r; j++) {
                subset.add(data[j]);
            }
            subsetList.add(subset);
            return;
        }

        if (i >= n) {
            return;
        }

        data[index] = arr[i];
        createSubsets(arr, n, r, index + 1,
                data, i + 1);

        createSubsets(arr, n, r, index, data, i + 1);
    }

}
