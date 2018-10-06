import java.util.Random;

public class SimulatedAnnealing {
    private int cost;
    private long runtime;
    private NQueenBoard solutionBoard;

    private boolean isSolved;


    public SimulatedAnnealing(NQueenBoard board) {
        double temperature = 1;
        double minTemperature = .0001;
        double coolingRate = .99;
        double probability;
        int numOfIterations = 8;
        int deltaE;
        Random rand = new Random();
        NQueenBoard current = board;
        NQueenBoard successor;

        int count = 1;

        while (temperature > minTemperature) {
            for (int i = 0; i < numOfIterations; i++) {
                successor = current.moveQueenRandomly(i);
                current.totalNumberOfAttackingQueens();
                successor.totalNumberOfAttackingQueens();

                deltaE = successor.getNumberOfAttackers() - current.getNumberOfAttackers();
                System.out.println("DeltaE: " + deltaE);
                probability = Math.exp(deltaE / temperature);
                System.out.println("Probability: " + probability);
                double randomProb = rand.nextDouble();
                System.out.println("Random prob: " + randomProb);
                if (deltaE > 0) {
                    current = successor;
                }
                else if (randomProb <= probability) {
                    current = successor;
                }
            }
            temperature *= coolingRate;
            count++;
        }

        System.out.println("Number of iterations: " + count);
        solutionBoard = current;
    }

    public int getCost() {
        return cost;
    }

    public long getRuntime() {
        return runtime;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public NQueenBoard getSolutionBoard() {
        return solutionBoard;
    }
}
