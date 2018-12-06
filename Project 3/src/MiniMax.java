import java.util.HashMap;

public class MiniMax {
    private int MIN = -Integer.MIN_VALUE;
    private int MAX = Integer.MAX_VALUE;
    private int maxDepth = 32;
    private int moveToMake = 0;
    private long startTime = 0; // For cutoff test
    private boolean timedOut = false;
    private FourInALineBoard optimalBoard;

    public int minimax(int depth, boolean isMax, FourInALineBoard b, int alpha, int beta) {
      int score = b.aiScore(b.hasFourInARow(b.convertToArray()));

      if (score == -1 || score == 1)
        return score;
      
      if (depth == maxDepth) {
        if ((System.nanoTime() - startTime) >= 24e9)    timedOut = true;
        return 0;
      }

      if (isMax) {
        int best = MIN;

        FourInALineBoard[] bclones = new FourInALineBoard[4];

        for (int i = 0; i < 4; ++i) {
            HashMap map = (HashMap)b.getBoard().clone();
            bclones[i] = new FourInALineBoard(map);
        }

        for (int i = 0; i < 4; ++i) {
          int[][] temp = bclones[i].convertToArray();
          int lastMove = b.getLastMove() - 1;
          int row = lastMove / 8;
          int col = lastMove % 8;

          if (i == 0 && row > 0 && temp[row-1][col] == 0) {
            bclones[i].addKeyValue(((row-1)*8+col+1), "X");
            int tempBest = best;
            best = Math.max(best, minimax(depth+1, false, bclones[i], alpha, beta));
            if (best >= tempBest)  optimalBoard = bclones[i];
            alpha = Math.max(alpha, best);
          }

          if (i == 1 && row < 7 && temp[row+1][col] == 0) {
            bclones[i].addKeyValue(((row+1)*8+col+1), "X");
            int tempBest = best;
            best = Math.max(best, minimax(depth+1, false, bclones[i], alpha, beta));
            if (best >= tempBest)  optimalBoard = bclones[i];
            alpha = Math.max(alpha, best);
          }

          if (i == 2 && col > 0 && temp[row][col-1] == 0) {
            bclones[i].addKeyValue((row*8+col), "X");
            int tempBest = best;
            best = Math.max(best, minimax(depth+1, false, bclones[i], alpha, beta));
            if (best >= tempBest)  optimalBoard = bclones[i];
            alpha = Math.max(alpha, best);
          }

          if (i == 3 && col < 7 && temp[row][col+1] == 0) {
            bclones[i].addKeyValue((row*8+col+2), "X");
            int tempBest = best;
            best = Math.max(best, minimax(depth+1, false, bclones[i], alpha, beta));
            if (best >= tempBest)  optimalBoard = bclones[i];
            alpha = Math.max(alpha, best);
          }

          if (beta <= alpha)    break;
        }
          
        return best;
      }
      
      else {
        int best = MAX;

        FourInALineBoard[] bclones = new FourInALineBoard[4];

        for (int i = 0; i < 4; ++i) {
            HashMap map = (HashMap)b.getBoard().clone();
            bclones[i] = new FourInALineBoard(map);
        }

        for (int i = 0; i < 4; ++i) {
          int[][] temp = bclones[i].convertToArray();
          int lastMove = b.getLastMove() - 1;
          int row = lastMove / 8;
          int col = lastMove % 8;

          if (i == 0 && row > 0 && temp[row-1][col] == 0) {
            bclones[i].addKeyValue(((row-1)*8+col+1), "O");
            int tempBest = best;
            best = Math.min(best, minimax(depth+1, true, bclones[i], alpha, beta));
            if (best <= tempBest)  optimalBoard = bclones[i];
            beta = Math.min(beta, best);
          }

          if (i == 1 && row < 7 && temp[row+1][col] == 0) {
            bclones[i].addKeyValue(((row+1)*8+col+1), "O");
            int tempBest = best;
            best = Math.min(best, minimax(depth+1, true, bclones[i], alpha, beta));
            if (best <= tempBest)  optimalBoard = bclones[i];
            beta = Math.min(beta, best);
          }

          if (i == 2 && col > 0 && temp[row][col-1] == 0) {
            bclones[i].addKeyValue((row*8+col), "O");
            int tempBest = best;
            best = Math.min(best, minimax(depth+1, true, bclones[i], alpha, beta));
            if (best <= tempBest)  optimalBoard = bclones[i];
            beta = Math.min(beta, best);
          }

          if (i == 3 && col < 7 && temp[row][col+1] == 0) {
            bclones[i].addKeyValue((row*8+col+2), "O");
            int tempBest = best;
            best = Math.min(best, minimax(depth+1, true, bclones[i], alpha, beta));
            if (best <= tempBest)  optimalBoard = bclones[i];
            beta = Math.min(beta, best);
          }

          if (beta <= alpha)    break;
        }
          
        return best;
      }
    }

    public int getNextMove(FourInALineBoard board) {
      startTime = System.nanoTime();
      int score = minimax(0, true, board, MIN, MAX);
      
      if (timedOut) {
        boolean gotMove = false;
        int moveToReturn = 0;
        int[][] temp = board.convertToArray();

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (temp[i][j] == 0) {
                    gotMove = true;
                    moveToReturn = i*8+j+1;
                    break;
                }
            }

            if (gotMove)    break;
        }

        timedOut = false;
        return moveToReturn;
      }
      
      return optimalBoard.getLastMove();
    }
}
