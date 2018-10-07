import java.util.Random;

public class SimulatedAnnealing {
    private int cost;
    private long runtime;
    private NQueenBoard solutionBoard;

    private boolean isSolved;


    public SimulatedAnnealing(NQueenBoard board) {
        double temperature = 1;
        double minTemperature = .0001;
        //.7 -> .25
        //.8 -> .50
        //.9 -> .80
        double coolingRate = .9;
        int numOfIterations = 100;
        double probability;
        double deltaE;
        Random rand = new Random();
        NQueenBoard current = board;
        NQueenBoard successor;


//        for (int temperature = 100; temperature > 0; temperature--) {
//            successor = current.moveQueenRandomly(rand.nextInt(8));
//
//            if (current.totalNumberOfAttackingQueens() == 0) {
//                break;
//            }
//
//            deltaE = successor.totalNumberOfAttackingQueens() - current.totalNumberOfAttackingQueens();
//            System.out.println("DeltaE: " + deltaE);
//            probability = Math.exp(deltaE / temperature);
//
//            System.out.println("Probability: " + probability);
//
//            double randomProb = rand.nextDouble();
//            System.out.println("Random prob: " + randomProb);
//
//            System.out.println(current.toString());
//            System.out.println(successor.toString());
//
//            if (deltaE > 0) {
//                current = successor;
//            } else if (randomProb <= probability) {
//                current = successor;
//            }
//        }
        while (temperature > minTemperature) {
            for (int i = 0; i < numOfIterations; i++) {
                successor = current.moveQueenRandomly(rand.nextInt(current.getSize()));
                deltaE = current.totalNumberOfAttackingQueens() - successor.totalNumberOfAttackingQueens();

//                System.out.println("DeltaE: " + deltaE);
                probability = Math.exp(deltaE / temperature);

//                System.out.println("Probability: " + probability);
                double randomProb = rand.nextDouble();

//                System.out.println("Random prob: " + randomProb);

//                System.out.println(current.toString());
//                System.out.println(successor.toString());

                if (probability > randomProb) {
                    current = successor;
                }
            }
            temperature *= coolingRate;
        }

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
