package Respositories.Java.Tree;

public class MaxDepthBT {

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

    private int maxDepth(TreeNode root){

        if(root == null) return 0;

        int left = maxDepth(root.left);
        int right = maxDepth(root.right);

        return 1+ Math.max(left, right);
    }
    
    public static void main(String[] args) {
        
        // Sample tree:
        //      1
        //     / \
        //    2   3
        //   /
        //  4
        TreeNode root = new TreeNode(1,
                            new TreeNode(2,
                                new TreeNode(4), null),
                            new TreeNode(3));
        MaxDepthBT tree = new MaxDepthBT();
        int depth = tree.maxDepth(root);
        System.out.println("Max depth: " + depth); // Output should be 3
    }
}
