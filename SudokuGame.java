import java.lang.*;
import java.util.Scanner;
import java.util.Random;

public class SudokuGame {
    static void rand_values(int[][] ib, int cl) {
        int max = 8;
        int max_val = 9;
        int min = 0;
        int min_val = 1;
        int rr = (int) ((Math.random() * (max - min)) + min);
        int rc = (int) ((Math.random() * (max - min)) + min);
        int rv = (int) ((Math.random() * (max_val - min_val)) + min_val);
        if (isValid(ib, rv, rr, rc)) {
            ib[rr][rc] = rv;
        }
    }

    public int[][] randomize(String d) {
        int[][] board = { 
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 } 
        };

        int ediff = 60;
        int mdiff = 40;
        int hdiff = 30;

        if (d.equals("Easy")) {
            for (int w = 0; w < ediff; w++) {
                rand_values(board, ediff);
            }
        }
        if (d.equals("Medium")) {
            for (int w = 0; w < mdiff; w++) {
                rand_values(board, mdiff);
            }
        }
        if (d.equals("Hard")) {
            for (int w = 0; w < hdiff; w++) {
                rand_values(board, hdiff);
            }
        }
        return board;
    }

    public boolean solve(int[][] b) {
        // Backtracking Algorithm
        // 1. Choose Empty
        // 2. Try all number 1-9
        // 3. Check validity after all each # and if not backtrack
        for (int row = 0; row < b.length; row++) {
            for (int column = 0; column < b.length; column++) {
                if (b[row][column] == 0) {
                    for (int i = 1; i <= 9; i++) {
                        b[row][column] = i;
                        if (isValid(b, i, row, column) && solve(b)) {
                            return true;
                        }
                        b[row][column] = 0;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    static boolean isValid(int[][] b, int num, int row, int column) {
        // check row
        for (int i = 0; i < b[0].length; i++) {
            if (b[row][i] == num && column != i) {
                return false;
            }
        }
        // check column
        for (int i = 0; i < b.length; i++) {
            if (b[i][column] == num && row != i) {
                return false;
            }
        }
        // check subsection (possible solutions: 0, 1, 2)
        int box_x = (int) Math.floor(column / 3);
        int box_y = (int) Math.floor(row / 3);

        for (int i = box_y * 3; i < box_y * 3 + 3; i++) {
            for (int j = box_x * 3; j < box_x * 3 + 3; j++) {
                if (b[i][j] == num && row != i && column != j) {
                    return false;
                }
            }
        }
        return true;

    }

    public void printBoard(int[][] b) {
        for (int row = 0; row < b.length; row++) {
            if ((row % 3) == 0 && row != 0) {
                System.out.println(" - - - - - - - - - - - - - - - ");
            }
            for (int column = 0; column < b.length; column++) {
                if ((column % 3) == 0 && column != 0) {
                    System.out.print(" | ");
                }
                if (b[row][column] != 0) {
                    System.out.print(" " + b[row][column] + " ");

                } else {
                    System.out.print(" X ");
                }

            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try {
            Scanner diff = new Scanner(System.in);
            System.out.println("Choose a difficulty (Easy, Medium, Hard): ");
            String difficulty = diff.nextLine();
            if (difficulty.equals("Easy") || difficulty.equals("Medium") || difficulty.equals("Hard")) {
                System.out.println("___________Sudoku Board____________");
                SudokuGame sudokuObj = new SudokuGame();
                int[][] rand_board = sudokuObj.randomize(difficulty);
                sudokuObj.printBoard(rand_board);

                Scanner await = new Scanner(System.in);
                System.out.println("Instructions: to view solution press return.");
                String response = await.nextLine();

                if (!response.equals(" ")) {
                    sudokuObj.solve(rand_board);
                    System.out.println("___________Solution___________");
                    sudokuObj.printBoard(rand_board);
                }
            } else {
                System.out.println("Please input valid values!");
            }
        } catch (Exception e) {
            System.out.println("Looks like there was an error. Please try again.");
            System.out.println(e);
        }


    }
}
