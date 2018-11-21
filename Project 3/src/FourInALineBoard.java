public class FourInALineBoard {
    private int[] board;

    FourInALineBoard(int[] board) {
        this.board = board;
    }

    @Override
    public String toString() {
        StringBuilder printBoard = new StringBuilder();

        for (int i = 0 ; i <= board.length; i++) {
            if (i == 0) {
                printBoard.append("  ");
            } else {
                printBoard.append("  ").append(i);
            }
        }

        printBoard.append("\n");

        int asciiValue = 65;
        for (int i = 0; i < board.length; i++) {
                printBoard.append(" ").append((char)(asciiValue));
                asciiValue++;

            for (int j = 0; j < board.length; j++) {
                if (i == board[j] && getPlayer().equals("X") ){
                    printBoard.append("  X");
                }

                else if (i == board[j] && getPlayer().equals("O")) {
                    printBoard.append("  O");
                }

                else {
                    printBoard.append("  -");
                }
            }

            printBoard.append("\n");
        }

        return printBoard.toString();
    }

    public String getPlayer() {
        return "Test";
    }
}
