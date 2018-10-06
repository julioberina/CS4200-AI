import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NQueenBoard {
    private int[] board;
    private int numberOfAttackers;
//    private int size;
//    private int numberOfQueens;

    public NQueenBoard(int[] board) {
        this.board = board;
//        this.size = board.length;
//        this.numberOfQueens = board.length;
    }

    public void moveQueens() {

    }

    // column = index, row = value
    public HashMap<String, ArrayList<String>> numberOfAttackingQueens() {
        numberOfAttackers = 0;
        HashMap<String, ArrayList<String>> attackingQueenPairs = new HashMap<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = i + 1; j < board.length; j++) {
                int elem1 = board[i];
                int elem2 = board[j];

                // Checking if on the same row
                if (elem1 == elem2) {
                    numberOfAttackers++;
                    //addPair(attackingQueenPairs, i, j, elem1, elem2);
                    addPair(attackingQueenPairs, Arrays.toString(new int[] {i, elem1}), Arrays.toString(new int[] {j, elem2}));

                // Finding diagonal rows
                } else {
                    int deltaCol = calculateDifference(i, j);
                    int deltaRow = calculateDifference(elem1, elem2);
                    if (deltaCol == deltaRow) {
                        numberOfAttackers++;
                        //addPair(attackingQueenPairs, i, j, elem1, elem2);
                        addPair(attackingQueenPairs, Arrays.toString(new int[] {i, elem1}), Arrays.toString(new int[] {j, elem2}));
                    }

                }


            }
        }
        return attackingQueenPairs;
    }

    private static void addPair(HashMap<String, ArrayList<String>> attackingQueenPairs, int queenOneCol, int queenTwoCol, int queenOneRow, int queenTwoRow) {
        ArrayList<String> queen;
        if (attackingQueenPairs.containsKey(Arrays.toString(new Integer[]{queenOneCol, queenOneRow}))) {
            queen = attackingQueenPairs.get(Arrays.toString(new Integer[]{queenOneCol, queenOneRow}));
            queen.add(Arrays.toString(new Integer[] {queenTwoCol, queenTwoRow}));
            attackingQueenPairs.put(Arrays.toString(new Integer[]{queenOneCol, queenOneRow}), queen);
        } else {
            queen = new ArrayList<>();
            queen.add(Arrays.toString(new Integer[] {queenTwoCol, queenTwoRow}));
            attackingQueenPairs.put(Arrays.toString(new Integer[]{queenOneCol, queenOneRow}), queen);
        }
    }

    private static void addPair(HashMap<String, ArrayList<String>> attackingQueenPairs, String queen1, String queen2) {
        ArrayList<String> queens;
        if (attackingQueenPairs.containsKey(queen1)) {
            queens = attackingQueenPairs.get(queen1);
            queens.add(queen2);
            attackingQueenPairs.put(queen1, queens);
        } else {
            queens = new ArrayList<>();
            queens.add(queen2);
            attackingQueenPairs.put(queen1, queens);
        }
    }

    public int calculateDifference(int a, int b) {
        return  Math.abs(a - b);
    }


    public boolean isSolved() {
        return numberOfAttackers == 0;
    }

    @Override
    public String toString() {

        return "test";
    }

}
