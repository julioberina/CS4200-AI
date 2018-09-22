import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;

public class AStar {
    private EightPuzzle initialPuzzle;
    // TODO: change back to public after private
    public int depth = 0;
    public int cost = 0;

    // If hamming is false, then do manhattan
    public AStar(EightPuzzle initialPuzzle, boolean useHamming) {
        PriorityQueue<EightPuzzle> openSet = null;
        if (useHamming) {
            openSet = new PriorityQueue<EightPuzzle>(11, Comparator.comparing(EightPuzzle::hammingH1));
        } else {
            openSet = new PriorityQueue<EightPuzzle>(11, Comparator.comparing(EightPuzzle::manhattanH2));
        }

        ArrayList<EightPuzzle> closedSet = new ArrayList<EightPuzzle>();
        openSet.add(initialPuzzle);

        EightPuzzle current;
        if (initialPuzzle.isSolvable()) {
            while (!openSet.isEmpty()) {
                current = openSet.peek();
                openSet.remove(current);
                closedSet.add(current);
                for (EightPuzzle neighbor: current.neighboringBoards()) {
                    // number of nodes generated
                    cost++;
                    if (closedSet.contains(neighbor)) {
                        continue;
                    }
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
                if (current.isSolved()) {
//Debugging
                    System.out.println("The puzzle is solved: ");
                    for (int num: current.puzzle) {
                        System.out.print(num + " ");
                    }
                    System.out.println();

                    break;
                }
                depth++;
            }
// Debugging
//            System.out.println("openSet size: " + openSet.size());
//            for (EightPuzzle puzzle: openSet) {
//                int[] a = puzzle.puzzle;
//                System.out.println("hamming for puzzle: " + puzzle.hammingH1());
//                for (int num : a) {
//                    System.out.print(num + " ");
//                }
//                System.out.println();
//            }
        } else {
            System.out.println("This puzzle is not solvable");
        }


        // this.initialPuzzle = initialPuzzle;
    }

    public Iterable<EightPuzzle> optimalSequence() {
        ArrayList<EightPuzzle> sequence = new ArrayList<EightPuzzle>();

        return sequence;
    }

    public int solutionDepth() {
        return depth++;
    }

    public int solutionCost(EightPuzzle puzzles) {
        for (EightPuzzle puzzle: puzzles.neighboringBoards()) {
            cost++;
        }
        return cost;
    }

    public HashMap<Integer, ArrayList<Integer>> eightPuzzleSolver() {

        HashMap<Integer, ArrayList<Integer>> solutionDepAndSearchCost = new HashMap<Integer, ArrayList<Integer>>();
        return solutionDepAndSearchCost;
    }
}
