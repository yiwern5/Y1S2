/**
 * ScapeGoat Tree class
 * <p>
 * This class contains some basic code for implementing a ScapeGoat tree. This version does not include any of the
 * functionality for choosing which node to scapegoat. It includes only code for inserting a node, and the code for
 * rebuilding a subtree.
 */
public class SGTree {
    // Designates which child in a binary tree
    enum Child {LEFT, RIGHT}
    /**
     * TreeNode class.
     * <p>
     * This class holds the data for a node in a binary tree.
     * <p>
     * Note: we have made things public here to facilitate problem set grading/testing. In general, making everything
     * public like this is a bad idea!
     */
    public static class TreeNode {
        int key;
        int weight = 1;
        public TreeNode left = null;
        public TreeNode right = null;
        TreeNode(int k) {
            key = k;
        }
    }
    // Root of the binary tree
    public TreeNode root = null;
    /**
     * Counts the number of nodes in the specified subtree.
     *
     * @param node  the parent node, not to be counted
     * @param child the specified subtree
     * @return number of nodes
     */
    public int countNodes(TreeNode node, Child child) {
        // TODO: Implement this
        if (node == null) {
            return 0;
        }
        if (child == Child.LEFT) {
            return countNodes(node.left, Child.LEFT) + countNodes(node.left, Child.RIGHT) + (node.left != null ? 1 : 0);
        } else if (child == Child.RIGHT) {
            return countNodes(node.right, Child.LEFT) + countNodes(node.right, Child.RIGHT) + (node.right != null ? 1 : 0);
        } else {
            return 0;
        }
    }
    /**
     * Builds an array of nodes in the specified subtree.
     *
     * @param node  the parent node, not to be included in returned array
     * @param child the specified subtree
     * @return array of nodes
     */
    public TreeNode[] enumerateNodes(TreeNode node, Child child) {
        int size = countNodes(node, child);
        TreeNode[] arr = new TreeNode[size];
        if (child == Child.LEFT) {
            inorderTraversal(node.left, arr, 0);
        } else if (child == Child.RIGHT) {
            inorderTraversal(node.right, arr, 0);
        }
        return arr;
    }
    private int inorderTraversal(TreeNode node, TreeNode[] arr, int index) {
        if (node == null) {
            return index;
        }
        index = inorderTraversal(node.left, arr, index);
        arr[index++] = node;
        index = inorderTraversal(node.right, arr, index);
        return index;
    }
    /**
     * Builds a tree from the list of nodes Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */
    public TreeNode buildTree(TreeNode[] nodeList) {
        // TODO: Implement this
        if (nodeList == null) {
            return null;
        }
        return helper(nodeList, 0, nodeList.length - 1);
    }
    private TreeNode helper(TreeNode[] nodeList, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = start + (end - start) / 2;
        TreeNode node = nodeList[mid];
        node.left = helper(nodeList, start, mid - 1);
        node.right = helper(nodeList, mid + 1, end);
        return node;
    }
    /**
     * Determines if a node is balanced. If the node is balanced, this should return true. Otherwise, it should return
     * false. A node is unbalanced if either of its children has weight greater than 2/3 of its weight.
     *
     * @param node a node to check balance on
     * @return true if the node is balanced, false otherwise
     */
    public boolean checkBalance(TreeNode node) {
        // TODO: Implement this
        if (node == null) {
            return true;
        }
        int leftChildrenWeight = (node.left != null) ? node.left.weight : 0;
        int rightChildrenWeight = (node.right != null) ? node.right.weight : 0;
        if (leftChildrenWeight > 2.0 / 3 * node.weight || rightChildrenWeight > 2.0 / 3 * node.weight) {
            return false;
        }
        return true;
    }
    /**
     * Rebuilds the specified subtree of a node.
     *
     * @param node  the part of the subtree to rebuild
     * @param child specifies which child is the root of the subtree to rebuild
     */
    public void rebuild(TreeNode node, Child child) {
        // Error checking: cannot rebuild null tree
        if (node == null) return;
        // First, retrieve a list of all the nodes of the subtree rooted at child
        TreeNode[] nodeList = enumerateNodes(node, child);
        // Then, build a new subtree from that list
        TreeNode newChild = buildTree(nodeList);
        // Finally, replace the specified child with the new subtree
        if (child == Child.LEFT) {
            node.left = newChild;
            fixWeights(node, Child.LEFT);
        } else if (child == Child.RIGHT) {
            node.right = newChild;
            fixWeights(node, Child.RIGHT);
        }
    }
    /**
     * Inserts a key into the tree.
     *
     * @param key the key to insert
     */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }
        TreeNode node = root;
        while (true) {
            node.weight++;
            if (key <= node.key) {
                if (node.left == null) break;
                node = node.left;
            } else {
                if (node.right == null) break;
                node = node.right;
            }
        }
        if (key <= node.key) {
            node.left = new TreeNode(key);
        } else {
            node.right = new TreeNode(key);
        }
        this.rebalanceTree(key);
    }
    public void rebalanceTree(int key){
        TreeNode highestUnbalancedNode = root;
        while(true){
            if (key <= highestUnbalancedNode.key) {
                if (highestUnbalancedNode.left == null) break;
                if (!checkBalance(highestUnbalancedNode.left)) {
                    this.rebuild(highestUnbalancedNode, Child.LEFT);
                    break;
                }
                highestUnbalancedNode = highestUnbalancedNode.left;
            } else {
                if (highestUnbalancedNode.right == null) break;
                if (!checkBalance(highestUnbalancedNode.right)) {
                    this.rebuild(highestUnbalancedNode, Child.RIGHT);
                    break;
                }
                highestUnbalancedNode = highestUnbalancedNode.right;
            }
        }
    }
    public int fixWeights(TreeNode node, Child child) {
        if (node == null) {
            return 0;
        }
        if (child == Child.LEFT) {
            node.weight = fixWeights(node.left, Child.LEFT) + fixWeights(node.left, Child.RIGHT) + 1;
        } else {
            node.weight = fixWeights(node.right, Child.LEFT) + fixWeights(node.right, Child.RIGHT) + 1;
        }
        return node.weight;
    }
    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        for (int i = 0; i < 100; i++) {
            tree.insert(i);
        }
        tree.rebuild(tree.root, Child.RIGHT);
        System.out.println(tree.checkBalance(tree.root));
    }
}