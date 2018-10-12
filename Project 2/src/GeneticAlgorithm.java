import java.util.*;


public class GeneticAlgorithm {
    private int cost;
    private long runtime;
    private int sizeOfPuzzle;
    private NQueenBoard solutionBoard;

    // keep individuals sorted by fitness in a pq
    public GeneticAlgorithm(int sizeOfPuzzle) {
        this.sizeOfPuzzle = sizeOfPuzzle;
        int populationSize = 200;

        PriorityQueue<NQueenBoard> population = new PriorityQueue<>(generatePopulation(populationSize));
        ArrayList<NQueenBoard> newPopulation;
        ArrayList<NQueenBoard> topXPercent;

        double percentToGrab = .30;
        int elites = Math.toIntExact(Math.round(population.size() * percentToGrab));

        long startTimeOut = System.currentTimeMillis();
        long timeOut = startTimeOut + 60 * 1000;

//        double probabilityOfMutation = .20;
        Random rand = new Random();

        NQueenBoard current = population.peek();

        // better algorithm.....
        // get top 30% of the population, select parents randomly and reproduce
        //      need an arraylist for selecting parents randomly
        //      pq with top %30
        while (System.currentTimeMillis() < timeOut && !current.isSolved()) {
            newPopulation = new ArrayList<>();
            topXPercent = new ArrayList<>();
            System.out.println("size of Population: " + population.size());

            for (int i = 0; i < elites; i++) {
                topXPercent.add(population.poll());
            }


//            if (population.size() % 2 != 0) {
//                population.poll();
//            }

            for (int i = 0; i < populationSize; i++) {

                // might grab the same individual....
                NQueenBoard mom = topXPercent.get(rand.nextInt(topXPercent.size()));
                NQueenBoard dad = topXPercent.get(rand.nextInt(topXPercent.size()));
                NQueenBoard child = generateChild(mom, dad);

                if (child.isSolved()) {
                    current = child;
                    break;
                }

                newPopulation.add(child);
            }

            population = new PriorityQueue<>(newPopulation);
            System.out.println("size of Population after new pop: " + population.size() + "\n");
        }

        solutionBoard = population.poll();
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
