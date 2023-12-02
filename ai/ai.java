
import java.util.*;

public class ai {
    private static final int SIZE = 3;
    private static final char EMPTY = ' ';
    private static final char PLAYER = 'X';
    private static final char AI_PLAYER = 'O';

    private char[][] board;
    private Random random;

    public ai() {
        board = new char[SIZE][SIZE];
        random = new Random();
    }

    public void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("---------");
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGameOver() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private boolean checkRows() {
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] != EMPTY && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int i = 0; i < SIZE; i++) {
            if (board[0][i] != EMPTY && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        return (board[0][0] != EMPTY && board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
                (board[0][2] != EMPTY && board[0][2] == board[1][1] && board[1][1] == board[2][0]);
    }

    public void playerMove(int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE && board[row][col] == EMPTY) {
            board[row][col] = PLAYER;
        }
    }

    public void aiMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = AI_PLAYER;
                    int score = minimax(false, 0);
                    board[i][j] = EMPTY;

                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }

        if (bestRow != -1 && bestCol != -1) {
            board[bestRow][bestCol] = AI_PLAYER;
        }
    }

    private int minimax(boolean isMaximizingPlayer, int depth) {
        if (isGameOver()) {
            if (isMaximizingPlayer) {
                return -1;
            } else {
                return 1;
            }
        } else if (isBoardFull()) {
            return 0;
        }

        if (isMaximizingPlayer) {
            int maxScore = Integer.MIN_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = AI_PLAYER;
                        int score = minimax(false, depth + 1);
                        board[i][j] = EMPTY;
                        maxScore = Math.max(maxScore, score);
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = PLAYER;
                        int score = minimax(true, depth + 1);
                        board[i][j] = EMPTY;
                        minScore = Math.min(minScore, score);
                    }
                }
            }
            return minScore;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ai game = new ai();

        game.initializeBoard();
        game.printBoard();

        while (!game.isGameOver() && !game.isBoardFull()) {
            System.out.print("Enter row (0-2): ");
            int row = scanner.nextInt();
            System.out.print("Enter column (0-2): ");
            int col = scanner.nextInt();

            game.playerMove(row, col);
            game.printBoard();

            if (!game.isGameOver() && !game.isBoardFull()) {
                game.aiMove();
                System.out.println("AI's turn:");
                game.printBoard();
            }
        }

        if (game.isGameOver()) {
            System.out.println("Game over. You " + (game.checkRows() || game.checkColumns() || game.checkDiagonals() ? "win!" : "lose!"));
        } else {
            System.out.println("It's a tie!");
        }
    }
}