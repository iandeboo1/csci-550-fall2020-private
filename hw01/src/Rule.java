import java.util.List;

public class Rule implements Comparable<Rule> {

    List<Integer> antecedent;
    List<Integer> consequent;
    int support;
    Double interest;

    public Rule(List<Integer> antecedent, List<Integer> consequent, int support) {
        this.antecedent = antecedent;
        this.consequent = consequent;
        this.support = support;
        interest = 0.0;
    }

    public List<Integer> getAntecedent() {
        return antecedent;
    }

    public List<Integer> getConsequent() {
        return consequent;
    }

    public int getSupport() {
        return support;
    }

    public Double getInterest() {return interest;}

    @Override
    public int compareTo(Rule rule) {
        return this.getInterest().compareTo(rule.getInterest());
    }

}
