import java.util.ArrayList;
import java.util.List;

public class FindAllUniqueSubsequences {

    public static void findAllUniqueSubsequences(int ind, int[] arr, List<Integer> res){

        System.out.println(res);

        for(int i = ind; i < arr.length; i++){

            if(i != ind && arr[i] == arr[i-1]){
                continue;
            }

            res.add(arr[i]);
            findAllUniqueSubsequences(i+1, arr, res);
            res.remove(res.size()-1);
        }   
    }


    public static void main(String[] args) {

        int[] arr = new int[]{1,1,2,3,3};

        findAllUniqueSubsequences(0,arr,new ArrayList<>());
        
    }
    
}
