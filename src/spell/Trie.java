package spell;

import java.util.Arrays;
import java.util.Objects;
import java.lang.*;

public class Trie implements ITrie {
    private int wordCount;
    private int nodeCount;
    private Node rootNode;
    private char[] alphabet = {'a','b','c','d',
            'e','f','g','h','i','j','k','l','m','n','o',
            'p','q','r','s','t','u','v','w','x','y','z'};

    public Trie() {
        this.wordCount = 0;
        this.nodeCount = 1;
        rootNode = new Node();
    }

    private void incrementNodeCount() {
        this.nodeCount++;
    }

    private void incrementWordCount() {
        this.wordCount++;
    }


    @Override
    public void add(String word) {
        char temp;
        int index = 0;
        Node currentNode = rootNode;
        for(int i = 0; i < word.length(); i++) {
            temp = word.charAt(i);
            for(int j = 0; j < alphabet.length; j++) {
                if(Objects.equals(temp, alphabet[j])) {
                    index = j;
                }
            }
            if(currentNode.getNodes()[index] == null) {
                Node newNode = new Node();
                newNode.setNodeLetter(temp);
                newNode.setParentNode(currentNode);
                currentNode.getNodes()[index] = newNode;
                incrementNodeCount();
            }
            currentNode = currentNode.getNodes()[index];
        }
        if(currentNode.getValue() == 0) {
            incrementWordCount();
        }
        currentNode.incrementValue();

    }

    @Override
    public Node find(String word) {
        Node currentNode = findHelper(rootNode, word);
        if(currentNode == null) {
            return null;
        }
        else if(currentNode.getValue() > 0) {
            return currentNode;
        }
        else {
            return null;
        }

    }

    private Node findHelper(Node currentNode, String str) {
        char temp = 0;
        if(str.length() >= 1) {
           temp = str.charAt(0);
        }
        int index = 0;
        for(int j = 0; j < alphabet.length; j++) {
            if(Objects.equals(temp, alphabet[j])) {
                index = j;
            }
        }
        if(currentNode.getNodes()[index] == null) {
            return null;
        }
        else if (Objects.equals(temp, currentNode.getNodes()[index].getNodeLetter())) {
            currentNode = currentNode.getNodes()[index];
            if (str.length() == 1) {
                return currentNode;
            }
            else {
                str = str.substring(1);
                currentNode = findHelper(currentNode, str);
            }
        }
        return currentNode;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trie trie = (Trie) o;
        return wordCount == trie.wordCount &&
                nodeCount == trie.nodeCount &&
                Objects.equals(rootNode, trie.rootNode);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(wordCount, nodeCount);
        result = 31 * result + Arrays.hashCode(rootNode.getNodes());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder myString = new StringBuilder();
        myString = traverse(rootNode, myString);
        return myString.toString();
    }

    private StringBuilder traverse(Node currentNode, StringBuilder strBuild) {
        String word;
        if(currentNode == null) {

        }
        else {
            if(currentNode.getValue() > 0) {
                word = currentNode.toString();
                strBuild.append(word);
                strBuild.append("\n");
            }
            for(int i = 0; i < currentNode.getNodes().length; i++) {
                strBuild = traverse(currentNode.getNodes()[i], strBuild);
            }

        }
        return strBuild;
    }

    public char[] getAlphabet() {
        return alphabet;
    }
}
