import java.util.PriorityQueue;


public class GeneticAlgorithm implements Comparable<NQueenBoard>{
    private int cost;
    private long runtime;
    private NQueenBoard currentBoard;
    private NQueenBoard solutionBoard;

    // keep individuals sorted by fitness in a pq
    public GeneticAlgorithm(NQueenBoard board) {
        this.currentBoard = board;


    }

    @Override
    // Calculating and sorting by (fitness = attacking Queen pairs)
    public int compareTo(NQueenBoard o) {
        int priority1 = currentBoard.totalNumberOfAttackingQueens();
        int priority2 = o.totalNumberOfAttackingQueens();

        if (priority1 < priority2) {
            return -1;
        } else if (priority1 > priority2) {
            return 1;
        } else {
            return 0;
        }

    }

    public int getCost() {
        return cost;
    }

    public long getRuntime() {
        return runtime;
    }

    public boolean isSolved() {
        return solutionBoard.totalNumberOfAttackingQueens() == 0;
    }

    public NQueenBoard generateChild(NQueenBoard mom, NQueenBoard dad) {
        NQueenBoard child;

        return child;
    }

    public void mutate(NQueenBoard child) {

    }

    public int getFitness() {
        return currentBoard.totalNumberOfAttackingQueens();
    }

    public NQueenBoard getSolutionBoard() {
        return solutionBoard;
    }

    public NQueenBoard[] generatePopulation() {
        NQueenBoard[] population;

        return population;
    }
}
