package spell;

import java.util.Arrays;
import java.util.Objects;

public class Node implements INode {
    private int value;
    private Node parentNode;
    private Node[] nodes;
    private char nodeLetter;

    public Node() {
        this.nodes = new Node[26];
        this.value = 0;
        this.parentNode = null;
    }

    @Override
    public int getValue() {
        return value;
    }

    public void incrementValue() {
        this.value++;
    }

    public void setNodeLetter(char nodeLetter) {
        this.nodeLetter = nodeLetter;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        Node currentNode = this;
        while(currentNode.getParentNode() != null) {
            str.append(currentNode.getNodeLetter());
            currentNode = currentNode.getParentNode();
        }
        str.reverse();
        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return value == node.value &&
                nodeLetter == node.nodeLetter &&
                Arrays.equals(nodes, node.nodes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(value, nodeLetter);
        result = 31 * result + Arrays.hashCode(nodes);
        return result;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public char getNodeLetter() {
        return nodeLetter;
    }

    public Node getParentNode() {
        return parentNode;
    }
}
