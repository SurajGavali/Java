import java.util.*;

public class NQueens {

    public static List<List<String>> nQueens(int n){

        List<List<String>> ans = new ArrayList<>();
        char[][] board = new char[n][n];

        int[] upperDiagonal = new int[2*n-1];
        int[] leftRow = new int[n];
        int[] lowerDiagonal = new int[2*n-1];

        for(int row = 0; row < n; row++){
            for(int col = 0; col < n; col++){
                board[row][col] = '.';
            }
        }  

        helper(board,ans,0,n, upperDiagonal, leftRow, lowerDiagonal);

        return ans;
    }

    public static void helper(char[][] board, List<List<String>> ans, int col, int n, int[] upperDiagonal, int[] leftRow, int[] lowerDiagonal){

        if(col == n){
            List<String> temp = new ArrayList<>();
            for(int i = 0; i < n; i++){
                temp.add(new String(board[i]));
            }
            ans.add(temp);
            return;
        }

        for(int row=0; row < n; row++){
            if(upperDiagonal[(n-1) + (col - row)] == 0 && leftRow[row] == 0 && lowerDiagonal[row+col] == 0){
                board[row][col] = 'Q';
                upperDiagonal[(n-1) + (col - row)] = 1;
                leftRow[row] = 1;
                lowerDiagonal[row+col] = 1;
                helper(board, ans, col+1, n, upperDiagonal, leftRow, lowerDiagonal);
                board[row][col] = '.';
                upperDiagonal[(n-1) + (col - row)] = 0;
                leftRow[row] = 0;
                lowerDiagonal[row+col] = 0;
            }
        }
    }

    public static boolean isSafe(char[][] board, int row, int col, int n){

        int tempRow = row;
        int tempCol = col;

        // left upwards
        while(row >= 0 && col >= 0){
            if(board[row][col] == 'Q'){
                return false;
            }
            row--;
            col--;
        }

        // left wards
        row = tempRow;
        col = tempCol;

        while(col >= 0){
            if(board[row][col] == 'Q'){
                return false;
            }
            col--;
        }

        // left Downwards
        row = tempRow;
        col = tempCol;

        while(row < n && col >= 0){
            if(board[row][col] == 'Q'){
                return false;
            }
            row++;
            col--;
        }
        return true;
    }

    public static void main(String[] args) {

        int n = 4;

        List<List<String>> res = nQueens(n);

        System.out.print(res);
    }
}
