import java.util.*;

public class AStar {
    private int cost;

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
                    if (!closedSet.contains(neighbor)) {
                        int heursticVal;
                        if (useHamming) {
                            heursticVal = neighbor.hammingH1();
                        } else {
                            heursticVal = neighbor.manhattanH2();
                        }
                        if (!neighbor.equals(current.getPreviousPuzzle()))
                        openSet.add(new EightPuzzle(neighbor.getPuzzle(), current.stepCost + 1, heursticVal, current));
                        }
//Debugging
//                        for (int num : current.getPuzzle()) {
//                            System.out.print(num + " ");
//                        }
//                        System.out.println();
                    }

                } else {
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
//        for (EightPuzzle puzzle: optimalPath) {
//            int[] a = puzzle.getPuzzle();
//            for (int num : a)
//                System.out.print(num + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
        System.out.println("searchCost (nodes generated): " + closedSet.size());
        cost = closedSet.size();
        System.out.println("Depth: " + (optimalPath.size() - 1));



        // this.initialPuzzle = initialPuzzle;
    }

    public Iterable<EightPuzzle> optimalSequence(ArrayList<EightPuzzle> sequence) {
        Collections.reverse(sequence);
        return sequence;
    }

    public int getCost() {
        return cost;
    }

}

