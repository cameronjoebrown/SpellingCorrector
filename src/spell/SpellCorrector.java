package spell;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    Trie myTrie;
    public SpellCorrector() {
        myTrie = new Trie();
    }
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File dictionary = new File(dictionaryFileName);
        Scanner myScanner = new Scanner(dictionary);
        myScanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
        String str = myScanner.next();
        while(myScanner.hasNext()) {
            myTrie.add(str);
        }
        myScanner.close();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        Node suggestionNode = myTrie.find(inputWord);
        StringBuilder str = new StringBuilder();
        if(suggestionNode == null) {

            return null;
        }
        else {
            while(suggestionNode.getParentNode() != null) {
                str.append(suggestionNode.getNodeLetter());
                suggestionNode = suggestionNode.getParentNode();
            }
            str.reverse();
            return str.toString();
        }

    }
    /*
    private Node deletionDistance(String inputWord) {

    }
    private Node transpositionDistance(String inputWord) {

    }
    private Node alterationDistance(String inputWord) {

    }
    private Node insertionDistance(String inputWord) {

    }*/
}
