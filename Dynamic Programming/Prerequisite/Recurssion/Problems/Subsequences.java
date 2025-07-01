import java.util.*;
public class Subsequences {

    // Contiguous: elements appear in consecutive order in the array
    // Example: For array [1, 2, 3, 4], [2, 3, 4] is a contiguous subsequence.
    // Non-contiguous: elements maintain order but are not consecutive
    // Example: For array [1, 2, 3, 4], [1, 3, 4] is a non-contiguous subsequence.

    public static void findAllSubsequences(int ind, List<Integer> arr, List<Integer> curr, List<List<Integer>> res) {
        int n = arr.size();
        if (ind == n) {
            // Add a copy of the current subsequence to the result
            res.add(new ArrayList<>(curr));
            System.out.println(curr);
            return;
        }
        // Include the current element
        curr.add(arr.get(ind));
        findAllSubsequences(ind + 1, arr, curr, res);
        curr.remove(curr.size() - 1); // backtrack
        // Exclude the current element
        findAllSubsequences(ind + 1, arr, curr, res);
    }


    public static boolean findAllSubsequencesWhereSumIsN(int ind, List<Integer> arr, List<Integer> curr, List<List<Integer>> res, int sum,int s){

        // int s = 0;

        if(ind == arr.size()){

            if(s == sum){

                System.out.println(curr);
                return true;
            } else{
                return false;
            }
        }

        curr.add(arr.get(ind));

        if(findAllSubsequencesWhereSumIsN(ind+1, arr, curr, res, sum,s+arr.get(ind))) return true;

        curr.remove(curr.size()-1);

        if(findAllSubsequencesWhereSumIsN(ind+1, arr, curr, res, sum,s)) return true;

        return false;
    }

    public static void main(String[] args) {

        List<Integer> sampleList = Arrays.asList(1, 2, 3, 4, 3, 4);

        List<List<Integer>> resultList = new ArrayList<>();

        // findAllSubsequences(0, sampleList, new ArrayList<>(), resultList);

        // System.out.println("-----------------");

        findAllSubsequencesWhereSumIsN(0,sampleList,new ArrayList<>(),resultList,5,0);

    }
    
}
