import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NQueenBoard {
    private int[] board;
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
    public HashMap<String, ArrayList<Integer[]>> numberOfAttackingQueens() {
        int numberOfAttackers = 0;
        HashMap<String, ArrayList<Integer[]>> attackingQueenPairs = new HashMap<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = i + 1; j < board.length; j++) {
                int elem1 = board[i];
                int elem2 = board[j];

                // Checking if on the same row
                if (elem1 == elem2) {
                    numberOfAttackers++;
                    addPair(attackingQueenPairs, i, j, elem1, elem2);

                // Finding diagonal rows
                } else {
                    int deltaCol = calculateDifference(i, j);
                    int deltaRow = calculateDifference(elem1, elem2);
                    if (deltaCol == deltaRow) {
                        numberOfAttackers++;
                        addPair(attackingQueenPairs, i, j, elem1, elem2);
                    }

                }


            }
        }
        System.out.println("Number of attackers: " + numberOfAttackers);
        return attackingQueenPairs;
    }

    private static void addPair(HashMap<String, ArrayList<Integer[]>> attackingQueenPairs, int queenOneCol, int queenTwoCol, int queenOneRow, int queenTwoRow) {
        ArrayList<Integer[]> queen;
        if (attackingQueenPairs.containsKey(Arrays.toString(new Integer[]{queenOneCol, queenOneRow}))) {
            queen = attackingQueenPairs.get(Arrays.toString(new Integer[]{queenOneCol, queenOneRow}));
            queen.add(new Integer[] {queenTwoCol, queenTwoRow});
            attackingQueenPairs.put(Arrays.toString(new Integer[]{queenOneCol, queenOneRow}), queen);
        } else {
            queen = new ArrayList<>();
            queen.add(new Integer[] {queenTwoCol, queenTwoRow});
            attackingQueenPairs.put(Arrays.toString(new Integer[]{queenOneCol, queenOneRow}), queen);
        }
    }

    public int calculateDifference(int a, int b) {
        return  Math.abs(a - b);
    }


    public boolean isSolved() {
        return false;
    }

    @Override
    public String toString() {

        return "test";
    }

}
