import java.util.ArrayList;
public class Trie {
    // Wildcards
    final char WILDCARD = '.';
    private class TrieNode {
        // TODO: Create your TrieNode class here.
        TrieNode[] presentChars = new TrieNode[62];
        boolean isEnd;
        public TrieNode() {
            this.isEnd = false;
        }
    }
    private TrieNode root;
    public Trie() {
        // TODO: Initialise a trie class here.
        root = new TrieNode();
    }
    /**
     * Inserts string s into the Trie.
     *
     * @param s string to insert into the Trie
     */
    void insert(String s) {
        int charVal;
        TrieNode currentNode = root;
        for (int charIndex = 0; charIndex < s.length(); charIndex++){
            charVal = s.charAt(charIndex);
            if (charVal >= 48 && charVal <= 57) {
                charVal = charVal - 48;
            } else if (charVal >= 65 && charVal <= 90) {
                charVal = charVal - 55;
            } else if (charVal >= 97 && charVal <= 122) {
                charVal = charVal - 61;
            } else {
                charVal = -1;
            }
            if (currentNode.presentChars[charVal] == null){
                currentNode.presentChars[charVal] = new TrieNode();
            }
            currentNode = currentNode.presentChars[charVal];
        }
        currentNode.isEnd = true;
    }
    /**
     * Checks whether string s exists inside the Trie or not.
     *
     * @param s string to check for
     * @return whether string s is inside the Trie
     */
    boolean contains(String s) {
        int charVal;
        TrieNode currentNode = root;
        for (int charIndex = 0; charIndex < s.length(); charIndex++){
            charVal = s.charAt(charIndex);
            if (charVal >= 48 && charVal <= 57) {
                charVal = charVal - 48;
            } else if (charVal >= 65 && charVal <= 90) {
                charVal = charVal - 55;
            } else if (charVal >= 97 && charVal <= 122) {
                charVal = charVal - 61;
            } else {
                charVal = -1;
            }
            if (currentNode.presentChars[charVal] == null){
                return false;
            }
            currentNode = currentNode.presentChars[charVal];
        }
        return currentNode.isEnd;
    }
    /**
     * Searches for strings with prefix matching the specified pattern sorted by lexicographical order. This inserts the
     * results into the specified ArrayList. Only returns at most the first limit results.
     *
     * @param s       pattern to match prefixes with
     * @param results array to add the results into
     * @param limit   max number of strings to add into results
     */
    void prefixSearch(String s, ArrayList<String> results, int limit) {
        TrieNode currentNode = root;
        StringBuilder sb = new StringBuilder(s);
        if (s == null || root == null) {
            return;
        }
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c == WILDCARD) {
                for (int j = 0; j < 62; j++) {
                    if (currentNode.presentChars[j] != null) {
                        sb.setCharAt(i, convertIndexToChar(j));
                        prefixSearch(sb.toString(), results, limit);
                    }
                }
                return;
            } else {
                int charVal = convertCharToIndex(c);
                if (currentNode.presentChars[charVal] == null) {
                    return;
                }
                currentNode = currentNode.presentChars[charVal];
            }
        }
        // If the prefix exists
        combineWords(currentNode, sb, results, limit);
    }
    void combineWords(TrieNode node, StringBuilder prefix, ArrayList<String> results, int limit) {
        if (node.isEnd) {
            if (results.size() >= limit) {
                return;
            }
            results.add(prefix.toString());
        }
        for (int i = 0; i < 62; i++) {
            if (node.presentChars[i] != null) {
                if (results.size() >= limit) {
                    return;
                }
                prefix.append(convertIndexToChar(i));
                combineWords(node.presentChars[i], prefix, results, limit);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
    }
    int convertCharToIndex(char c) {
        int charVal;
        if (c >= 48 && c <= 57) {
            charVal = c - 48;
        } else if (c >= 65 && c <= 90) {
            charVal = c - 55;
        } else if (c >= 97 && c <= 122) {
            charVal = c - 61;
        } else {
            charVal = -1;
        }
        return charVal;
    }
    char convertIndexToChar(int index) {
        char c;
        if (index < 10) {
            c = (char)(index + 48);
        } else if (index < 36) {
            c = (char)(index + 55);
        } else {
            c = (char)(index + 61);
        }
        return c;
    }
    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }
    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("peter");
        t.insert("piper");
        t.insert("picked");
        t.insert("a");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");
        String[] result1 = t.prefixSearch("pe", 10);
        String[] result2 = t.prefixSearch("pe.", 10);
        for (int i = 0; i < result2.length; i++) {
            System.out.println(result2[i]);
        }
        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}