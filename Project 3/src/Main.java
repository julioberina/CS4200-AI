import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    // TODO: fix issue with existing keys in hashmap being added to movelist
    public static void playGame(FourInALineBoard board) {
        Scanner keyboard = new Scanner(System.in);
        HashMap<Integer, MovePairs> moveList = new HashMap<>();

        System.out.print("Who goes first, C for computer, O for opponent: ");

        String input = null;
        try {
            input = keyboard.next().toUpperCase();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input, computer will go first by default");
            input = "C";
        }

        boolean isComputer = true;
        boolean isX = true;
        if (input.equals("O")) {
            isComputer = false;
            isX = false;
            System.out.print("Opponent's move is: ");
        } else {
            System.out.print("Player's move is: ");
        }

        int moveNumber = 1;
        while (!board.hasFourInARow(board.convertToArray()) && !board.isDraw()) {
            String move = null;
            move = keyboard.next();

            int index = convertMovetoIndex(move);
            if (index < 1 || index > 64) {
                System.out.println("Invalid move passed into playGame method");
            } else {
                if (isX) {
                    board.addKeyValue(index, "X");
                    isX = false;
                } else {
                    board.addKeyValue(index, "O");
                    isX = true;
                }

                if (!moveList.containsKey(moveNumber)) {
                    MovePairs mp = new MovePairs();
                    mp.setMoveOne(move);
                    moveList.put(moveNumber, mp);
                } else {
                    MovePairs mp = moveList.get(moveNumber);
                    mp.setMoveTwo(move);
                    moveList.put(moveNumber, mp);
                    moveNumber += 1;
                }

                System.out.println();
                printBoardAndMoves(board.toString(), moveListOutputter(moveList, isComputer));
                System.out.println();

                if (!isX) {
                    System.out.print("Choose Opponent's next move: ");
                } else {
                    System.out.print("Choose Player's next move: ");
                }

            }

        }

        System.out.println();
        if (!isX) {
            System.out.println("X wins!");
        } else {
            System.out.println("O wins!");
        }
    }


    public static String moveListOutputter(HashMap<Integer, MovePairs> moveList, boolean isComputer) {
        StringBuilder moveListString = new StringBuilder();

        if (isComputer) {
            moveListString.append("Player vs. Opponent");
        } else {
            moveListString.append("Opponent vs. Player");
        }
        moveListString.append("\n");

        for (int key: moveList.keySet()) {
            MovePairs mp = moveList.get(key);

            if (mp.getMoveTwo() != null) {
                moveListString.append(key).append(". ").append(mp.getMoveOne()).append("  " ).append(mp.getMoveTwo());
            } else {
                moveListString.append(key).append(". ").append(mp.getMoveOne()).append(" " );
            }

            moveListString.append("\n");
        }

        return moveListString.toString();
    }

    public static void printBoardAndMoves(String board, String moves) {
        String[] boardLines = board.split("\n");
        String[] moveLines = moves.split("\n");

        int i = 0;
        int j = 0;
        while (i < boardLines.length && j < moveLines.length) {
            System.out.println(boardLines[i] + "       " + moveLines[j]);
            i++;
            j++;
        }

        while (i < boardLines.length){
            System.out.println(boardLines[i]);
            i++;
        }

        while (j < moveLines.length){
            System.out.println("                                 " + moveLines[j]);
            j++;
        }
    }

    public static int convertMovetoIndex(String move) {
        int index = -1;
        move = move.toUpperCase();
        int asciiLetter = (int) move.charAt(0);

        if (move.length() != 2 || (asciiLetter < 65 || asciiLetter > 72)) {
            System.out.println("Invalid move");
            return -1;
        } else {
            try {
                int number = Integer.parseInt(move.substring(1, 2));
                asciiLetter = asciiLetter - 64;
                index = ((asciiLetter * 8) + number) - 8;
            } catch (NumberFormatException e) {
                System.out.println("Invalid move");
                return -1;
            }
        }

        return index;
    }

    public static void main(String[] args) {
//        HashMap<Integer, String> testMap = new HashMap<>();
//        FourInALineBoard testBoard = new FourInALineBoard(testMap);
//        System.out.println(testBoard.toString(false));
//        testMap.put(1, "X");
//        testMap.put(10, "O");
//        testMap.put(15, "X");
//        testMap.put(24, "O");
//        testMap.put(30, "X");
//        testMap.put(64, "O");
//        System.out.println(testBoard.toString());
//
//        int[][] testArray = testBoard.convertToArray();
//
//        for (int i = 0; i < testArray.length; i++) {
//            for (int j = 0; j < testArray.length; j++) {
//                System.out.print(testArray[i][j] + " ");
//            }
//            System.out.println();
//        }


        HashMap<Integer, String> playerPositions = new HashMap<>();
        FourInALineBoard board = new FourInALineBoard(playerPositions);

//        int i = 1;
//        while (!board.isDraw()) {
//            board.addKeyValue(i, "X");
//            System.out.println(board.toString());
//            System.out.println("i = " + i);
//            i++;
//        }
//
//        for(int key: board.getBoard().keySet()) {
//            System.out.println("Key " + key);
//        }

        playGame(board);

//        System.out.println("Index value: " + convertMovetoIndex("h9"));
    }
}
