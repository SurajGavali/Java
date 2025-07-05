import java.util.ArrayList;
import java.util.List;

public class SumOfAllSubsets {

    public static void sumOfAllSubsets(int ind, int[] arr, List<Integer> res,int sum){

        if(ind == arr.length){
            System.out.println(sum);
            return;
        }

        sumOfAllSubsets(ind+1, arr, res, sum+arr[ind]);
        sumOfAllSubsets(ind+1, arr, res, sum);
    }


    public static void main(String[] args) {
        
        int[] arr = new int[]{1,2,3};

        sumOfAllSubsets(0,arr,new ArrayList<>(),0);
    }
    
}
