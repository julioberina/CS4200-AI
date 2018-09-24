import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

public class AStar {
    private int cost;
    private double time;
    private int depth;
    private ArrayList<EightPuzzle> optimalPath;

    // TODO: Get average runtime, make it work for depth >= 6, add previous node
    public AStar(int[] initialState, boolean useHamming) {
        PriorityQueue<EightPuzzle> openSet = new PriorityQueue<EightPuzzle>();
        HashSet<EightPuzzle> closedSet = new HashSet<>();

        EightPuzzle initialPuzzle = new EightPuzzle(initialState, 0, 0, null);
        openSet.add(initialPuzzle);
        EightPuzzle current = null;
        long startTime = System.nanoTime();
        long totalTime = 0;
        //for stopping the algorithm from running too long
        long start = System.currentTimeMillis();
        long end = start + 5 * 1000;

        if (initialPuzzle.isSolvable()) {
            while (!openSet.isEmpty() && System.currentTimeMillis() < end) {
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
                            if (!neighbor.equals(current.getPreviousPuzzle())) {
                                openSet.add(new EightPuzzle(neighbor.getPuzzle(), current.stepCost + 1, heursticVal, current));
                            }
                        }
                    }
                } else {
                    totalTime = System.nanoTime() - startTime;
                    openSet.clear();
                }
            }

        } else {
            System.out.println("\nThis puzzle is not solvable");
        }

        optimalPath = new ArrayList<>();
        while (current != null) {
            optimalPath.add(current);
            current = current.getPreviousPuzzle();
        }
        //optimalSequence(optimalPath);
        Collections.reverse(optimalPath);
        cost = closedSet.size();
        time =  totalTime * 0.000001;
        time = Math.round(time * 10000.0)/10000.0;
        depth = optimalPath.size();

        // Testing to see if algorithm went over allotted time
        start = System.currentTimeMillis();
        end = end - start;
        if (end <= 0) {
            System.out.println("A* quit after 5 seconds, any results printed will be invalid....");
            System.out.println("***If it quits after 5 seconds, it definitely has a depth > 24, and my implementation is not memory efficient");
            cost = -1;
            time = -1;
            depth = -1;
            optimalPath = null;
        }
        //System.out.println("Time long: " + (totalTime * 0.000001));
        //System.out.println("Time double: " + (time));

//        System.out.println("searchCost (nodes generated): " + closedSet.size());
//        System.out.println("Depth: " + (optimalPath.size() - 1));
    }

    public Iterable<EightPuzzle> optimalSequence() {
        return optimalPath;
    }

    public int getCost() {
        return cost;
    }

    public double getTime() {
        return time;
    }

    public int getDepth() {
        return depth - 1;
    }

}

