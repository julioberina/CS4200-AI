import java.util.ArrayList;
import java.util.HashMap;

public class FourInALineBoard {
    private HashMap<Integer, String> board;
    private final int BOARD_SIZE = 8;

    FourInALineBoard(HashMap<Integer, String> board) {
        this.board = board;
    }

    @Override
    public String toString() {
        StringBuilder printBoard = new StringBuilder();

        for (int i = 0; i <= 8; i++) {
            if (i == 0) {
                printBoard.append("  ");
            } else {
                printBoard.append("  ").append(i);
            }
        }
        printBoard.append("\n");

        int asciiValue = 65;
        for (int i = 1; i <= BOARD_SIZE; i++) {
            printBoard.append(" ").append((char) (asciiValue));
            asciiValue++;

            for (int j = 1; j <= BOARD_SIZE; j++) {
                int index1D = ((i * BOARD_SIZE) + j) - 8;
//                System.out.println("Index value: " + index1D);

                if (board.containsKey(index1D)) {
                    if (board.get(index1D).equals("X")) {
                        printBoard.append("  X");
                    } else if (board.get(index1D).equals("O")) {
                        printBoard.append("  O");
                    } else {
                        throw new Error("Invalid piece in board");
                    }

                } else {
                    printBoard.append("  -");
                }
            }

            if (i < 8) {
                printBoard.append("\n");
            }
        }

        return printBoard.toString();
    }

    public HashMap<Integer, String> getBoard() {
        return board;
    }

    public void addKeyValue(int key, String XorO) {
        if (!board.containsKey(key)) {
            board.put(key, XorO);
        } else {
            System.out.println("Key " + key + " already exists in board HashMap");
        }
    }

    public boolean isDraw() {
        return board.size() >= 64;
    }

    public int[][] convertToArray() {
        int[][] convertedHashmap = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                int index1D = ((i * BOARD_SIZE) + j) + 1;

                if (board.containsKey(index1D)) {
                    String value = board.get(index1D);
                    if (value.equals("X")) {convertedHashmap[i][j] = 1;}
                    else if (value.equals("O")) {convertedHashmap[i][j] = 2;}
                    else {convertedHashmap[i][j] = 0;}
                }

                else {convertedHashmap[i][j] = 0;}
            }
        }

        return convertedHashmap;
    }

    // TODO: Come up a way to directly check the hashmap instead of converting to a 2d array
    public boolean hasFourInARow(int[][] board) {
        for (int r = 0; r < BOARD_SIZE; r++) { // iterate rows, bottom to top
            for (int c = 0; c < BOARD_SIZE; c++) { // iterate columns, left to right
                int player = board[r][c];
                if (player == 0) { continue; }

                if (c + 3 < BOARD_SIZE &&
                        player == board[r][c + 1] && // look right
                        player == board[r][c + 2] &&
                        player == board[r][c + 3])
                    return true;

                if (r + 3 < BOARD_SIZE) {
                    if (player == board[r + 1][c] && // look up
                            player == board[r + 2][c] &&
                            player == board[r + 3][c])
                        return true;
                }
            }
        }

        return false;
    }
//
    // Convert the hashMap to two arrays
//    public boolean hasFourInARow() {
//        ArrayList<Integer> rowIndices = new ArrayList<>();
//        ArrayList<Integer> colIndices = new ArrayList<>();
//        for (int key: board.keySet()) {
//            rowIndices.add(key / 3);
//            colIndices.add(key % 3);
//        }
//    }
}
