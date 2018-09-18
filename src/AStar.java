import java.util.ArrayList;
import java.util.HashMap;

public class AStar {

    public int hammingH1(int[] puzzle) {
        int misplacedTiles = 0;
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] != i && puzzle[i] != 0) {
                misplacedTiles += 1;
            }
        }
        return misplacedTiles;
    }

    public static int[] getCorrectPosition (int num) {
        int iteration = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (num == iteration) {
                    int[] pos = {i, j};
                    System.out.println(pos[0] + " " + pos[1]);
                    return pos;
                }
                iteration += 1;
            }
        }
        return null;
    }

    public int manhattanH2(int[] puzzle) {
        // Converting puzzle to 2d array to calculate manhattan dist
        int num = 0;
        int[][] puzzle2d = new int[3][3];
        for (int i = 0; i < puzzle2d.length; i++) {
            for (int j = 0; j < puzzle2d[i].length; j++) {
                puzzle2d[i][j] = puzzle[num];
                num += 1;
            }
        }

        num = 0;
        int sumOfManhattan = 0;
        for (int y = 0; y < puzzle2d.length; y++) {
            for (int x = 0; x < puzzle2d[y].length; x++) {
                if (puzzle2d[y][x] != num && puzzle2d[y][x] != 0) {
                    int[] correctPosition = getCorrectPosition(puzzle2d[y][x]);
                    int manhattanDist = Math.abs(correctPosition[1] - x) + Math.abs(correctPosition[0] - y);
                    sumOfManhattan += manhattanDist;
                }
                num += 1;
            }
        }
        return sumOfManhattan;
    }

    // TODO: Implement A* with both heurstics
    // will utilize A*
    private HashMap<Integer, ArrayList<Integer>> eightPuzzleSolver(int[] puzzle, boolean showOptSeq) {
        // maybe....
        HashMap<Integer, ArrayList<Integer>> solutionDepAndSearchCost = new HashMap<Integer, ArrayList<Integer>>();
        return solutionDepAndSearchCost;
    }
    public void solver(int[] puzzle) {

    }
}
