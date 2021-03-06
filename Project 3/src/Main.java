import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void playGame(FourInALineBoard board) {
        Scanner keyboard = new Scanner(System.in);
        HashMap<Integer, MovePairs> moveList = new HashMap<>();
        MiniMax mm = new MiniMax();
        boolean initMoveMade = false;
        Random rand = new Random();

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
        while (board.hasFourInARow(board.convertToArray()) == 0 && !board.isDraw()) {
            int index = 0;
            String move = null;
            boolean validInput = false;
        
            if (isX) {
                if (initMoveMade == false) {
                    move = convertMoveToStr(rand.nextInt(64) + 1);
                    index = convertMovetoIndex(move);
                    initMoveMade = true;
                }
                else {
                    index = mm.getNextMove(board);
                    move = convertMoveToStr(index);
                }
            }
            else {

                do {
                    move = keyboard.next();
                    index = convertMovetoIndex(move);

                    if (index != -1 && !board.getBoard().containsKey(index)) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid input, try again");
                    }

                } while(!validInput);

            }

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
                    System.out.print("Player is making move...");
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

    
    public static String convertMoveToStr(int move) {
        int theMove = move - 1;
        String answer = "";
        answer += (char)(65 + (theMove/8));
        answer += Integer.toString((theMove%8)+1);
        return answer;
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
                if (number < 1 || number > 8) {
                    return -1;
                } else {
                    asciiLetter = asciiLetter - 64;
                    index = ((asciiLetter * 8) + number) - 8;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid move");
                return -1;
            }
        }

        return index;
    }

    public static void main(String[] args) {
        HashMap<Integer, String> playerPositions = new HashMap<>();
        FourInALineBoard board = new FourInALineBoard(playerPositions);


        playGame(board);

    }
}
