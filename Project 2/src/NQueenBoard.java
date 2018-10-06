import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class NQueenBoard {
    private int[] board;
    private int numberOfAttackers;
    private HashMap<String, ArrayList<String>> attackingQueenPairs;
//    private int size;
//    private int numberOfQueens;

    public NQueenBoard(int[] board) {
        this.board = board;
//        this.size = board.length;
//        this.numberOfQueens = board.length;
    }

    public void moveQueenRandomly(int index) {
        Random rand = new Random();
        board[index] = rand.nextInt(board.length);
    }

    public void moveQueenUpDown(int index, boolean up) {
        Random rand = new Random();
        if (up && board[index] != board.length - 1) {
            //from the value of the row up (
            board[index] = rand.nextInt(board.length - (board[index] + 1)) + (board[index] + 1);
        }
        else if (board[index] != 0) {
            board[index] = rand.nextInt(board[index]);
        }
    }



    // column = index, row = value
    //Potential issue is attackingQueenPairs init
    public void totalNumberOfAttackingQueens() {
        numberOfAttackers = 0;
        attackingQueenPairs = new HashMap<>();
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
    }

    // Potential optimization...only calculate queen of one column, not all. could be used to update attackingQueenPairs array
    public void numberOfAttackingQueens(int index) {
        attackingQueenPairs.remove(Arrays.toString(new int[] {index, board[index]}));

       numberOfAttackers = 0;
       int elem1 = board[index];
       for (int i = 0; i < board.length; i++) {
           int elem2 = board[i];

           // Checking if on the same row
           if (elem1 == elem2 && index != i) {
               numberOfAttackers++;
               addPair(attackingQueenPairs, Arrays.toString(new int[] {index, elem1}), Arrays.toString(new int[] {i, elem2}));

           // Finding diagonal rows
           } else {
               if (index != i && (calculateDifference(index, i) == calculateDifference(elem1, elem2) || calculateSum(index, i) == calculateSum(elem1, elem2))) {
                   numberOfAttackers++;
                   addPair(attackingQueenPairs, Arrays.toString(new int[] {index, elem1}), Arrays.toString(new int[] {i, elem2}));
               }
           }

       }
       // TODO: test isSolved
       if (isSolved()) {
           attackingQueenPairs.remove(Arrays.toString(new int[] {index, elem1}));
       }
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

    public int calculateSum(int a, int b) {
        return a + b;
    }
    public boolean isSolved() {
        return numberOfAttackers == 0;
    }

    public int getNumberOfAttackers() {
        return numberOfAttackers;
    }

    public HashMap<String, ArrayList<String>> getAttackingQueenPairs() {
        return attackingQueenPairs;
    }

    @Override
    // column = index, row = value
    public String toString() {
        StringBuilder printBoard = new StringBuilder();
        for (int i = board.length - 1; i >= 0; i--){
            if (i < 10) {
                printBoard.append(" ").append(i);
            } else {
                printBoard.append(i);
            }
            for (int j = 0; j < board.length; j++) {
                if (i == board[j]) {
                    printBoard.append("  Q");
                } else {
                    printBoard.append("  .");
                }
            }

            printBoard.append("\n");
            if (i == 0) {
                int rowNum = 0;
                while (rowNum < board.length) {
                    if (rowNum == 0) {
                        printBoard.append("   ");
                    }

                    if (rowNum > 0 && rowNum <= 10)  {
                        printBoard.append("  ").append(rowNum);
                        rowNum++;
                    } else {
                        printBoard.append(" ").append(rowNum);
                        rowNum++;
                    }
                }
            }
        }
        return printBoard.toString();
    }

}
