package tictactoe;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static char[][] board = new char[3][3];

    public static void main(String[] args) {
        fillBoard();
        printBoard();

        while (!gameFinished) {
            getCoordinates();
            countAxisMoves();
            checkAxisWins();
            checkColOrRowWins();
            checkGameState();
        }
    }

    static int cordX, cordY;
    static int axis1X, axis2X = 0;
    static int axis1O, axis2O = 0;
    static int winingOAmount, winingXAmount = 0;
    static int movesMade = 0;
    static boolean gameFinished = false;

    // prefill with empty cell ' ' to keep board compact
    public static void fillBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public static void printBoard() {
        System.out.println("---------");
        for (int i = 0; i < board.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public static void getCoordinates() {
        boolean quit = false;
        do {
            System.out.println("Enter the coordinates:");
            if (scanner.hasNextInt()) {
                int number1 = scanner.nextInt() - 1;
                int number2 = scanner.nextInt() - 1;

                if ((number1 < 0 || number2 < 0) || (number1 > 2 || number2 > 2)) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else {
                    cordX = number1;
                    cordY = number2;
                    makeMove(cordX, cordY);
                    quit = true;
                }
            } else {
                System.out.println("You should enter numbers!");
                scanner.nextLine();
            }
        } while (!quit);
    }

    public static void makeMove(int cordX, int cordY) {

        if (board[cordX][cordY] != ' ') {
            System.out.println("This cell is occupied! Choose another one!");
            getCoordinates();
        } else {
            board[cordX][cordY] = movesMade % 2 == 0 ? 'X' : 'O';
            printBoard();
            movesMade++;
        }
    }

    public static void countAxisMoves() {
        boolean leftAxis = cordX == cordY;// or Math.abs(j - i) == 0; // from top left 1 = 1
        boolean rightAxis = (cordX + cordY) - 2 == 0; //from top right (0 + 2) - 2 = 0

        if (leftAxis) {
            if (board[cordX][cordY] == 'X') {
                axis1X += 1;
            } else if (board[cordX][cordY] == 'O') {
                axis1O += 1;
            }
        }
        if (rightAxis) {
            if (board[cordX][cordY] == 'X') {
                axis2X += 1;
            } else if (board[cordX][cordY] == 'O') {
                axis2O += 1;
            }
        }
    }

    // three X or Y on axis
    public static void checkAxisWins() {
        if (axis1O == 3 || axis2O == 3) {
            winingOAmount = 1;
        } else if (axis1X == 3 || axis2X == 3) {
            winingXAmount = 1;
        }
    }

    public static void checkColOrRowWins() {

        boolean loopEnded = false;
        for (int i = 0; i < board.length && !loopEnded; i++) {
            int verticalX = 0;
            int verticalO = 0;
            int horizontalX = 0;
            int horizontalO = 0;
            for (int j = 0; j < board.length; j++) {
                // three X or Y in a row
                if (board[i][j] == 'X') {
                    horizontalX += 1;
                } else if (board[i][j] == 'O') {
                    horizontalO += 1;
                }
                // three X or Y in a column
                if (board[j][i] == 'X') {
                    verticalX += 1;
                } else if (board[j][i] == 'O') {
                    verticalO += 1;
                }
                // check if there is a winning row or column
                if (horizontalO == 3 || verticalO == 3) {
                    winingOAmount += 1;
                    loopEnded = true;
                } else if (horizontalX == 3 || verticalX == 3) {
                    winingXAmount += 1;
                    loopEnded = true;
                }
            }
        }
    }

    public static void checkGameState() {
        int maxPossibleMoves = board.length * board[0].length;
//      to win winningAmount have to be != 0
//      because there is possibility to make 2 wins (axis and row) with last one move
        if (winingOAmount != 0) {
            System.out.println("O wins");
            gameFinished = true;
        } else if (winingXAmount != 0) {
            System.out.println("X wins");
            gameFinished = true;
        }
        // All 9 moves were made, and there is no winner
        else if (movesMade == maxPossibleMoves) {
            System.out.println("Draw");
            gameFinished = true;
        }
    }
}