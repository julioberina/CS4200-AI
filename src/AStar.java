import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStar {
    private EightPuzzle puzzle;

    public AStar(EightPuzzle puzzle) {
        this.puzzle = puzzle;
    }

    public Iterable<EightPuzzle> optimalSequence() {
        List<EightPuzzle> sequence = new ArrayList<EightPuzzle>();

        return sequence;
    }

    public int minMoves() {
        int min = 0;

        return min;
    }

    // TODO: Implement A* with both heurstics
    private HashMap<Integer, ArrayList<Integer>> eightPuzzleSolver() {
        // maybe....
        HashMap<Integer, ArrayList<Integer>> solutionDepAndSearchCost = new HashMap<Integer, ArrayList<Integer>>();
        return solutionDepAndSearchCost;
    }
}
