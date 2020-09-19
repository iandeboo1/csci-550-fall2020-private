import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RankedRulesGenerator {

    public RankedRulesGenerator() {
    }

    public List<Rule> scoringAlgorithm(List<Rule> rules, int k, Map<List<Integer>, Integer> F, int dbSize) {
        List<Rule> allRules = new ArrayList<>();
        List<Rule> interstingRules = new ArrayList<>();
        for (Rule rule : rules) {
            double rSup = rSup(rule, dbSize);
            double conf = conf(rule, F);
            double lift = lift(rule, F, dbSize);
            if (rSup >  0.0010) {
                rule.interest++;
                if (rSup > 0.0015) {
                    rule.interest++;
                    if (rSup > 0.0020) {
                        rule.interest++;
                    }
                }
            }
            if (conf > 0.8) {
                rule.interest++;
                if (conf > 0.95) {
                    rule.interest++;
                }
            }
            rule.interest = rule.interest + (lift * 0.10);
            allRules.add(rule);
        }
        allRules.sort(Collections.reverseOrder());
        if (allRules.size() < k) {
            System.out.println("\nOnly " + allRules.size() + " rules were found, so they are all interesting!");
            return allRules;
        } else {
            for (int i = 0; i < k; i++) {
                interstingRules.add(allRules.get((i)));
            }
        }
        return interstingRules;
    }

    public double rSup(Rule rule, int dbSize) {
        return ((double) rule.support / dbSize);
    }

    public double conf(Rule rule, Map<List<Integer>, Integer> F) {
        return ((double) rule.support / F.get(rule.getAntecedent()));
    }

    public double lift(Rule rule, Map<List<Integer>, Integer> F, int dbSize) {
        double conf = conf(rule, F);
        double conseqRelSup = (double) F.get(rule.getConsequent()) / dbSize;
        return conf / conseqRelSup;
    }

}
