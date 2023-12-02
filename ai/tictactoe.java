import java.util.Scanner;

class tictactoe {
    char[][] board;
    char currentPlayer;
    String playerName;

    public tictactoe(String playerName) {
        board = new char[3][3];
        currentPlayer = 'X';
        this.playerName = playerName;
        initializeBoard();
    }

    void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public void displayPlayerName() {
        System.out.println("Player's Name: " + playerName);
    }

    boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    boolean checkWin(char player) {

        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    void updateHeuristic(int row, int col) {
        int[][] manhattanDistance = {
                { 0, 1, 2 },
                { 1, 2, 1 },
                { 2, 1, 0 }
        };

        int scoreO = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'O') {
                    scoreO += manhattanDistance[i][j];
                }
            }
        }
        int aiScore = evaluateEuclidean();
        aiScore -= scoreO;
    }

    int evaluateEuclidean() {
        int scoreX = 0;
        int scoreO = 0;
        int[][] euclideanDistance = {
                { 0, 1, 2 },
                { 1, 2, 1 },
                { 2, 1, 0 }
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'X') {
                    scoreX += euclideanDistance[i][j];
                } else if (board[i][j] == 'O') {
                    scoreO += euclideanDistance[i][j];
                }
            }
        }

        return scoreX - scoreO;
    }


    int minimax(int depth, boolean isMaximizing) {
        if (checkWin('X')) {
            return 10 - depth;
        } else if (checkWin('O')) {
            return depth - 10;
        } else if (isBoardFull()) {
            return 0;
        }

        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'X';
                        int score = minimax(depth + 1, false);
                        board[i][j] = '-';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'O';
                        int score = minimax(depth + 1, true);
                        board[i][j] = '-';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        if (bestScore == Integer.MIN_VALUE || bestScore == Integer.MAX_VALUE) {
            return 0;
        }

        return bestScore;
    }

    void makeMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = 'X';
                    int score = minimax(0, false);
                    board[i][j] = '-';

                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }

        board[bestRow][bestCol] = 'X';
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tic Tac Toe - You are 'O', and I am 'X'");
        System.out.println("Here's the initial empty board:");
        printBoard();

        while (true) {
            if (checkWin('X')) {
                System.out.println("AI won. Better luck next time!");
                break;
            } else if (checkWin('O')) {
                System.out.println("Congratulations, " + getPlayerName() + "! You won!");
                break;
            } else if (isBoardFull()) {
                System.out.println("It's a draw! Good game!");
                break;
            }

            System.out.println("Your turn (row [0-2] and column [0-2]):");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != '-') {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            board[row][col] = 'O';
            printBoard();
            updateHeuristic(row, col);

            if (!checkWin('O') && !isBoardFull()) {
                makeMove();
                System.out.println("AI's move:");
                printBoard();
                int aiScore = evaluateEuclidean();
                System.out.println("AI's Heuristic Score: " + aiScore);
            }
        }
    }
    void printBoard() {
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String playerName = scanner.nextLine();

        tictactoe game = new tictactoe (playerName);
        game.displayPlayerName();
        game.play();
    }
}

