import java.util.*;

public class StrongRuleGenerator {

    public StrongRuleGenerator() {
    }

    public List<Rule> AssociationRules(Map<List<Integer>, Integer> F, double minconf) {
        List<Rule> ruleList = new ArrayList<>();
        Iterator<Map.Entry<List<Integer>, Integer>> iter = F.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry<List<Integer>, Integer> Z = iter.next();
            if (Z.getKey().size() >= 2) {
                List<List<Integer>> A = getSubsets(Z.getKey());
                int zSup = Z.getValue();                                        //support of Z, used for calculating confidence
                while (!A.isEmpty()) {
                    List<Integer> X = A.get(0);                                 //maximal element in A
                    A.remove(X);
                    double conf = (double) zSup / F.get(X);
                    if (conf >= minconf) {
                        List<Integer> consequent = new ArrayList<>();
                        consequent.addAll(Z.getKey());                          //must add to new list like this in order to avoid hashing breakdown in F
                        for (Integer value : X) {                               //forms the consequent by subtracting everything in X from everything in Z
                            consequent.remove(value);
                        }
                        ruleList.add(new Rule(X, consequent, zSup));
                    } else {
                        List<List<Integer>> subsets = getSubsets(X);
                        for (List<Integer> entry : subsets) {
                            A.remove(entry);
                        }
                    }
                }
            }
        }
        return ruleList;
    }

    public List<List<Integer>> getSubsets(List<Integer> set) {
        List<List<Integer>> fullSubsetList = new ArrayList<>();                 //unsorted subset list, not a true subset
        List<List<Integer>> sortedSubsetList = new ArrayList<>();               //subset list sorted, and a true subset
        int n = set.size();
        for (int i = 1; i < (1 << n); i++) {
            List<Integer> singleSubset = new ArrayList<>();
            for (int j = 0; j < n; j++){
                if ((i & (1 << j)) > 0) {
                    singleSubset.add(set.get(j));
                }
            }
            fullSubsetList.add(singleSubset);
        }
        for (int i = n - 1; i > 0; i--) {                                       //loops through n - 1 times
            for (List<Integer> entry : fullSubsetList) {
                if (!entry.equals(set)) {                                       //removes subset value equal to the set
                    if (entry.size() == i) {
                        sortedSubsetList.add(entry);
                    }
                }
            }
        }
        return sortedSubsetList;
    }

}