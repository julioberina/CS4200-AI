import java.util.HashMap;

public class Main {

    public void playGame(int move, boolean isPlayer) {

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
        HashMap<Integer, String> testMap = new HashMap<>();
        testMap.put(1, "X");
        testMap.put(10, "O");
//        testMap.put(15, "X");
//        testMap.put(24, "O");
//        testMap.put(30, "X");
//        testMap.put(64, "O");

        FourInALineBoard testBoard = new FourInALineBoard(testMap);
        System.out.println(testBoard.toString());

        System.out.println("Index value: " + convertMovetoIndex("h9"));
    }
}
