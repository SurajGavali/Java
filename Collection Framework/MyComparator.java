import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

// public class MyComparator implements Comparator {
    
    
//     @Override
//     public int compare(Integer i1, Integer i2){

//         return i2 - i1;
//     }
// }

class Main{

    public static void main(String[] args) {
        
        List<Integer> sampleList = new ArrayList<>();

        sampleList.add(20);
        sampleList.add(10);
        sampleList.add(30);

        sampleList.sort((a,b) -> b - a);

        System.out.println(sampleList);
    }
}