import java.util.*;

public class AStar {
    private int depth = 0;
    private int cost = 0;


    public int minMoves() {

        return  -1;
    }
    // TODO: Get average runtime, make it work for depth >= 6, add previous node
    public AStar(int[] initialState, boolean useHamming) {
        PriorityQueue<EightPuzzle> openSet = new PriorityQueue<EightPuzzle>();
        HashSet<EightPuzzle> closedSet = new HashSet<>();

        EightPuzzle initialPuzzle = new EightPuzzle(initialState, 0, 0, null);
        openSet.add(initialPuzzle);
        EightPuzzle current = null;
        if (initialPuzzle.isSolvable()) {
            while (!openSet.isEmpty()) {
                current = openSet.remove();
                closedSet.add(current);

                if (!current.isSolved()) {
                    for (EightPuzzle neighbor : current.neighboringBoards()) {
                    cost++;
                    // number of nodes generated
                    if (!closedSet.contains(neighbor)) {
                        int heursticVal;
                        if (useHamming) {
                            heursticVal = neighbor.hammingH1();
                        } else {
                            heursticVal = neighbor.manhattanH2();
                        }

                        openSet.add(new EightPuzzle(neighbor.getPuzzle(), cost, heursticVal, current));
                        System.out.println("Step cost: " + neighbor.stepCost);
                        }

                        for (int num : current.getPuzzle()) {
                            System.out.print(num + " ");
                        }
                        System.out.println();
                    }
//Debugging

                } else {
                    System.out.println("The puzzle is solved: ");
                    for (int num : current.getPuzzle()) {
                        System.out.print(num + " ");
                    }
                    System.out.println();
                    openSet.clear();
                }

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

        ArrayList<EightPuzzle> optimalPath = new ArrayList<>();
        while (current != null) {
            optimalPath.add(current);
            current = current.getPreviousPuzzle();
        }
        optimalSequence(optimalPath);
//Debugging -- i wanna die lol
        for (EightPuzzle puzzle: optimalPath) {
            int[] a = puzzle.getPuzzle();
            for (int num : a) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("closedSet size: " + closedSet.size());
        System.out.println("openSetSize: " + openSet.size());

        // this.initialPuzzle = initialPuzzle;
    }

    public Iterable<EightPuzzle> optimalSequence(ArrayList<EightPuzzle> sequence) {
        Collections.reverse(sequence);
        return sequence;
    }

    public int getDepth() {
        return depth;
    }

    public int getCost() {
        return cost;
    }
}

