package Respositories.Java.Graph;

import java.util.ArrayList;
import java.util.List;

public class GraphRepresentation {

    public static void main(String[] args) {

        int n = 3, m = 3;

        List<List<Integer>> adjList = new ArrayList<>();
        
        for(int i = 0; i <= n; i++){
            adjList.add(new ArrayList<Integer>());
        }

        adjList.get(1).add(2);
        adjList.get(2).add(1);

        adjList.get(2).add(3);
        adjList.get(3).add(2);

        adjList.get(1).add(3);
        adjList.get(3).add(1);

        for(int i=1; i < n; i++){
            for(int j = 0; j < adjList.get(i).size(); j++){
                System.out.print(adjList.get(i).get(j)+ " ");
            }
            System.out.println();
        }
    }
}
