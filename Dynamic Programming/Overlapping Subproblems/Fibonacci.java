import java.util.Arrays;

public class Fibonacci {
    /*
     * Time and Space Complexity:
     *
     * Brute Force Recursive Approach:
     *   - Time Complexity: O(2^n) (exponential, due to repeated subproblems)
     *   - Space Complexity: O(n) (recursion stack depth)
     *
     * Dynamic Programming with Memoization:
     *   - Time Complexity: O(n) (each subproblem solved once)
     *   - Space Complexity: O(n) (for dp array + recursion stack)
     */
    // In this problem, we are repeatedly calculating values like fibonacci(2) in different branches of the recursion tree.
    // This repetition is known as "Overlapping Subproblems".
    // Overlapping subproblems occur when a recursive algorithm solves the same subproblem multiple times.
    // Dynamic Programming techniques (like memoization or tabulation) can be used to avoid this redundancy and improve efficiency.

    /*
     *                                          f(5)
     *                                      /           \
     *                                  f(4)           f(3)
     *                                 /   \          /   \
     *                             f(3)   f(2)      f(2)  f(1)
     *                            /   \   / \       / \
     *                        f(2) f(1) f(1) f(0) f(1) f(0)
     *                       /   \
     *                   f(1)  f(0)
     *
     * The full recursion tree for fibonacci(5) is shown above.
     * Each node splits into two until it reaches the base cases f(1) and f(0).
     */

    public static int fibonacci(int n){

        if(n <= 1){
            return n;
        }
        return fibonacci(n-1)+ fibonacci(n-2);
    }

    public static int fibonacciWithMemoization(int n, int[] dp){
    /*
    Memoization: This function optimizes the recursive Fibonacci calculation by storing
    previously computed results in the dp array. This avoids redundant recursive calls
    for the same subproblems, a key feature of dynamic programming (DP).
    If dp[n] is not -1, it means Fibonacci(n) was already computed and can be reused directly.
    */
        if(n <= 1){
            return n;
        }

        if(dp[n] != -1){
            return dp[n];
        }

        dp[n] = fibonacciWithMemoization(n-1,dp)+ fibonacciWithMemoization(n-2,dp);

        return dp[n];
    }

    public static void main(String[] args) {

        int n = 6;

        int[] dp = new int[n+1];

        Arrays.fill(dp, -1);
        
        System.out.println("Fibonacci of number is :: "+ fibonacci(5));
        System.out.println("Fibonacci of number with memoization :: "+ fibonacciWithMemoization(n,dp));

    }
}
