import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Catch bad input
public class EightPuzzle implements Comparable<EightPuzzle>{
    private final int[] currentPuzzle;
    protected int stepCost;
    private int estimatedCost;
    private EightPuzzle previousPuzzle;

    public EightPuzzle (int[] currentPuzzle, int stepCost, int estimatedCost, EightPuzzle previousPuzzle) {
        this.currentPuzzle = currentPuzzle;
        this.stepCost = stepCost;
        this.estimatedCost = estimatedCost;
        this.previousPuzzle = previousPuzzle;
    }

    public EightPuzzle(int[] currentPuzzle) {
        this.currentPuzzle = currentPuzzle;
    }

    // Critical optimization recommended by:
    // http://www.cs.princeton.edu/courses/archive/spr18/cos226/assignments/8puzzle/index.html
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof EightPuzzle)) {
            return false;
        }

        EightPuzzle that = (EightPuzzle) o;
        return Arrays.deepEquals(new int[][]{this.currentPuzzle}, new int[][]{that.currentPuzzle});
    }

    public int[] getPuzzle() {
        return currentPuzzle;
    }

    public EightPuzzle getPreviousPuzzle() {
        return previousPuzzle;
    }

    public boolean isSolvable() {
        int numberOfInversions = 0;
        for (int i = 0; i < currentPuzzle.length; i++) {
            if (i + 1 >= 9) {
                break;
            }
            if (currentPuzzle[i] < currentPuzzle[i + 1]) {
                numberOfInversions += 1;
            }
        }

        int parity = numberOfInversions % 2;
        if (parity == 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isSolved() {
        for (int i = 0; i < currentPuzzle.length; i++) {
            if (i != currentPuzzle[i]) {
                return false;
            }
        }
        return true;
    }

    // this function is the problem..too much memory generating puzzles
    private int[] swapTiles(int location1, int location2) {
        int[] tempPuzzle = currentPuzzle.clone();
        int temp = tempPuzzle[location1];
        tempPuzzle[location1] = tempPuzzle[location2];
        tempPuzzle[location2] = temp;
        return tempPuzzle;
    }

    private static void shufflePuzzle(int[] puzzle) {
        int index, temp;
        Random random = new Random();
        for (int i = 0; i < puzzle.length; i++) {
            index = random.nextInt(puzzle.length);
            temp = puzzle[index];
            puzzle[index] = puzzle[i];
            puzzle[i] = temp;
        }
    }

    private static int[] generatePuzzle() {
       int[] generatedPuzzle = new int[9];
       for (int i = 0; i < generatedPuzzle.length; i++) {
           generatedPuzzle[i] = i;
       }
       shufflePuzzle(generatedPuzzle);
       return generatedPuzzle;
    }

    // Prints average of tests for certian depth
    private static void printTable(HashMap<Integer, ArrayList<Integer>> data) {
        String leftAlignFormat = "| %-2d | %-6d | %-5d | %-4d |%n";
        System.out.format("+---+---------+-------+------+%n");
        System.out.format("| d |   h1    |  h2   |numsT|   %n");
        System.out.format("+---+---------+-------+------+%n");

        int i = 2;
        while (i <= 24) {
            ArrayList<Integer> searchCostList;
            if (data.containsKey(i)) {
                searchCostList = data.get(i);
                int numOfTests = searchCostList.get(2);
                int h1Average = searchCostList.get(0) / numOfTests;
                int h2Average = searchCostList.get(1) / numOfTests;

                System.out.format(leftAlignFormat, i , h1Average, h2Average, numOfTests);
            }
            i += 1;
        }

        System.out.format("+---+---------+-------+------+%n");
    }

    public int hammingH1() {
        int hamming = 0;
        for (int i = 0; i < currentPuzzle.length; i++) {
            if (currentPuzzle[i] != i && currentPuzzle[i] != 0) {
                hamming += 1;
            }
        }
        return hamming;
    }

    public int manhattanH2() {
        int[][] puzzle2d = convertTo2D(currentPuzzle);

        int num = 0;
        int sumOfManhattan = 0;
        for (int y = 0; y < puzzle2d.length; y++) {
            for (int x = 0; x < puzzle2d[y].length; x++) {
                if (puzzle2d[y][x] != num && puzzle2d[y][x] != 0) {
                    int[] correctPosition = getCorrectPosition(puzzle2d[y][x]);
                    int manhattanDist = Math.abs(correctPosition[1] - x) + Math.abs(correctPosition[0] - y);
                    sumOfManhattan += manhattanDist;
                }
                num += 1;
            }
        }
        return sumOfManhattan;
    }

    private static int[][] convertTo2D (int[] puzzle) {
        int num = 0;
        int[][] puzzle2d = new int[3][3];
        for (int i = 0; i < puzzle2d.length; i++) {
            for (int j = 0; j < puzzle2d[i].length; j++) {
                puzzle2d[i][j] = puzzle[num];
                num += 1;
            }
        }
        return puzzle2d;
    }

    private static int[] getCorrectPosition(int num) {
        int iteration = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (num == iteration) {
                    int[] pos = {i, j};
//                    System.out.println(pos[0] + " " + pos[1]);
                    return pos;
                }
                iteration += 1;
            }
        }
        return null;
    }

    public Iterable<EightPuzzle> neighboringBoards() {
        ArrayList<EightPuzzle> neighbors = new ArrayList<EightPuzzle>();
        int locationOfZero = 0;
        for (int i = 0; i < currentPuzzle.length; i++) {
            if (currentPuzzle[i] == 0) {
                locationOfZero = i;
            }
        }

        EightPuzzle neighborPuzzle;
        int neighborArray[];
        // If tile is in the middle ((1,1) in 2d array)
        if (locationOfZero == 4) {
            for (int i = 1; i <= 7; i = i + 2) {
                neighborArray = swapTiles(locationOfZero, i);
                neighborPuzzle = new EightPuzzle(neighborArray);
                neighbors.add(neighborPuzzle);
            }
            return neighbors;
        }
        // top left corner
        if (locationOfZero == 0) {
            neighborArray = swapTiles(locationOfZero, 1);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);

            neighborArray = swapTiles(locationOfZero, 3);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);
            return neighbors;
        }
        // top right corner
        if (locationOfZero == 2) {
            neighborArray = swapTiles(locationOfZero, 1);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);

            neighborArray = swapTiles(locationOfZero, 5);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);
            return neighbors;
        }
        // bottom left corner
        if (locationOfZero == 6) {
            neighborArray = swapTiles(locationOfZero, 3);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);

            neighborArray = swapTiles(locationOfZero, 7);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);
            return neighbors;
        }
        // bottom right corner
        if (locationOfZero == 8) {
            neighborArray = swapTiles(locationOfZero, 5);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);

            neighborArray = swapTiles(locationOfZero, 7);
            neighborPuzzle = new EightPuzzle(neighborArray);
            neighbors.add(neighborPuzzle);
            return neighbors;
        }
        // top middle
        if (locationOfZero == 1) {
            for (int i = 0; i <= 4; i = i + 2) {
                neighborArray = swapTiles(locationOfZero, i);
                neighborPuzzle = new EightPuzzle(neighborArray);
                neighbors.add(neighborPuzzle);
            }
            return neighbors;
        }
        // middle left
        if (locationOfZero == 3) {
            for (int i = 0; i <= 6; i = i + 2) {
                if (i != 2) {
                    neighborArray = swapTiles(locationOfZero, i);
                    neighborPuzzle = new EightPuzzle(neighborArray);
                    neighbors.add(neighborPuzzle);
                }
            }
            return neighbors;
        }
        //middle right
        if (locationOfZero == 5) {
            for (int i = 2; i <= 8; i = i + 2) {
                if (i != 6) {
                    neighborArray = swapTiles(locationOfZero, i);
                    neighborPuzzle = new EightPuzzle(neighborArray);
                    neighbors.add(neighborPuzzle);
                }
            }
            return neighbors;
        }
        // bottom middle
        if (locationOfZero == 7) {
            for (int i = 4; i <= 8; i = i + 2) {
                neighborArray = swapTiles(locationOfZero, i);
                neighborPuzzle = new EightPuzzle(neighborArray);
                neighbors.add(neighborPuzzle);
            }
            return neighbors;
        }
        return null;
    }

    private static int [] fileReader(String fileName, int line) {
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String strPuzzle = null;
            for (int i = 1; i <= line; i++) {
                strPuzzle = br.readLine();
                if (strPuzzle.contains("Depth") || strPuzzle.isEmpty()) {
                    strPuzzle = null;
                }
            }

            if (strPuzzle != null) {
                String[] nums = strPuzzle.split(" ");
                int[] readPuzzle = new int[nums.length];
                for(int i = 0; i < nums.length; i++) {
                    readPuzzle[i] = Integer.parseInt(nums[i]);
                }
                return readPuzzle;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private static void updateListValues(ArrayList<Integer> array, int index, int value) {
        array.set(index, array.get(index) + value);
    }

    //index 0 = hamming values, index 1 = manhattan, index 2 = total times for this depth
    private static HashMap<Integer, ArrayList<Integer>> runTests(int numbOfTests) {
       HashMap<Integer, ArrayList<Integer>> tests = new HashMap<>();
       ArrayList<Integer> testValues;
       AStar solver;
       int[] puzzle;

       for (int i = 0; i <= numbOfTests; i++) {
           puzzle = generatePuzzle();

           solver = new AStar(puzzle, true);
           if (solver.optimalSequence() != null) {
               if (tests.containsKey(solver.getDepth())) {
                   testValues = tests.get(solver.getDepth());
                   updateListValues(testValues, 0, solver.getCost());
                   updateListValues(testValues, 2,  1 );
                   tests.put(solver.getDepth(), testValues);
               } else {
                   int count = 1;
                   testValues = new ArrayList<>();
                   testValues.add(0, solver.getCost());
                   testValues.add(1, 0);
                   testValues.add(2, count);
                   tests.put(solver.getDepth(),testValues);
               }
           }

           solver = new AStar(puzzle, false);
           if (solver.optimalSequence() != null) {
               testValues = tests.get(solver.getDepth());
               updateListValues(testValues, 1, solver.getCost());
               tests.put(solver.getDepth(), testValues);
           }


       }
       return tests;
    }
    @Override
    public int compareTo(EightPuzzle o) {
        int priority1 = stepCost + estimatedCost;
        int priority2 = o.stepCost + o.estimatedCost;

        if (priority1 < priority2) {
            return -1;
        } else if (priority1 > priority2) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void print2dArray(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }


    public static void printOutput(Iterable<EightPuzzle> optimalSequence, int depth, int searchCost) {
        if (searchCost != 0) {
            System.out.println("Optimal sequence: ");
            for (EightPuzzle puzzle: optimalSequence) {
                int[] a = puzzle.getPuzzle();
                print2dArray(convertTo2D(a));
                System.out.print("\n\n");
            }

            System.out.print("Solution depth: " + depth);
            System.out.println();
            System.out.print("Solution cost: " + searchCost);
            System.out.println();
        }
    }

    public static void main(String [] args) {
        EightPuzzle puzzle;
        AStar solver;

        System.out.println("Three sample solutions: ");
        int[] sample1 = {3, 2, 7, 1, 8, 4, 5, 6, 0};
        int[] sample2 = {0, 1, 2, 3, 5, 4, 6, 8, 7};
        int[] sample3 = {0, 1 , 3, 4, 2, 5, 7, 8, 6};

        System.out.println("Sample 1: ");
        solver = new AStar(sample1, true);
        System.out.println("Using Hamming");
        printOutput(solver.optimalSequence(), solver.getDepth(), solver.getCost());
        System.out.println();

        solver = new AStar(sample1, false);
        System.out.println("Using Manhattan");
        printOutput(solver.optimalSequence(), solver.getDepth(), solver.getCost());
        System.out.println("\n");


        System.out.println("Sample 2: ");
        solver = new AStar(sample2, true);
        System.out.println("Using Hamming");
        printOutput(solver.optimalSequence(), solver.getDepth(), solver.getCost());
        System.out.println();

        solver = new AStar(sample2, false);
        System.out.println("Using Manhattan");
        printOutput(solver.optimalSequence(), solver.getDepth(), solver.getCost());
        System.out.println("\n");


        System.out.println("Sample 3: ");
        solver = new AStar(sample3, true);
        System.out.println("Using Hamming");
        printOutput(solver.optimalSequence(), solver.getDepth(), solver.getCost());
        System.out.println();

        solver = new AStar(sample3, false);
        System.out.println("Using Manhattan");
        printOutput(solver.optimalSequence(), solver.getDepth(), solver.getCost());
        System.out.println("\n");


        int[] board;
        Scanner keyboard = new Scanner(System.in);
        int choice = 0;
        while (choice != 4) {
            System.out.println("\nSelect an option:");
            choice = keyboard.nextInt();
            switch (choice) {
                case 1:
                    do {
                        board = generatePuzzle();
                        puzzle = new EightPuzzle(board);
                    } while (!puzzle.isSolvable());

                    System.out.println("Generated Puzzle: ");
                    print2dArray(convertTo2D(board));
                    System.out.println();

                    solver = new AStar(board, true);
                    System.out.println("Using Hamming");
                    if (solver.optimalSequence() != null) {
                        printOutput(solver.optimalSequence(), solver.getDepth(), solver.getCost());
                    }

                    solver = new AStar(board, false);
                    System.out.println("Using Manhattan");
                    if (solver.optimalSequence() != null) {
                        printOutput(solver.optimalSequence(), solver.getDepth(), solver.getCost());
                    }
                    break;

                case 2:
                    System.out.println("Enter a puzzle: ");
                    keyboard.nextLine();
                    String userPuzzle = keyboard.nextLine();

                    if (userPuzzle != null) {
                        String[] nums = userPuzzle.split(" ");
                        if (nums.length < 9) {
                            System.out.println("Incorrect format");
                        } else {
                            int[] readPuzzle = new int[nums.length];
                            for (int i = 0; i < nums.length; i++) {
                                try {
                                    readPuzzle[i] = Integer.parseInt(nums[i]);
                                } catch (NumberFormatException e) {
                                    System.out.println("Expected ints, got something else");
                                    for (i = 0; i < nums.length; i++) {
                                        readPuzzle[i] = i;
                                    }
                                    break;
                                }
                            }

                            puzzle = new EightPuzzle(readPuzzle);
                            if (!puzzle.isSolvable()) {
                                System.out.println("Not a valid puzzle");
                            } else {
                                System.out.println("User puzzle: ");
                                print2dArray(convertTo2D(readPuzzle));
                                System.out.println();

                                solver = new AStar(readPuzzle, true);
                                System.out.println("Using Hamming");
                                if (solver.optimalSequence() != null) {
                                    printOutput(solver.optimalSequence(), solver.getDepth(), solver.getCost());
                                }

                                solver = new AStar(readPuzzle, false);
                                System.out.println("Using Manhattan");
                                if (solver.optimalSequence() != null) {
                                    printOutput(solver.optimalSequence(), solver.getDepth(), solver.getCost());
                                }
                            }
                        }
                    } else {
                        System.out.println("Not a valid puzzle");
                    }
                    break;

                case 3:
                    System.out.println("Generating 1000 test cases: ");
                    HashMap<Integer, ArrayList<Integer>> tests = runTests(100);
                    printTable(tests);
                    break;

                case 4:
                    System.out.println("Exiting....");
                    break;

                default:
                    System.out.println("Invalid choice....");
                    break;


            }
        }
    }
}
