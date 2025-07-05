import java.util.ArrayList;
import java.util.List;

public class CombinationSum {

    public static void combinationSum(int ind, int target, int[] arr, List<Integer> res){

        if(ind == arr.length){
            if(target == 0){
                System.out.println(res);
            }
            return;
        }

        if(arr[ind] <= target){

            res.add(arr[ind]);
            combinationSum(ind, target-arr[ind], arr, res);
            res.remove(res.size()-1);
        }
        combinationSum(ind+1, target, arr, res);
    }

    public static void main(String[] args) {

        int[] arr = new int[]{2,3,6,7};
        combinationSum(0,7,arr,new ArrayList<>());
        
    }
}
