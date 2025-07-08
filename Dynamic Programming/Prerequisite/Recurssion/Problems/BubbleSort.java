public class BubbleSort {

    public static int[] bubbleSort(int[] nums) {

        helper(nums,0,nums.length-1);

        return nums;
    }

    public static void helper(int[] nums, int start, int end){

        while(start < end){
            if(nums[start] > nums[start+1]){
                swap(start,start+1,nums);
            }
            start++;
        }
        if(end > 1){
            helper(nums,0,end-1);
        }
    }

    public static void swap(int i, int j, int[] nums){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] res = bubbleSort(new int[]{8,4,6,3,2,2,3,44,5,6,7,5,});

        for (int i : res) {
            System.out.println(i);
        }
    }
    
}
