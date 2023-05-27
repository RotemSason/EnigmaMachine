package Screens.DMoperational;

import java.util.*;

public class Trie {

    private TrieNode root;
    private int size;

    public Trie() {
        root = new TrieNode();
        size = 0;
    }

    public boolean add(String word) {
        TrieNode trie = root;
        if (trie == null || word == null)
            return false;

        char[] chars = word.toCharArray();
        int counter = 0;
        while (counter < chars.length) {
            Set childs = trie.getChildren().keySet();
            if (!childs.contains(chars[counter])) {
                insertChar(trie, chars[counter]);
                if (counter == chars.length - 1) {
                    getChild(trie, chars[counter]).setIsWord(true);
                    size++;
                    return true;
                }
            }
            trie = getChild(trie, chars[counter]);
            if (trie.getText().equals(word) && !trie.isWord()) {
                trie.setIsWord(true);
                size++;
                return true;
            }
            counter++;
        }
        return false;
    }

    public boolean find(String str) {
        Map<Character, TrieNode> children = root.getChildren();
        List<String> lst=new ArrayList<>();
        TrieNode t = null;
        if(t.isWord()){
            lst.add(t.getText());
        }
        for (int i = 0; i < children.size(); i++) {
            // t = children.get();
            children = t.getChildren();
        }


        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (children.containsKey(c)) {
                t = children.get(c);
                children = t.getChildren();
            } else return false;
        }

        return true;
    }
   /* void printAllStringsInTrie(TrieNode t, String prefix) {
        if (t.isWord()) System.out.println(prefix);
        for (int i = 0; i < t.getChildren().size(); i++) {
            if (t.getChildren().get(i)!= null) {
                printAllStringsInTrie(t.getChildren().get(i), prefix + ('a' + i));
            }
        }
    }*/



    public List<String> findAllWordsForPrefix(String prefix, TrieNode root) {
        List<String> words = new ArrayList<>();
        TrieNode current = root;
        for(Character c: prefix.toCharArray()) {
            TrieNode nextNode = current.getChildren().get(c);
            if(nextNode == null) return words;
            current = nextNode;
        }
        if(!current.getChildren().isEmpty()) {
            findAllWordsForPrefixRecursively(prefix, current, words);
        } else {
            if(current.isWord()) words.add(prefix);
        }
        return words;
    }
    static void findAllWordsForPrefixRecursively(String prefix, TrieNode node, List<String> words) {
        if(node.isWord()) words.add(prefix);
        if(node.getChildren().isEmpty()) {
            return;
        }
        for(Character c: node.getChildren().keySet()) {
            findAllWordsForPrefixRecursively(prefix + c, node.getChildren().get(c), words);
        }
    }
    /*public boolean find(TrieNode t, String c) {
        children.containsKey(c);
        Map<Character, TrieNode> children = t.getChildren();

        if(t.isWord()){
            lst.add(t.getText());
        }
        if (children.containsKey(c)) {
            t = children.get(c);
            find(t,)
        }


        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (children.containsKey(c)) {
                t = children.get(c);
                children = t.getChildren();
            } else return false;
        }

        return true;
    }*/

    public boolean remove(String str) {

        return findNode(root, str);
    }

    private TrieNode getChild(TrieNode trie, Character c) {
        return trie.getChildren().get(c);
    }

    private TrieNode insertChar(TrieNode trie, Character c) {
        if (trie.getChildren().containsKey(c)) {
            return null;
        }

        TrieNode next = new TrieNode(trie.getText() + c.toString());
        trie.getChildren().put(c, next);
        return next;
    }

    private boolean findNode(TrieNode trie, String s) {

        Map<Character, TrieNode> children = root.getChildren();

        TrieNode parent = null;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (children.containsKey(c)) {
                parent = trie;
                trie = children.get(c);
                children = trie.getChildren();
                if (trie.getText().equals(s)) {

                    parent.getChildren().remove(c);
                    trie = null;
                    return true;
                }
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }
    public TrieNode getRoot(){return root;}

}