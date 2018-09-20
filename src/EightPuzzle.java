import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.List;

// NOTE: When applying h1 and h2 they should both have the same solution depth
// Solution cost should be close to the table
// Catch bad input
// h2 should be worse than h1
// Can use graph search w/h2
public class EightPuzzle {
    private int[] puzzle;

    public EightPuzzle (int[] puzzle) {
        this.puzzle = puzzle;
    }
    public boolean isSolvable() {
        // Finding number of inversions
        int numberOfInversions = 0;
        for (int i = 0; i < puzzle.length; i++) {
            if (i + 1 >= 9) {
                break;
            }
            if (puzzle[i] < puzzle[i + 1]) {
//                System.out.println("1st:" + puzzle[i] + ", 2nd: " + puzzle[i + 1]);
                numberOfInversions += 1;
            }
        }

// Debugging
//        System.out.println("Number of inversions: " + numberOfInversions);
//        System.out.println("Parity: " + parity);

        int parity = numberOfInversions % 2;
        if (parity == 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isSolved() {
        for (int i = 0; i < puzzle.length; i++) {
            if (i != puzzle[i]) {
                return false;
            }
        }
        return true;
    }

    public int getTileAt(int location) {
        return puzzle[location - 1];
    }

    private static void shufflePuzzle(int[] puzzle) {
        int index, temp;
        Random random = new Random();
        for (int i = 0; i < puzzle.length; i++) {
            index = random.nextInt(puzzle.length);
//            System.out.print(index);
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

    // Might not use
    private static int[][] generatePuzzles(int numberOfPuzzles) {
        int[][] generatedPuzzles = new int[numberOfPuzzles][9];
        for (int i = 0; i < generatedPuzzles.length; i++) {
            for (int j = 0; j < generatedPuzzles[i].length; j++) {
                generatedPuzzles[i][j] = j;
            }
            shufflePuzzle(generatedPuzzles[i]);
// Debugging
//            System.out.println("Preshuffled");
//            for (int num : generatedPuzzles[i]) {
//                System.out.print(num + " ");
//            }
//
//            System.out.println();
//            System.out.println("Shuffled");
//            for (int num : generatedPuzzles[i]) {
//                System.out.print(num + " ");
//            }
//            System.out.println("\n\n");

        }
        return generatedPuzzles;
    }

    // Needs to be changed to have a column for average runtime, and number of cases with a specific depth
    private void printTable(HashMap<Integer, ArrayList<Integer>> data) {
        String leftAlignFormat = "| %-2d | %-6d | %-5d | %n";
        System.out.format("+---+---------+-------+%n");
        System.out.format("| d |   h1    |  h2   |%n");
        System.out.format("+---+---------+-------+%n");

        int i = 2;
        while ((i / 2) < data.size()) {
            ArrayList<Integer> searchCostList;
            if (data.containsKey(i)) {
                searchCostList = data.get(i);
                int h1 = searchCostList.get(0);
                int h2 = searchCostList.get(1);

                System.out.format(leftAlignFormat, i , h1, h2);
            } else {
                System.out.format(leftAlignFormat, -1 , -1, -1);
            }
            i += 2;
        }

        System.out.format("+---+---------+-------+%n");
    }

    public int hammingH1() {
        int hamming = 0;
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] != i && puzzle[i] != 0) {
                hamming += 1;
            }
        }
        return hamming;
    }

    public int manhattanH2() {
        // Converting puzzle to 2d array to calculate manhattan dist
        int num = 0;
        int[][] puzzle2d = new int[3][3];
        for (int i = 0; i < puzzle2d.length; i++) {
            for (int j = 0; j < puzzle2d[i].length; j++) {
                puzzle2d[i][j] = puzzle[num];
                num += 1;
            }
        }

        num = 0;
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
        List<EightPuzzle> neighbors = new ArrayList<EightPuzzle>();
        return neighbors;
    }

    // TODO: Get total number of lines in file, read in all lines skipping depth info
    private int [][] fileReader(String fileName) {
        int numOfLines = 0;
        int[][] readPuzzles = new int[numOfLines][9];

        return readPuzzles;
    }

    public static void main(String [] args) {
        int[] testPuzzle = {3, 1, 2, 6, 4, 5, 0, 7, 8};
        int[] testPuzzle1 = {3, 1,  2, 4, 0, 5, 6, 7, 8};
        int[] testPuzzled20 = {0, 5, 8, 2, 7, 6, 1, 3, 4};
        int[] testPuzzle3 = {7, 2, 4, 5, 0, 6, 8, 3, 1};
        int[] solvedPuzzle = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        EightPuzzle test = new EightPuzzle(testPuzzle3);

        System.out.println("Checking if solvable");
        System.out.println(test.isSolvable());
        System.out.println();

        System.out.println("Checking if solved");
        System.out.println(test.isSolved());
        System.out.println();

        System.out.println("Checking getTileAt");
        System.out.println(test.getTileAt(4));
        System.out.println();

        // Testing puzzle generator
        int[][] testPuzzles = generatePuzzles(100);
        int[] testGeneratedPuzzle = generatePuzzle();

        for (int num : testGeneratedPuzzle) {
            System.out.print(num + " ");
        }


        // Testing printTable
        System.out.println();
        HashMap<Integer, ArrayList<Integer>> testMap = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> testList= new ArrayList<Integer>(Arrays.asList(10, 20));
        ArrayList<Integer> testList2 = new ArrayList<Integer>(Arrays.asList(30, 40));
        testMap.put(2, testList);
        testMap.put(4, testList);
        testMap.put(6, testList);
        testMap.put(5, testList);
        test.printTable(testMap);
        System.out.println();

        System.out.println("Checking hamming");
        System.out.println(test.hammingH1());

        System.out.println("Checking manhattan");
        System.out.println(test.manhattanH2());
        System.out.println();


    }

}
