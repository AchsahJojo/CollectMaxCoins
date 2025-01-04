/*
 * INSTRUCTIONS:
 *     This is the starting Java code for hw10_2.
 *     Note that the current filename is "Main.java". 
 *     When you finish your development, copy the file.
 *     Then, rename the copied file to "Main_hw10_2.java".
 *     After that, upload the renamed file to Canvas.
 */

// Finish the head comment with Abstract, ID, Name, and Date.
/*
 * Title: Main.java
 * Abstract: Collect maximum number of coins on an n x m board
which was covered in the class
 * ID: 5687
 * Name: Achsah Jojo
 * Date: 12/05/2024
 */
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int rows = s.nextInt();
        int cols = s.nextInt();

        if (rows > 0 && rows <= 25 && cols > 0 && cols <= 25) {
            int[][] matrix = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = s.nextInt();
                }
            }
            // 2D array to store the maximum number of coins that can be collected : recoridng aspect of dynamic programming
            int[][] memo = new int[rows][cols];
            //fill with -1 firtst
            for (int[] row : memo) Arrays.fill(row, -1);

            //call function
            collectMaxCoins(matrix, rows, cols, memo);

            //f there’s no path to reach to the
            //destination due to the inaccessible cells or there’s no coin collected (= zero coin) at the destination,
            //display “Invalid input”.
            if (memo[rows - 1][cols - 1] <= 0) {
                System.out.println("Max coins: 0");
                System.out.println("Path: Invalid input");
            } else {
                System.out.println("Max coins: " + memo[rows - 1][cols - 1]);
                List<String> path = makePath(matrix, memo);
                System.out.println("Path: " + String.join("->", path));
            }
        } else {
            System.out.println("Invalid board size");
        }
    }

    public static void collectMaxCoins(int[][] matrix, int rows, int cols, int[][] memo) {
        // If the starting cell contains 1 then the value is 1
        //otherwise its 0
        memo[0][0] = Math.max(0, matrix[0][0]);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                //Skip any cell marked as -1 cuz its a hole
                if (matrix[i][j] == -1) {
                    memo[i][j] = -1;
                    continue;
                }
                //check if there is a valid path from the cell above
                int fromAbove;
                if (i > 0 && memo[i - 1][j] != -1) {
                    fromAbove = memo[i - 1][j];
                } else {
                    fromAbove = -1;
                }
                //check if there is a valid path from the cell above
                int fromLeft;
                if (j > 0 && memo[i][j - 1] != -1) {
                    fromLeft = memo[i][j - 1];
                } else {
                    fromLeft = -1;
                }

                //if valid, then choose the bigger one either from the top cell or the left cell
                if (fromAbove != -1 || fromLeft != -1) {
                    int maxFromAboveOrLeft = Math.max(fromAbove, fromLeft);
                    int coinValue = 0;
                    if (matrix[i][j] == 1) {
                        coinValue = 1;
                    }
                    memo[i][j] = maxFromAboveOrLeft + coinValue;
                }
            }
        }
    }

    public static List<String> makePath(int[][] matrix, int[][] memo) {
        //use a list of strings to backtrack
        List<String> path = new ArrayList<>();
        int i = memo.length - 1;
        int j = memo[0].length - 1;
        // start at the bottom-right corner of the memo array
        //and add the current cell to the path till you reach top corner
        while (i > 0 || j > 0) {
            path.add("(" + (i + 1) + "," + (j + 1) + ")");

            int fromAbove = -1;
            int fromLeft = -1;
            //check for valid cell above and below
           // int fromAbove;
            if (i > 0) {
                fromAbove = memo[i - 1][j];
            } else {
                fromAbove = -1;
            }


           // int fromLeft;
            if (j > 0) {
                fromLeft = memo[i][j - 1];
            } else {
                fromLeft = -1;
            }

            //move to next cell
            if (fromAbove >= fromLeft) {
                //up value is bigger
                i--;
            } else {
                //down value bigger
                j--;
            }
        }
        path.add("(1,1)"); //always starts at 1,1
        Collections.reverse(path);
        return path;
    }

}


