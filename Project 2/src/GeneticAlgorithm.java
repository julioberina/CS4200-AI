import java.util.*;


public class GeneticAlgorithm {
    private int cost;
    private long runtime;
    private int sizeOfPuzzle;
    private NQueenBoard solutionBoard;

    public GeneticAlgorithm(int sizeOfPuzzle) {
        this.sizeOfPuzzle = sizeOfPuzzle;
        int populationSize = 20;

        PriorityQueue<NQueenBoard> population = new PriorityQueue<>(generatePopulation(populationSize));
        ArrayList<NQueenBoard> newPopulation;
        ArrayList<NQueenBoard> topXPercent;
        Random rand = new Random();

        double percentToGrab = .30;
        int elites = Math.toIntExact(Math.round(population.size() * percentToGrab));

        long startTimeOut = System.currentTimeMillis();
        long timeOut = startTimeOut + 2 * 1000;

        NQueenBoard current = population.peek();

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < timeOut && !current.isSolved()) {
            newPopulation = new ArrayList<>();
            topXPercent = new ArrayList<>();

            for (int i = 0; i < elites; i++) {
                topXPercent.add(population.poll());
            }

            for (int i = 0; i < populationSize; i++) {

                NQueenBoard mom = topXPercent.get(rand.nextInt(topXPercent.size()));
                NQueenBoard dad = topXPercent.get(rand.nextInt(topXPercent.size()));
                NQueenBoard child = generateChild(mom, dad);

                if (child.isSolved()) {
                    current = child;
                    break;
                } else {
                    newPopulation.add(child);
                }

            }

            population = new PriorityQueue<>(newPopulation);
        }

        runtime = System.currentTimeMillis() - startTime;
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

    public NQueenBoard generateChild(NQueenBoard mom, NQueenBoard dad) {
        cost += 1;
        Random rand = new Random();
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

        return mutate(new NQueenBoard(childBoard));
    }

    public NQueenBoard mutate(NQueenBoard child) {
        Random rand = new Random();
        double mutationRate = .01;

        for (int i = 0; i < child.getSize(); i++) {
            if (rand.nextDouble() <= mutationRate) {
                child = child.moveQueenRandomly(i);
            }
        }

        return child;
    }

    public NQueenBoard getSolutionBoard() {
        return solutionBoard;
    }

    public ArrayList<NQueenBoard> generatePopulation(int numToGenerate) {
        ArrayList<NQueenBoard> population = new ArrayList<>();

        for (int i = 0; i < numToGenerate; i++) {
            population.add(new NQueenBoard(Main.generateBoard(sizeOfPuzzle)));
        }

        return population;
    }
}
