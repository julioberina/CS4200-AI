import java.util.*;


public class GeneticAlgorithm {
    private int cost;
    private long runtime;
    private NQueenBoard currentBoard;
    private NQueenBoard solutionBoard;

    // keep individuals sorted by fitness in a pq
    public GeneticAlgorithm(NQueenBoard board) {
        this.currentBoard = board;
//        ArrayList<NQueenBoard> population = generatePopulation(board.getSize());
        ArrayList<NQueenBoard> population = generatePopulation(100000);
        PriorityQueue<NQueenBoard> fitnessPQ = new PriorityQueue<>(population);
        double percentToGrab = .20;
        ArrayList<NQueenBoard> newPopulation = new ArrayList<>();
        PriorityQueue<NQueenBoard> newFitnessPQ = new PriorityQueue<>();

        long startTimeOut = System.currentTimeMillis();
        long timeOut = startTimeOut + 60 * 1000;

        while (System.currentTimeMillis() < timeOut) {
            System.out.println("Size of population: " + population.size());
            System.out.println("FitnessPQ size: " + Math.round(fitnessPQ.size()));

            int topXPercent = Math.toIntExact(Math.round(fitnessPQ.size() * percentToGrab));
            System.out.println("value of top x: " + topXPercent);
            for (int i = 0; i < topXPercent; i++) {
                newFitnessPQ.add(fitnessPQ.poll());
//                newPopulation.add(generateChild(fitnessPQ.remove(), fitnessPQ.remove()));
            }

            System.out.println("newFitnessPQ size: " + newFitnessPQ.size());

            if (newFitnessPQ.size() % 2 != 0) {
                newFitnessPQ.poll();
            }

            while (!newFitnessPQ.isEmpty()) {
                newPopulation.add(generateChild(newFitnessPQ.poll(), newFitnessPQ.poll()));
            }


            System.out.println("Size of new population: " + newPopulation.size() + "\n");

            population = new ArrayList<>(newPopulation);
            fitnessPQ = new PriorityQueue<>(population);

            if (fitnessPQ.peek() != null && fitnessPQ.peek().isSolved()) {
                solutionBoard = fitnessPQ.remove();
                fitnessPQ.clear();
                break;
            }


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

//        NQueenBoard child;
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
        double mutationRate = .01;

        for (int i = 0; i < child.getSize(); i++) {
            if (rand.nextDouble() <= mutationRate) {
                child = child.moveQueenRandomly(i);
            }
        }

        return child;
    }
    public NQueenBoard mutateOne(NQueenBoard child) {
        Random rand = new Random();

        child.moveQueenRandomly(rand.nextInt(child.getSize()));

        return child;
    }

    public int getFitness() {
        return currentBoard.totalNumberOfAttackingQueens();
    }

    public NQueenBoard getSolutionBoard() {
        return solutionBoard;
    }

    public ArrayList<NQueenBoard> generatePopulation(int size) {
        ArrayList<NQueenBoard> population = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            population.add(new NQueenBoard(Main.generateBoard(currentBoard.getSize())));
        }

        return population;
    }
}
