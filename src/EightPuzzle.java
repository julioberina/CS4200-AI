import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class EightPuzzle {

    // Return the value of invariant (0 for even, 1 for odd. Add value to an array, check the array for uniformity)
    private int getParity(int[] puzzle) {
        // Checking for an already solved puzzle
        for (int i = 0; i < puzzle.length; i++) {
            if (i != puzzle[i]) {
                break;
            }
            if (i == 8) {
                return -1;
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
        System.out.println("Number of inversions: " + numberOfInversions);
//        int parity = numberOfInversions % 2;
//        System.out.println("Parity: " + parity);

        return numberOfInversions % 2;
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


    private void printTable() {

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

        System.out.println(test.getParity(testPuzzle));
        System.out.println(test.getParity(testPuzzle1));
        System.out.println(test.getParity(testPuzzled20));
        System.out.println(test.getParity(solvedPuzzle));

        // Testing puzzle generator
        int[][] testPuzzles = test.generatePuzzles(100);

    }

}