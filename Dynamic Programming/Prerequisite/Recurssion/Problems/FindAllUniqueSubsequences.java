import java.util.ArrayList;
import java.util.List;

public class FindAllUniqueSubsequences {

    public static void findAllUniqueSubsequences(int ind, int[] arr, List<Integer> res){

        // System.out.println(res);

        for(int i = ind; i < arr.length; i++){

            if(i != ind && arr[i] == arr[i-1]){
                continue;
            }

            res.add(arr[i]);
            findAllUniqueSubsequences(i+1, arr, res);
            res.remove(res.size()-1);
        }   
    }

    public static List<List<Integer>> powerSet(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> curr = new ArrayList<>();
        helper(nums, res, 0, curr);
        return res;
    }

    public static void helper(int[] nums, List<List<Integer>> res, int ind, List<Integer> curr){
        res.add(new ArrayList<>(curr));
        for(int i = ind; i < nums.length; i++){
            if(i != ind && nums[i] == nums[i-1]){
                continue;
            }
            curr.add(nums[i]);
            helper(nums, res, i+1, curr);
            curr.remove(curr.size()-1);
        }
    }


    public static void main(String[] args) {

        int[] arr = new int[]{1,1,2,3,3};

        // findAllUniqueSubsequences(0,arr,new ArrayList<>());

        System.out.println(powerSet(new int[]{1,2,3}));
        
    }
    
}
