import java.util.HashMap;

public class FourInALineBoard {
    private HashMap<Integer, String> board;
    private final int BOARD_SIZE = 8;

    FourInALineBoard(HashMap<Integer, String> board) {
        this.board = board;
    }

    public String toString(boolean isComputer) {
        StringBuilder printBoard = new StringBuilder();

        for (int i = 0 ; i <=  8; i++) {
            if (i == 0) {
                printBoard.append("  ");
            } else {
                printBoard.append("  ").append(i);
            }
        }

        if (isComputer) {
            printBoard.append("      Player vs Opponent");
        } else {
            printBoard.append("      Opponent vs Player");
        }

        printBoard.append("\n");

        int asciiValue = 65;
        for (int i = 1; i <= BOARD_SIZE; i++) {
                printBoard.append(" ").append((char)(asciiValue));
                asciiValue++;

            for (int j = 1; j <= BOARD_SIZE; j++) {
                int index1D = ((i * BOARD_SIZE) + j) - 8;
//                System.out.println("Index value: " + index1D);

                if (board.containsKey(index1D)){
                    if (board.get(index1D).equals("X")) { printBoard.append("  X"); }
                    else if (board.get(index1D).equals("O")) { printBoard.append("  O"); }
                    else { throw new Error("Invalid piece in board"); }

                } else {
                    printBoard.append("  -");
                }
            }

            printBoard.append("\n");
        }

        return printBoard.toString();
    }

    public HashMap<Integer, String> getBoard() {
        return board;
    }

    public void addKeyValue(int key, String XorO) {
        if (!board.containsKey(key)) { board.put(key, XorO); }
        else {System.out.println("Key " + key + " already exists in board HashMap");}
    }

    public boolean hasFourInARow() {

        return false;
    }

}
