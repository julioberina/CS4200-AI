public class Main {

    public String generateBoard(int size) {
        String generatedBoard = "";

        return generatedBoard;
    }

    public void AnalyzeAlgorithm(boolean useGenetic, int numberOfPuzzles) {
        int solvedProblems, searchCostTotal, runtimesTotal;
        solvedProblems = searchCostTotal = runtimesTotal = 0;
        String board;
        GeneticAlgorithm geneticAlgorithm;
        SimulatedAnnealing simulatedAnnealing;
        int size = 21;
        for (int i = 1; i <= numberOfPuzzles; i++) {
            board = generateBoard(size);

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

    }

    public static void main(String[] args) {
	// write your code here
    }

}
