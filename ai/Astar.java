import java.util.*;

class puzzlleNode {
    int[][] puzzleState = new int[3][3];
    puzzlleNode parent;
    int zeroRow;
    int zeroCol;
    int depth;
    int heuristic;

    puzzlleNode(int[][] puzzleState, puzzlleNode parent, int[] zeroPos,int depth,int heuristic) {
        this.puzzleState = puzzleState;
        this.parent = parent;
        this.zeroRow = zeroPos[0];
        this.zeroCol = zeroPos[1];
        this.depth=depth;
        this.heuristic=heuristic;
    }
}

public class Astar {

    public static int[][] initial = {
            {1, 2, 3},
            {4, 5,0},
            {6,7, 8}
        };
    public static int[][] goal = {
        {1, 2, 3},
        {4, 5, 8},
        {6, 0, 7}
      };

    public static int[] zeroPos(int[][] puzzleState) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (puzzleState[i][j] == 0)
                    return new int[] { i, j };
            }
        }
        return new int[] { -1, -1 };
    }
    
    public static void displayPuzzle(int[][]puzzleState){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(puzzleState[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static boolean isGoal(int[][] puzzleState) {
        return Arrays.deepEquals(puzzleState, goal);
    }

    public static void solvePuzzle() {
        PriorityQueue<puzzlleNode> queue=new PriorityQueue<>(Comparator.comparingInt(node->node.heuristic+node.depth));
        Set<String> visited = new HashSet<>();
        puzzlleNode root = new puzzlleNode(initial, null, zeroPos(initial),0,calculateHeuristic(initial));
        queue.add(root);
        visited.add(Arrays.deepToString(root.puzzleState));
        while (!queue.isEmpty()) {
            puzzlleNode currNode = queue.poll();

            displayPuzzle(currNode.puzzleState);
            System.out.println();
            if (isGoal(currNode.puzzleState)) {
                System.out.println("Puzzle Solved");
                printSolution(currNode);
                break;
            }
            ArrayList<puzzlleNode> neighbourList = generateNeighbours(currNode);
            for (puzzlleNode neighbour : neighbourList) { 
                queue.add(neighbour); 
            }
        }
    }

    public static int calculateHeuristic(int[][] puzzleState){
        int heuristic=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(puzzleState[i][j]!=goal[i][j])
                    heuristic++;
            }
        }
        return heuristic;
    }
    public static void printSolution(puzzlleNode currentNode) {
        ArrayList<String> moves = new ArrayList<>();
        while (currentNode.parent != null) { 
            int parentZeroRow = currentNode.parent.zeroRow;
            int parentZeroCol = currentNode.parent.zeroCol;
            int zeroRow = currentNode.zeroRow;
            int zeroCol = currentNode.zeroCol;
            if (zeroRow == parentZeroRow && zeroCol == parentZeroCol + 1) {
                moves.add("Right");
            } else if (zeroRow == parentZeroRow && zeroCol == parentZeroCol - 1) {
                moves.add("Left");
            } else if (zeroRow == parentZeroRow + 1 && zeroCol == parentZeroCol) {
                moves.add("Down");
            } else {
                moves.add("Up");
            }
            currentNode = currentNode.parent;
        }
        Collections.reverse(moves);
        System.out.println("Solution of Problem :");
        for (String move : moves) {
            System.out.print(move + " ");
        }
    }

    public static ArrayList<puzzlleNode> generateNeighbours(puzzlleNode parent) {
        ArrayList<puzzlleNode> newNeighbours = new ArrayList<>();
        int moves[][] = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }; 
        int zeroRow = parent.zeroRow;
        int zeroCol = parent.zeroCol;
        for (int move[] : moves) { 
            if (move[0] + zeroRow >= 0 && move[0] + zeroRow < 3 && move[1] + zeroCol >= 0 && move[1] + zeroCol < 3) {
                int neighbour[][] = new int[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        neighbour[i][j] = parent.puzzleState[i][j];
                    }
                }
                neighbour[zeroRow][zeroCol]=neighbour[zeroRow+move[0]][zeroCol+move[1]];
                neighbour[zeroRow+move[0]][zeroCol+move[1]]=0;
                newNeighbours.add(new puzzlleNode(neighbour, parent, new int[]{zeroRow+move[0],zeroCol+move[1]},parent.depth+1,calculateHeuristic(neighbour))); 
            }
        }
        return newNeighbours;
    }

    public static void main(String args[]) {
        solvePuzzle();
    }
}