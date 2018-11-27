import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    // Either pass a move into this method, have gameplay loop in main
    // or make this a gameplay loop method
    public static void playGame(FourInALineBoard board) {
        Scanner keyboard = new Scanner(System.in);

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

        while (!board.hasFourInARow()) {
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

                System.out.println(board.toString(isComputer));
                if (isComputer) {
                    isComputer = false;
                    System.out.print("Choose Opponent's next move: ");
                } else {
                    isComputer = true;
                    System.out.print("Choose Player's next move: ");
                }

            }

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

        HashMap<Integer, String> playerPositions = new HashMap<>();
        FourInALineBoard board = new FourInALineBoard(playerPositions);

        playGame(board);

//        System.out.println("Index value: " + convertMovetoIndex("h9"));
    }
}
