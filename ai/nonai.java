import java.util.Scanner;
public class nonai {
    private char[][] board;
    private char currentPlayer;

    public nonai() {
        board = new char[3][3];
        currentPlayer = 'X';

        // Initialize the board with empty spaces
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean gameFinished = false;

        while (!gameFinished) {
            // Print the current state of the board
            printBoard();

            // Get the current player's move
            System.out.println("Player " + currentPlayer + "'s turn. Enter the row (0-2):");
            int row = scanner.nextInt();
            System.out.println("Enter the column (0-2):");
            int col = scanner.nextInt();

            // Check if the move is valid
            if (isValidMove(row, col)) {

                board[row][col] = currentPlayer;


                if (checkWin()) {
                    System.out.println("Player " + currentPlayer + " wins!");
                    gameFinished = true;
                } else {

                    if (isBoardFull()) {
                        System.out.println("It's a draw!");
                        gameFinished = true;
                    } else {

                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    }
                }
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }


        printBoard();
    }

    private void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    private boolean isValidMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return false;
        }
        return board[row][col] == ' ';
    }

    private boolean checkWin() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == currentPlayer && board[1][j] == currentPlayer && board[2][j] == currentPlayer) {
                return true;
            }
        }

        // Check diagonals
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true;
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        nonai game = new nonai();
        game.play();
    }
}