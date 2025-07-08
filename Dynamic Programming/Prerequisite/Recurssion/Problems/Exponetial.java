public class Exponetial {

    // Iterative
    public static double powI(int x, int n){

        int ans = 1;

        for(int i =0; i < n; i++){
            ans = ans * x;
        }

        return ans;
    }

    // Optimal

    public static double pow(int x, int n){

        double ans = 1;
        int m = n;

        if(n < 0){
            n = -n;
        }

        while(n > 0){

            if(n%2 == 0){

                n = n/2;
                x = x * x;
            } else{

                ans = ans * x;
                n--;
            }
        }

        if(m < 0){
            return 1.0/ans;
        }

        return ans;
    }

    // Recursive

    public static double powR(int x, int n) {
        if (n == 0) return 1;
        if (n < 0) return 1.0 / powR(x, -n);

        double half = powR(x, n / 2);

        if (n % 2 == 0) {
            return half * half;
        } else {
            return x * half * half;
        }
    }

    public static void main(String[] args) {
        // System.out.println(powI(2,5));
        // System.out.println(pow(2,5));
        // System.out.println(pow(2,-2));
        System.out.println(powR(2, 5));
    }
    
}
