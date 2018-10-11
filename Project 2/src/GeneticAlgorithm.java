import java.util.PriorityQueue;
import java.util.Random;


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
    //TODO: check proirity1, may not work as expected
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
        Random rand = new Random();
        NQueenBoard child;
        int[] momBoard = mom.getBoard();
        int[] dadBoard = dad.getBoard();
        int crossover =  rand.nextInt((momBoard.length - 2)) + 2;

        int[] childBoard = new int[momBoard.length];
        for (int i = 0; i < momBoard.length; i++) {
            if (i >= crossover) {
                childBoard[i] = momBoard[i];
            } else {
                childBoard[i] = dadBoard[i];
            }
        }

//        System.out.println("Crossover value: " + crossover);
//        System.out.println("Mom: ");
//        System.out.println(mom.toString());
//        System.out.println("Dad: " );
//        System.out.println(dad.toString());

//        child = new NQueenBoard(childBoard);
//        System.out.println("Child before mute: ");
//        System.out.println(child.toString());
//        child = mutate(child);
//        System.out.println("Child after mute: ");
//        System.out.println(child.toString());
//        return child;
        return mutate(new NQueenBoard(childBoard));
    }

    // TODO: test mutating one values instead of multiple
    public NQueenBoard mutate(NQueenBoard child) {
        Random rand = new Random();
        double mutationRate = .10;

        for (int i = 0; i < child.getSize(); i++) {
            if (rand.nextDouble() <= mutationRate) {
                child = child.moveQueenRandomly(i);
            }
        }

        return child;
    }

    public int getFitness() {
        return currentBoard.totalNumberOfAttackingQueens();
    }

    public NQueenBoard getSolutionBoard() {
        return solutionBoard;
    }

//    public NQueenBoard[] generatePopulation() {
//        NQueenBoard[] population;
//
//        return population;
//    }
}
