import java.util.*;

public class DistinctExpression {
    public static int[] distinctExpressions(int[] nums) {
        Set<Integer> res = new HashSet<>(); // Use a set for distinct values
        helper(nums, res, 1, nums[0]);
        // Convert set to sorted array
        return res.stream().sorted().mapToInt(Integer::intValue).toArray();
    }

    public static void helper(int[] nums, Set<Integer> res, int ind, int sum) {
        if (ind == nums.length) {
            res.add(sum);
            return;
        }
        // Include '+'
        helper(nums, res, ind + 1, sum + nums[ind]);
        // Include '-'
        helper(nums, res, ind + 1, sum - nums[ind]);
    }

    public static void main(String[] args) {
        int[] res = distinctExpressions(new int[]{1, 2, 3});
        for (int i : res) {
            System.out.println(i);
        }
    }
}
