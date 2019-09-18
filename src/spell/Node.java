package spell;

public class Node implements INode {
    private int value;
    private Node parentNode;
    private Node[] nodes;
    private char nodeLetter;

    public Node() {
        this.nodes = new Node[26];
        this.value = 0;
    }

    public Node(char letter, int value, Node parent) {
        this.nodeLetter = letter;
        this.value = value;
        this.nodes = new Node[26];
        this.parentNode = parent;
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
