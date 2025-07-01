package Respositories.Java.Tree;

import java.util.*;

public class BinaryTreeTraversal {

    static List<Integer> preOrderTraversalList = new ArrayList<>();
    static List<Integer> postOrderTraversalList = new ArrayList<>();
    static List<Integer> inOrderTraversalList = new ArrayList<>();

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // Helper function to print the tree in a visually structured format
    private static void printTree(TreeNode node, String prefix, boolean isLeft) {
        if (node == null) {
            return;
        }
        // Print the right subtree first (so it appears on top)
        printTree(node.right, prefix + (isLeft ? "│   " : "    "), false);
        // Print the current node
        System.out.println(prefix + (isLeft ? "└── " : "┌── ") + node.val);
        // Print the left subtree
        printTree(node.left, prefix + (isLeft ? "    " : "│   "), true);
    }

    public static void traverseTree(TreeNode root){
        // Preorder traversal: root, left, right
        if (root == null) return;

        preOrderTraversalList.add(root.val); // Pre Order Traversing
        // System.out.println(root.val); // Commented out: just traversing
        traverseTree(root.left);

        inOrderTraversalList.add(root.val); // // In Order Traversing
        traverseTree(root.right);

        postOrderTraversalList.add(root.val); // Post Order Traversing        
    }

    public static void main(String[] args) {

        TreeNode root = new TreeNode(1,
            new TreeNode(2,
                new TreeNode(4),
                new TreeNode(5)
            ),
            new TreeNode(6,new TreeNode(6), new TreeNode(0))
        );

        traverseTree(root);
        System.out.println(preOrderTraversalList);
        System.out.println(postOrderTraversalList);
        System.out.println(inOrderTraversalList);
    }
    
}
