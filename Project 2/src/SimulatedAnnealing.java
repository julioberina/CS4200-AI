import java.util.Random;

public class SimulatedAnnealing {
    private int cost;
    private long runtime;
    private NQueenBoard solutionBoard;

    // values for cooling rate -> percent correct
    //.7 -> .25
    //.8 -> .50
    //.9 -> .80
    //.925 -> .9
    //.93 -> .93
    //,94 -> .95 (slowish)
    //.95 -> .98 (Slow)
    //.99 -> 1 (Very slow)
    public SimulatedAnnealing(NQueenBoard board) {
        double temperature = 1;
        double minTemperature = .0001;
        double coolingRate = .94;
        double probability;
        double deltaE;
        int numOfIterations = 100;
        Random rand = new Random();
        NQueenBoard current = board;
        NQueenBoard successor;

        long startTime = System.nanoTime();
        while (temperature > minTemperature) {
            for (int i = 0; i < numOfIterations; i++) {
                successor = current.moveQueenRandomly(rand.nextInt(current.getSize()));

                deltaE = current.totalNumberOfAttackingQueens() - successor.totalNumberOfAttackingQueens();
                cost += Math.abs(deltaE);
                probability = Math.exp(deltaE / temperature);
                double randomProb = rand.nextDouble();

                if (probability > randomProb) {
                    current = successor;
                }
            }
            temperature *= coolingRate;
        }
        runtime = System.nanoTime() - startTime;
        solutionBoard = current;
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

    public NQueenBoard getSolutionBoard() {
        return solutionBoard;
    }
}
