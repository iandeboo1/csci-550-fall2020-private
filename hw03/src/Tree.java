import java.util.List;

public class Tree {

    private Node root;

    Tree() { }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node n) {
        root = n;
    }

    public String apply(Point p) {
        //use tree to classify test point
        if (!root.isLeaf()) {
            boolean nodeIsLeaf = false;
            Node currentNode = root;
            do {
                if (p.getDimensions().get(currentNode.getSplitDimension()) < currentNode.getSplitValue()) {
                    // satisfies split point
                    currentNode = currentNode.getLChild();
                    nodeIsLeaf = currentNode.isLeaf();
                } else {
                    // does not satisfy split point
                    currentNode = currentNode.getRChild();
                    nodeIsLeaf = currentNode.isLeaf();
                }
            } while (!nodeIsLeaf);
            return currentNode.getLabel();
        } else {
            return root.getLabel();
        }
    }

    public void printTree() {

    }

}
