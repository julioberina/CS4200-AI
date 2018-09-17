import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class EightPuzzle {


    private boolean isSolvable(int[] puzzle) {
        // Checking for an already solved puzzle
        for (int i = 0; i < puzzle.length; i++) {
            if (i != puzzle[i]) {
                break;
            }
            if (i == 8) {
                return false;
            }
        }

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

        System.out.println(test.isSolvable(testPuzzle));
        System.out.println(test.isSolvable(testPuzzle1));
        System.out.println(test.isSolvable(testPuzzled20));
        System.out.println(test.isSolvable(solvedPuzzle));

        // Testing puzzle generator
        int[][] testPuzzles = test.generatePuzzles(100);

        // Testing printTable
        HashMap<Integer, ArrayList<Integer>> testMap = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> testList= new ArrayList<Integer>(Arrays.asList(10, 20));
        testMap.put(2, testList);
        testMap.put(4, testList);
        testMap.put(6, testList);
        testMap.put(5, testList);

        test.printTable(testMap);
    }

}