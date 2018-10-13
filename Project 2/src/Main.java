import java.util.Random;

public class Main {

    public static int[] generateBoard(int size) {
        int[] generatedBoard = new int[size];
        Random rand = new Random();
        for (int i = 0; i < generatedBoard.length; i++) {
            int n = rand.nextInt(size);
            generatedBoard[i] = n;
        }
        return generatedBoard;
    }

    public static void algorithmAnalysis(boolean useGenetic, int numberOfPuzzles, int sizeOfPuzzle) {
        int solvedProblems, searchCostTotal, runtimesTotal;
        solvedProblems = searchCostTotal = runtimesTotal = 0;
        GeneticAlgorithm geneticAlgorithm;
        SimulatedAnnealing simulatedAnnealing;

        for (int i = 1; i <= numberOfPuzzles; i++) {
            if (useGenetic) {
                geneticAlgorithm = new GeneticAlgorithm(sizeOfPuzzle);
                if (geneticAlgorithm.isSolved()) {
                    solvedProblems += 1;
                }
                searchCostTotal += geneticAlgorithm.getCost();
                runtimesTotal += geneticAlgorithm.getRuntime();

            } else {
                simulatedAnnealing = new SimulatedAnnealing(new NQueenBoard(generateBoard(sizeOfPuzzle)));
                if (simulatedAnnealing.isSolved()) {
                    solvedProblems += 1;
                }
                searchCostTotal += simulatedAnnealing.getCost();
                runtimesTotal += simulatedAnnealing.getRuntime();
            }
        }

        System.out.println("Averages for board of size " + sizeOfPuzzle);
        double percentSolvedPuzzles = (solvedProblems * 1.0 / numberOfPuzzles) * 100.0;
        System.out.println("Percentage of puzzles solved: " + percentSolvedPuzzles + "%");
        System.out.println("Average search cost: " + searchCostTotal/ numberOfPuzzles);
        if (useGenetic) {
            System.out.println("Average runtime: " + (runtimesTotal/ numberOfPuzzles) + "ms");
        } else {
            System.out.println("Average runtime: " + (runtimesTotal/ numberOfPuzzles) * 0.000001 + "ms");
        }
    }

    public static void get3Solutions(int sizeOfPuzzle) {
        SimulatedAnnealing simulatedAnnealing;
        NQueenBoard board;
        GeneticAlgorithm genetic;

        System.out.println("Simulated Annealing");
        System.out.println("============================================================");
        for (int i = 1; i <= 3; i++) {
            board = new NQueenBoard(generateBoard(21));
            System.out.println("Initial board: ");
            System.out.println(board);

            System.out.println();

            simulatedAnnealing = new SimulatedAnnealing(board);
            System.out.println("Solved board: ");
            System.out.println(simulatedAnnealing.getSolutionBoard());
        }
        System.out.println("\n\n");

        System.out.println("Genetic");
        System.out.println("============================================================");
        for (int i = 1; i <= 3; i++) {
            genetic = new GeneticAlgorithm(21);
            System.out.println("Initial board (1st board in pop pq): ");
            System.out.println(genetic.getInitialBoard());

            System.out.println("Solved board: ");
            System.out.println(genetic.getSolutionBoard());

        }

    }
    public static void main(String[] args) {



//        System.out.println("Analysis of Simulated Annealing: ");
//        long startTime = System.currentTimeMillis();
//        algorithmAnalysis(false, 500, 21);
//        long totalTime = System.currentTimeMillis() - startTime;
//        System.out.println("Total time: " + totalTime);
//
//        System.out.println("\n");
//
//        System.out.println("Analysis of Genetic: ");
//        startTime = System.currentTimeMillis();
//        algorithmAnalysis(true, 500, 21);
//        totalTime = System.currentTimeMillis() - startTime;
//        System.out.println("Total time: " + totalTime);


        get3Solutions(21);
    }


}
