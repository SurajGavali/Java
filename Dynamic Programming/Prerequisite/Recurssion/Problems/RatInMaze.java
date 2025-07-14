import java.util.ArrayList;
import java.util.List;

public class RatInMaze {

    public static List<String> findPath(int[][] maze, int n, int row, int col){

        List<String> validPaths = new ArrayList<>();
        int[][] visitedPath = new int[n][n];

        int[] di = {+1,0,0,-1};
        int[] dj = {0,-1,1,0};

        //helper(row, col,maze,"",visitedPath,n,validPaths);

        helper1(row, col, maze, "", visitedPath, n, validPaths, di, dj);

        return validPaths;
    }

    public static void helper(int row, int col, int[][] maze, String path, int[][] visitedPath, int n, List<String> validPaths){

        if(row == n-1 && col == n-1) {
            validPaths.add(path);
            return;
        }

        // downward
        if(row+1 < n && visitedPath[row+1][col] == 0 && maze[row+1][col] == 1){
            visitedPath[row][col] = 1;
            helper(row+1, col, maze, path+"D", visitedPath, n, validPaths);
            visitedPath[row][col] = 0;
        }

        //left
        if(col-1 > 0 && visitedPath[row][col-1] == 0 && maze[row][col-1] == 1){
            visitedPath[row][col] = 1;
            helper(row, col-1, maze, path+"L", visitedPath, n, validPaths);
            visitedPath[row][col] = 0;
        }

        //right
        if(col+1 < n && visitedPath[row][col+1] == 0 && maze[row][col+1] == 1){
            visitedPath[row][col] = 1;
            helper(row, col+1, maze, path+"R", visitedPath, n, validPaths);
            visitedPath[row][col] = 0;
        }

        // upward
        if(row-1 > 0 && visitedPath[row-1][col] == 0 && maze[row-1][col] == 1){
            visitedPath[row][col] = 1;
            helper(row-1, col, maze, path+"U", visitedPath, n, validPaths);
            visitedPath[row][col] = 0;
        }
    }


    //all in one with one if statement
    public static void helper1(int row, int col, int[][] maze, String path, int[][] visitedPath, int n, List<String> validPath, int[] di, int[] dj){

        if(row == n-1 && col == n-1) {
            validPath.add(path);
            return;
        }

        String dir = "DLRU";

        for(int i=0; i < 4; i++){
            int nexti = row + di[i];
            int nextj = col + dj[i];

            if(nexti >= 0 && nextj >= 0 && nexti < n && nextj < n && visitedPath[nexti][nextj] == 0 && maze[nexti][nextj] == 1){

                visitedPath[row][col] = 1;
                helper1(nexti, nextj, maze, path+dir.charAt(i), visitedPath, n, validPath, di, dj);
                visitedPath[row][col] = 0;
            }
        }
    }


    public static void main(String[] args) {

        List<String> ans = new ArrayList<>();

        int[][] maze = {
            {1,0,0,0},
            {1,1,0,1},
            {1,1,0,0},
            {0,1,1,1}
        };

        if(maze[0][0] == 1) ans = findPath(maze,4,0,0);
        
        System.out.println(ans);
    }    
}
