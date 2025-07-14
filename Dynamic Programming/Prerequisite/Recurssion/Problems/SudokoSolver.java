public class SudokoSolver {

    public static boolean solve(int[][] sudoko){

        for(int row=0; row<9; row++){
            for(int col=0; col < 9; col++){
                if(sudoko[row][col] == 0){

                    for(int val=1; val <= 9; val++){

                        if(isValid(sudoko, row, col, val)){

                            sudoko[row][col] = val;

                            if(solve(sudoko)){
                                return true;
                            } else{
                                sudoko[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isValid(int[][] sudoko, int row, int col, int val){
        
        for(int i=0; i < 9; i++){
            // row check
            if(sudoko[row][i] == val){
                return false;
            }

            // column check
            if(sudoko[i][col] == val){
                return false;
            }

            //sub matrix check
            int subMatrixRow = 3 * (row/3) + i/3;
            int subMatrixCol = 3 * (col/3) + i%3;

            if(sudoko[subMatrixRow][subMatrixCol] == val){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        int[][] sudoko = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };


        solve(sudoko);

        for(int i=0; i < 9; i++){
            for(int j=0; j < 9; j++){
                System.out.print(sudoko[i][j] + " ");
            }
            System.out.println();
        }
    }
}
