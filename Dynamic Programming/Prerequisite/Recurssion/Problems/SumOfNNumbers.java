public class SumOfNNumbers {

    public static void main(String[] args) {

        System.out.println("Sum of first N numbers :: "+sumOfNums(3, 0));
        System.out.println("Sum of first N numbers :: "+sumOfNums1(3));
        System.out.println("fact :: "+factorial(5));
        
    }

    //Parameterized way
    public static int sumOfNums(int n, int sum){

        if(n < 1){
            return sum;
        }

        sum = sumOfNums(n-1, sum+n);

        return sum;
    }

    //Functional way

    public static int sumOfNums1(int n){

        if(n == 0){
            return 0;
        }

        return n + sumOfNums1(n-1);
    }

    //factorial

    public static int factorial(int n){

        if(n == 0){
            return 1;
        }

        return n * factorial(n-1);
    }
    
}
