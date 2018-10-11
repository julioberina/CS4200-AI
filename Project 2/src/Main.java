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
                geneticAlgorithm = new GeneticAlgorithm(new NQueenBoard(generateBoard(sizeOfPuzzle)));
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
//        System.out.println("Number of solved puzzles " + solvedProblems);
        double percentSolvedPuzzles = (solvedProblems * 1.0 / numberOfPuzzles) * 100.0;
        System.out.println("Percentage of puzzles solved: " + percentSolvedPuzzles + "%");
        System.out.println("Average search cost: " + searchCostTotal/ numberOfPuzzles);
        System.out.println("Average runtime: " + (runtimesTotal/ numberOfPuzzles) * 0.000001 + "ms");
    }

    public static void main(String[] args) {
        int[] eightBoard = {3, 2, 1, 4, 3, 2, 1, 2};
        NQueenBoard test = new NQueenBoard(eightBoard);


//        long startTime = System.currentTimeMillis();
//        algorithmAnalysis(false, 500, 21);
//        long totalTime = System.currentTimeMillis() - startTime;
//
//        System.out.println("Total time: " + totalTime);

        NQueenBoard mom = new NQueenBoard(generateBoard(8));
        NQueenBoard dad = new NQueenBoard(generateBoard(8));

        GeneticAlgorithm testGA = new GeneticAlgorithm(mom);
        testGA.generateChild(mom, dad);
    }


}
