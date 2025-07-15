import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InversionsInArray {

    public static int findTotalInversions(int[] arr){

        AtomicInteger count = new AtomicInteger(0);

        helper(arr,0,arr.length-1,count);

        return count.get();
    }

    public static void helper(int[] arr, int low, int high, AtomicInteger count){
        
        if(low >= high){
            return;
        }

        int mid = (low + high)/2;

        helper(arr, low, mid,count);
        helper(arr, mid+1, high,count);
        merge(arr,low,mid,high,count);
    }

    public static void merge(int[] arr,int low, int mid, int high, AtomicInteger count){

        List<Integer> temp = new ArrayList<>();

        int left = low;
        int right = mid+1;

        while(left <= mid && right <= high){

            if(arr[left] <= arr[right]){
                temp.add(arr[left]);
                left++;
            } else{
                temp.add(arr[right]);
                count.addAndGet(mid - left + 1);
                right++;
            }
        }

        while(left <= mid){
            temp.add(arr[left]);
            left++;
        }

        while(right <= high){
            temp.add(arr[right]);
            right++;
        }

        for(int i = low; i <= high; i++){
            arr[i] = temp.get(i - low);
        }
    }
    

    public static void main(String[] args) {

        int[] arr = new int[]{5,3,2,4,1};
        
        System.out.println("Total numbers of inversions are :: "+findTotalInversions(arr));
    }
}
