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

    public void algorithmAnalysis(boolean useGenetic, int numberOfPuzzles, int sizeOfPuzzle) {
        int solvedProblems, searchCostTotal, runtimesTotal;
        solvedProblems = searchCostTotal = runtimesTotal = 0;
        int[] board;
        GeneticAlgorithm geneticAlgorithm;
        SimulatedAnnealing simulatedAnnealing;
        for (int i = 1; i <= numberOfPuzzles; i++) {
            board = generateBoard(sizeOfPuzzle);

            if (useGenetic) {
                geneticAlgorithm = new GeneticAlgorithm(new NQueenBoard(board));
                if (geneticAlgorithm.isSolved()) {
                    solvedProblems += 1;
                }
                searchCostTotal += geneticAlgorithm.getCost();
                runtimesTotal += geneticAlgorithm.getRuntime();

            } else {
                simulatedAnnealing = new SimulatedAnnealing(new NQueenBoard(board));
                if (simulatedAnnealing.isSolved()) {
                    solvedProblems += 1;
                }
                searchCostTotal += simulatedAnnealing.getCost();
                runtimesTotal += simulatedAnnealing.getRuntime();
            }
        }

        System.out.println("Averages for board of size " + sizeOfPuzzle);
        System.out.println("Percentage of puzzles solved: " + solvedProblems / numberOfPuzzles);
        System.out.println("Average search cost: " + searchCostTotal/ numberOfPuzzles);
        System.out.println("Average runtime: " + runtimesTotal/ numberOfPuzzles);
    }

    public static void main(String[] args) {
// testing board generator
//        int[] testBoards;
//        int i = 0;
//        while (i <= 50) {
//            testBoards = generateBoard(21);
//            System.out.print("Test board " + i +   "  of size " + testBoards.length + ": ");
//            for (int num : testBoards) {
//                System.out.print(num + " ");
//            }
//            System.out.println();
//            i++;
//        }
        int[] eightBoard = {3, 2, 1, 4, 3, 2, 1, 2};
//        int i = 0;
//        while (i <= 200) {
//            testboard = generateBoard(500);
//            NQueenBoard test = new NQueenBoard(testboard);
//            test.numberOfAttackingQueens();
//            i++;
//        }
        NQueenBoard test = new NQueenBoard(eightBoard);
        test.numberOfAttackingQueens();
        System.out.println();
    }


}
