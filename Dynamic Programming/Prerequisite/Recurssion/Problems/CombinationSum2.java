import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum2 {

    public static void combinationSum2(int ind, int[] arr, int target, List<Integer> res){

        if(target == 0) {
            System.out.println(res);
            return;
        }

        for(int i=ind; i < arr.length;i++){

            if(i > ind && arr[i] == arr[i-1]) continue;
            if(arr[i] > target) break;

            res.add(arr[i]);
            combinationSum2(i+1, arr, target-arr[i], res);
            res.remove(res.size()-1);
        }
    }




    public static void main(String[] args) {

        int[] arr = new int[]{1,3,1,2,2};

        Arrays.sort(arr);

        combinationSum2(0,arr,5,new ArrayList<>());
        
    }
}
