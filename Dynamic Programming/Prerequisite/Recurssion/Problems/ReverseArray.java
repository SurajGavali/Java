public class ReverseArray {

    public static void swap(int n1, int n2){

        int temp = n1;
        n1 = n2;
        n2 = temp;
    }

    public static void f1(int i, int j, int[] arr){

        if(i >= j){
            return;
        }

        swap(arr[i],arr[j]);

        f1(i+1,j-1,arr);
    }

    public static void f2(int i,int n,int[] arr){

        if(i >= n/2){
            return;
        }
        swap(arr[i],arr[n-i-1]);
        f2(i+1,n,arr);
    }

    public static void main(String[] args) {

        int[] arr = new int[]{1,2,3,4,5,6,7};

        // f1(0,arr.length-1,arr);

        // for(int i =0; i < arr.length; i++){
        //     System.out.println(arr[i]);
        // }
        f2(0,arr.length,arr);

        // System.out.println("---------");

        for(int j =0; j < arr.length; j++){
            System.out.println(arr[j]);
        }
    }
}
