package spell;

import java.io.IOException;
import java.io.File;
import java.sql.ClientInfoStatus;
import java.util.Scanner;
import java.util.ArrayList;

public class SpellCorrector implements ISpellCorrector {
    public Trie myTrie;

    public SpellCorrector() {
        myTrie = new Trie();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File dictionary = new File(dictionaryFileName);
        Scanner myScanner = new Scanner(dictionary);
        String str;
        while (myScanner.hasNext()) {
            str = myScanner.next();
            myTrie.add(str);
        }
        myScanner.close();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();
        Node suggestionNode = myTrie.find(inputWord);
        String suggestion;
        if (suggestionNode != null) {
            suggestion = suggestionNode.toString();
            return suggestion;
        } else {
            // Edit Distance of 1
            ArrayList<String> edit1 = new ArrayList<String>();
            ArrayList<String> potSuggestions = new ArrayList<String>();
            edit1.addAll(deletion(inputWord));
            edit1.addAll(transposition(inputWord));
            edit1.addAll(alteration(inputWord));
            edit1.addAll(insertion(inputWord));
            Node find;
            for (int i = 0; i < edit1.size(); i++) {
                find = myTrie.find(edit1.get(i));
                if (find != null) {
                    potSuggestions.add(edit1.get(i));
                }
            }

            if (!potSuggestions.isEmpty()) {
                suggestion = bestSuggestion(potSuggestions);
            } else { // Edit Distance of 2 if necessary
                ArrayList<String> edit2 = new ArrayList<String>();
                for (int i = 0; i < edit1.size(); i++) {
                    edit2.addAll(deletion(edit1.get(i)));
                    edit2.addAll(transposition(edit1.get(i)));
                    edit2.addAll(alteration(edit1.get(i)));
                    edit2.addAll(insertion(edit1.get(i)));
                }
                for (int i = 0; i < edit2.size(); i++) {
                    find = myTrie.find(edit2.get(i));
                    if (find != null) {
                        potSuggestions.add(edit2.get(i));
                    }
                }
                if (!potSuggestions.isEmpty()) {
                    suggestion = bestSuggestion(potSuggestions);
                } else { // If no suggestions found after 2 edit distances
                    suggestion = null;
                }
            }


        }
        return suggestion;
    }

    private ArrayList<String> deletion(String inputWord) {
        StringBuilder sb;
        ArrayList<String> list = new ArrayList<String>();
        if (inputWord.length() > 1) {
            for (int i = 0; i < inputWord.length(); i++) {
                sb = new StringBuilder(inputWord);
                sb.deleteCharAt(i);
                list.add(sb.toString());
            }
        }
        return list;
    }

    private ArrayList<String> transposition(String inputWord) {
        StringBuilder sb;
        ArrayList<String> list = new ArrayList<String>();
        if (inputWord.length() > 1) {
            for (int i = 0; i < inputWord.length(); i++) {
                if (i + 1 == inputWord.length()) {

                } else {
                    sb = new StringBuilder(inputWord);
                    sb.setCharAt(i, inputWord.charAt(i + 1));
                    sb.setCharAt(i + 1, inputWord.charAt(i));
                    list.add(sb.toString());
                }
            }
        }

        return list;
    }

    private ArrayList<String> alteration(String inputWord) {
        StringBuilder sb;
        ArrayList<String> list = new ArrayList<String>();
        if (inputWord.length() > 1) {
            for (int i = 0; i < inputWord.length(); i++) {
                for (int j = 0; j < myTrie.getAlphabet().length; j++) {
                    sb = new StringBuilder(inputWord);
                    sb.replace(i, i + 1, Character.toString(myTrie.getAlphabet()[j]));
                    list.add(sb.toString());
                }
            }
        }
        return list;
    }

    private ArrayList<String> insertion(String inputWord) {
        StringBuilder sb;
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i <= inputWord.length(); i++) {
            for (int j = 0; j < myTrie.getAlphabet().length; j++) {
                sb = new StringBuilder(inputWord);
                sb.insert(i, myTrie.getAlphabet()[j]);
                list.add(sb.toString());
            }
        }
        return list;
    }


    private Node alphabetOrder(Node currentNode, Node compare) {
        String current = currentNode.toString();
        String compared = current.toString();
        int currentVal = 0;
        int compareVal = 0;
        if(current.length() > compared.length()) {
            for(int i = 0; i < compared.length(); i++) {
                currentVal += current.charAt(i);
                compared += compared.charAt(i);
            }
        }
        else if(current.length() < compared.length()) {
            for(int i = 0; i < current.length(); i++) {

            }
        }
        else {
            for(int i = 0; i < compared.length(); i++) {

            }
        }

        if(currentVal > compareVal) {
            return compare;
        }
        else if(currentVal < compareVal) {
            return currentNode;
        }
        else {
            if(current.length() > compared.length()) {
                return compare;
            }
            else if(current.length() < compared.length()) {
                return currentNode;
            }

        }
        return currentNode;
    }

    private String bestSuggestion(ArrayList<String> list) {
        Node temp1 = null;
        Node temp2 = null;
        if (list.size() > 1) {
            while(list.size() > 1) {
                temp1 = myTrie.find(list.get(0));
                temp2 = myTrie.find(list.get(1));
                if (temp1.equals(temp2)) {
                    list.remove(0);
                } else if (temp1.getValue() > temp2.getValue()) {
                    list.remove(1);
                } else if (temp1.getValue() < temp2.getValue()) {
                    list.remove(0);
                } else if (temp1.getValue() == temp2.getValue()) {
                    Node first = alphabetOrder(temp1, temp2);
                    if (first.equals(temp1)) {
                        list.remove( 1);
                    } else {
                        list.remove(0);
                    }
                }

            }
        }
        return list.get(0);
    }
}


