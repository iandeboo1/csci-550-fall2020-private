import java.util.List;

public class Node {

    private Node lChild;
    private Node rChild;
    private Boolean isLeaf;
    private String label;
    private Double splitValue;
    private Integer splitDimension;

    public Node() {
        isLeaf = false;
    }

    public void setRChild(Node n) {
        if (rChild == null) {
            rChild = n;
        }
    }

    public Node getRChild() {
        return rChild;
    }

    public void setLChild(Node n) {
        if (lChild == null) {
            lChild = n;
        }
    }

    public Node getLChild() {
        return lChild;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean b) {
        isLeaf = b;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String l) {
        label = l;
    }

    public void setSplitValue(Double d) {
        splitValue = d;
    }

    public Double getSplitValue() {
        return splitValue;
    }

    public void setSplitDimension(Integer i) {
        splitDimension = i;
    }

    public Integer getSplitDimension() {
        return splitDimension;
    }

}