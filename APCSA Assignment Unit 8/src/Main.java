import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuePlaying = true;
        while(continuePlaying) { //While loop to check if the user wants to play more than one round
            String userinput = "";
            boolean checkIfContinue = true;
            int countRound = 1, x = -1, y = -1;
            int[][] arr = new int[3][3]; //make a 3 x 3 integer grid for the game in the form of a 2d array, 0 stand for empty, 1 stand for X and 2 stand for O
            while (checkIfContinue) { //while loop so that the game could be played by the users, one by one
                displayBoard(arr, countRound); //print the board by using 2d array and round number as parameters
                int[] move = getUserMove(scanner, arr, countRound); //store the user's input into an array
                updateBoard(arr, countRound, move[0], move[1]);//carry the user input, 2d array, and the round number to update the board.
                checkIfContinue = checkGameStatus(arr, countRound); //check if any of the users win
                countRound++;
            }
            while(true) { //while loop to check if the user wishes to keep playing
                System.out.println("Play Again? Y/N");
                String continueCheck = scanner.nextLine();
                if (continueCheck.equals("N")) {
                    continuePlaying = false;
                    break;
                } else if (continueCheck.equals("Y")) {
                    break;
                } else {
                    System.out.println("Invalid Input");
                }
            }
        }
    }

    private static void displayBoard(int[][] arr, int countRound) { //method to print out the board.
        String temp = " ";
        System.out.println("Round " + countRound);
        for (int i = 0; i < 3; i++) { //nested for loop to print the board
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == 1) {
                    temp = "X";
                } else if (arr[i][j] == 2) {
                    temp = "O";
                }
                System.out.print("[" + temp + "]");
                temp = " ";
            }
            System.out.println();
        }
        if (countRound % 2 == 1) { // print the lines to take the user's input.
            System.out.println("X, make your move (row, col):");
        } else {
            System.out.println("O, make your move (row, col):");
        }
    }

    private static int[] getUserMove(Scanner scanner, int[][] arr, int countRound) {
        int x = -1, y = -1;
        while (true) { //while loop to ensure the user input a valid input
            String userinput = scanner.nextLine();
            if (!(userinput.indexOf(",") >= 1)) {
                System.out.println("Invalid Input, Try Again: ");
            } else {
                String[] userInputArr = userinput.split(",");
                try {
                    x = Integer.parseInt(userInputArr[0]);
                    y = Integer.parseInt(userInputArr[1]);
                } catch (Exception e) { //catch any exception if the input doesn't work as intended
                }
                if (isValidMove(x, y, arr)) { // break the loop if the user's input pass by the next method
                    break;
                }
                System.out.println("Invalid Input, Try Again: ");
            }
        }
        return new int[]{x, y};
    }

    private static boolean isValidMove(int x, int y, int[][] arr) {
        return 0 < x && x < 4 && 0 < y && y < 4 && arr[x - 1][y - 1] == 0; //check for any negative or inputs exceeding 3, used in the method getUserMove
    }

    private static void updateBoard(int[][] arr, int countRound, int x, int y) {
        if (countRound % 2 == 1) { // update the board by using the inputs gained from getUserMov
            arr[x - 1][y - 1] = 1;
        } else {
            arr[x - 1][y - 1] = 2;
        }
    }

    private static boolean checkGameStatus(int[][] arr, int countRound) {
        boolean check_not_tie = false;

        for (int i = 0; i < 3; i++) {
            // Check Rows
            if ((arr[i][0] != 0) && (arr[i][0] == arr[i][1]) && (arr[i][1] == arr[i][2])) {
                printWinner(arr[i][0]);
                return false;
            }
            // Check Columns
            if ((arr[0][i] != 0) && (arr[0][i] == arr[1][i]) && (arr[1][i] == arr[2][i])) {
                printWinner(arr[0][i]);
                return false;
            }
        }

        // Check Diagonal
        if ((arr[1][1] != 0) && (((arr[0][0] == arr[1][1]) && (arr[1][1] == arr[2][2])) || ((arr[0][2] == arr[1][1]) && (arr[1][1] == arr[2][0])))) {
            printWinner(arr[1][1]);
            return false;
        }

        // Check for tie
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                check_not_tie |= arr[i][j] == 0;
        }

        if (!check_not_tie) { // check for any ties
            System.out.println("Tie");
            return false;
        }

        return true;
    }

    private static void printWinner(int player) { //print out the winner according to 1 - X, 2 - O, used in method checkGameStatus
        if (player == 1) {
            System.out.println("X, You Win!");
        } else {
            System.out.println("O, You Win!");
        }
    }
}