import java.util.ArrayList;
import java.util.List;

public class kthPermutation {
    // Recurssion

    private static void backtrack(int n, String path, boolean[] used, List<String> permutations) {
    if (path.length() == n) {
        permutations.add(path);
        return;
    }
    for (int i = 1; i <= n; i++) {
        if (!used[i]) {
            used[i] = true;
            backtrack(n, path + i, used, permutations);
            used[i] = false;
        }
    }
}
    
    //Mathematical approach
    public static String findKthPermutation(int n, int k){

        String res = "";

        List<Integer> nums = new ArrayList<>();

        int fact = 1;
        for(int i=1; i<n; i++){
            fact = fact * i;
            nums.add(i);
        }

        nums.add(n);
        k = k-1;

        while(true){
            res = res + nums.get(k/fact);
            nums.remove(k/fact);

            if(nums.size() == 0){
                break;
            }

            k = k % fact;
            fact = fact / nums.size();
        }
        return res;
    }

    public static void main(String[] args) {
        
        System.out.println("Kth Permutation :: "+findKthPermutation(4,17));
    }
}
