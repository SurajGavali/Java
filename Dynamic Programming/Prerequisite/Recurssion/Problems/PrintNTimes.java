public class PrintNTimes {

    public static void printNTimes(int i, int n){

        if(i > n){
            return;
        }

        System.out.println("Suraj");

        printNTimes(i+1, n);

    }

    public static void printNumbers(int i, int n){

        if(i > n){
            return;
        }

        System.out.println(i);

        printNumbers(i+1, n);

        System.out.println(i);

    }

    public static void printNumbersReverse(int i){

        if(i < 1){
            return;
        }

        System.out.println(i);

        printNumbersReverse(i-1);

        System.out.println(i);

    }

    public static void main(String[] args) {

        printNTimes(1, 3);
        printNumbers(1, 3);
        printNumbersReverse(3);
        
    }
    
}
