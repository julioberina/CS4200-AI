import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class EightPuzzle {

    private boolean isSolvable(int[] puzzle) {
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

    private boolean isSolved(int[] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            if (i != puzzle[i]) {
                return false;
            }
        }
        return true;
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

    private int[] generatePuzzle() {
       int[] generatedPuzzle = new int[9];
       for (int i = 0; i < generatedPuzzle.length; i++) {
           generatedPuzzle[i] = i;
       }
       shufflePuzzle(generatedPuzzle);
       return generatedPuzzle;
    }

    // Might not use
    private int[][] generatePuzzles(int numberOfPuzzles) {
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

    private HashMap<Integer, ArrayList<Integer>> aStar(boolean showOptSeq) {
        // maybe....
        HashMap<Integer, ArrayList<Integer>> solutionDepAndSearchCost = new HashMap<Integer, ArrayList<Integer>>();
        return solutionDepAndSearchCost;
    }

    public static void main(String [] args) {
        EightPuzzle test = new EightPuzzle();
        int[] testPuzzle = {3, 1, 2, 6, 4, 5, 0, 7, 8};
        int[] testPuzzle1 = {3, 1,  2, 4, 0, 5, 6, 7, 8};
        int[] testPuzzled20 = {0, 5, 8, 2, 7, 6, 1, 3, 4};
        int[] solvedPuzzle = {0, 1, 2, 3, 4, 5, 6, 7, 8};

        System.out.println("Checking if solvable");
        System.out.println(test.isSolvable(testPuzzle));
        System.out.println(test.isSolvable(testPuzzle1));
        System.out.println(test.isSolvable(testPuzzled20));
        System.out.println(test.isSolvable(solvedPuzzle));
        System.out.println();

        System.out.println("Checking if solved");
        System.out.println(test.isSolved(testPuzzle));
        System.out.println(test.isSolved(testPuzzle1));
        System.out.println(test.isSolved(testPuzzled20));
        System.out.println(test.isSolved(solvedPuzzle));
        System.out.println();

        // Testing puzzle generator
        int[][] testPuzzles = test.generatePuzzles(100);
        int[] testGeneratedPuzzle = test.generatePuzzle();

        for (int num : testGeneratedPuzzle) {
            System.out.print(num + " ");
        }
        System.out.println();
        System.out.println(test.isSolvable(testGeneratedPuzzle));
        System.out.println(test.isSolved(testGeneratedPuzzle));


        // Testing printTable
        System.out.println();
        HashMap<Integer, ArrayList<Integer>> testMap = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> testList= new ArrayList<Integer>(Arrays.asList(10, 20));
        ArrayList<Integer> testList2 = new ArrayList<Integer>(Arrays.asList(30, 40));
        testMap.put(2, testList);
        testMap.put(4, testList);
        testMap.put(6, testList);
        //testMap.put(4, testList2);
        testMap.put(5, testList);

        test.printTable(testMap);
    }

}
