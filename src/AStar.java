import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class AStar {
    private int depth = 0;
    private int cost = 0;

    public int minMoves() {

        return  -1;
    }
    // TODO: Get average runtime, make it work for depth >= 6, add previous node
    public AStar(EightPuzzle initialPuzzle, boolean useHamming) {
        PriorityQueue<EightPuzzle> openSet = null;
        if (useHamming) {
            openSet = new PriorityQueue<EightPuzzle>(1000, Comparator.comparing(EightPuzzle::hammingH1));
        } else {
            openSet = new PriorityQueue<EightPuzzle>(1000, Comparator.comparing(EightPuzzle::manhattanH2));
        }

        ArrayList<EightPuzzle> closedSet = new ArrayList<EightPuzzle>();
        openSet.add(initialPuzzle);

        EightPuzzle current;
        if (initialPuzzle.isSolvable()) {
            while (!openSet.isEmpty()) {
                current = openSet.peek();
                if (current.isSolved()) {
//Debugging
//                    for (int num : current.puzzle) {
//                        System.out.print(num + " ");
//                    }
//                    System.out.println();

                    System.out.println("The puzzle is solved: ");
                    break;
                }
                openSet.remove(current);
                closedSet.add(current);
                for (EightPuzzle neighbor : current.neighboringBoards()) {
                    // number of nodes generated
                    cost++;
                    if (closedSet.contains(neighbor)) {
                        continue;
                    }
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
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

    public int getDepth() {
        return depth;
    }

    public int getCost() {
        return cost;
    }
}

