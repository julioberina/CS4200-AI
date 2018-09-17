import java.util.ArrayList;
import java.util.HashMap;

public class EightPuzzle {

    // Return the value of invariant (0 for even, 1 for odd. Add value to an array, check the array for uniformity)
    private int getParity(int[] puzzle) {
        // Checking for an already solved puzzle,
        for (int i = 0; i < puzzle.length; i++) {
            if (i != puzzle[i]) {
                break;
            } else {
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
//        System.out.println("Number of inversions: " + numberOfInversions);
//        int parity = numberOfInversions % 2;
//        System.out.println("Parity: " + parity);

        return numberOfInversions % 2;
    }

    private int[][] generatePuzzles(int numberOfPuzzles) {
        int[][] generatedPuzzles = new int[numberOfPuzzles][9];
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
        int[] solvedPuzzle = {0, 1, 2, 3, 4, 5, 7, 8};

        System.out.println(test.getParity(testPuzzle));
        System.out.println(test.getParity(testPuzzle1));
        System.out.println(test.getParity(solvedPuzzle));
    }

}