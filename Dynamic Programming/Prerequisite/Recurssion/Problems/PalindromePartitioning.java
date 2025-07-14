import java.util.ArrayList;
import java.util.List;

public class PalindromePartitioning {
    
    public static boolean isPalindrome(String s, int start, int end){
        while(start <= end){
            if(s.charAt(start) != s.charAt(end)){
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public static List<List<String>> partitioning(String s){

        List<List<String>> res = new ArrayList<>();
        List<String> path = new ArrayList<>();

        helper(res,path,0,s);

        return res;
    }

    public static void helper(List<List<String>> res, List<String> path, int partition, String s){

        if(partition == s.length()){
            res.add(new ArrayList<>(path));
            return;
        }

        for(int i=partition; i < s.length(); i++){
            if(isPalindrome(s,partition,i)){
                path.add(s.substring(partition, i+1));
                helper(res, path, i+1, s);
                path.remove(path.size()-1);
            }
        }
    }
    

    public static void main(String[] args) {

        String given = "aabb";

        List<List<String>> ans = partitioning(given);

        System.out.println(ans); 
    }
}
