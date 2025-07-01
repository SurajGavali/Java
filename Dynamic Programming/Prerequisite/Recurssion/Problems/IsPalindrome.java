public class IsPalindrome {

    public static boolean isPalindrome(String s,int i){

        int n = s.length();

        if(i >= n/2){
            return true;
        }

        if(s.charAt(i) != s.charAt(n-i-1)){
            return false;
        }

        return isPalindrome(s,i+1);

    }

    public static void main(String[] args) {

        System.out.println("isPalindrome :: "+ isPalindrome("MADAP",0));
    }
    
}
