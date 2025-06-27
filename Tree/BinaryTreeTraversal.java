package Respositories.Java.Tree;

public class BinaryTreeTraversal {

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
        // Print the tree in a visually structured format
        printTree(root, "", false);
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
    }
    
}
