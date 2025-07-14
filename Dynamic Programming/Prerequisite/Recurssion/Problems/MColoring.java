import java.util.List;

public class MColoring {

    public static boolean graphColoring(List<Integer>[] graph, int[] color, int i, int m) {
        //your code goes here

        int n = graph.length;

        if(helper(0, graph, color, n, m)){
            return true;
        }

        return false;
    }

    public static boolean helper(int node, List<Integer>[] graph, int[] color, int n, int m){

        if(node == n){
            return true;
        }

        for(int i=1; i <= m; i++ ){
            if(isSafe(node, graph, color, n, i)){
                color[node] = i;
                if(helper(node+1, graph, color, n, m)){
                    return true;
                }
                color[node] = 0;
            }
        }
        return false;
    }

    public static boolean isSafe(int node, List<Integer>[] graph, int[] color, int n, int col){

        for(int it : graph[node]){
            if (color[it] == col) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int N = 4, M = 3, E = 5;
        int[][] edges = { {0,1}, {1,2}, {2,3}, {3,0}, {0,2} };

        List<Integer>[] graph = new java.util.ArrayList[N];
        for (int i = 0; i < N; i++) graph[i] = new java.util.ArrayList<>();
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]); // undirected
        }
        int[] color = new int[N];
        boolean result = graphColoring(graph, color, 0, M);
        System.out.println("Is coloring possible? " + result);
    }
}
