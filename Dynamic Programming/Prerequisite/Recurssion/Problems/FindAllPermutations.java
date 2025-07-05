import java.util.ArrayList;
import java.util.List;

public class FindAllPermutations {

    public static void swap(int i, int j, int[] arr){

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public static void findAllPermutations(int[] arr, int[] tracker, List<Integer> res){

        if(res.size() == arr.length){
            System.out.println(res);
            return;
        }

        for(int i = 0; i < arr.length; i++){

            if(tracker[i] == 0){


                res.add(arr[i]);
                tracker[i] = 1;

                findAllPermutations(arr,tracker,res);

                res.remove(res.size()-1);
                tracker[i] = 0;
            }
        }
    }


    public static void findAllPermutationsWithOutTracker(int ind, int[] arr){

        if(ind == arr.length){

            List<Integer> res = new ArrayList<>();

            for(int i : arr){
                res.add(i);
            }
            System.out.println(res);
            return;
        } 

        for(int i = ind; i < arr.length; i++){

            swap(ind, i, arr);
            findAllPermutationsWithOutTracker(ind+1,arr);
            swap(ind, i, arr);
        }
    }


    public static void main(String[] args) {

        int[] arr = new int[]{1,2,3};

        int[] tracker = new int[arr.length];

        findAllPermutations(arr,tracker, new ArrayList<>());

        System.out.println("--------------");

        findAllPermutationsWithOutTracker(0,arr);
        
    }
}
