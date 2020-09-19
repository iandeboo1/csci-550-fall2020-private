import java.util.List;

public class Rule {

    List<Integer> antecedent;
    List<Integer> consequent;
    int support;

    public Rule(List<Integer> antecedent, List<Integer> consequent, int support) {
        this.antecedent = antecedent;
        this.consequent = consequent;
        this.support = support;
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

}
