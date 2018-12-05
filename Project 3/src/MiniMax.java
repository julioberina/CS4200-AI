public class MiniMax {
    private int MIN = -Integer.MIN_VALUE;
    private int MAX = Integer.MAX_VALUE;
    private int maxDepth = 6;
    private int oppLastMove = 0;

    public void setLastMove(int move) { oppLastMove = move; }

    public int minimax(int depth, boolean isMax, FourInALineBoard b, int mIndex, int alpha, int beta) {
      if (depth == maxDepth || b.aiScore(b.hasFourInARow(b.convertToArray())) != 0)
        return mIndex;

      if (isMax) {
        int best = MIN;

        FourInALineBoard b1, b2, b3, b4;

        try { b1 = (FourInALineBoard)b.clone(); }
        catch (CloneNotSupportedException e) { e.printStackTrace(); }

        try { b2 = (FourInALineBoard)b.clone(); }
        catch (CloneNotSupportedException e) { e.printStackTrace(); }

        try { b3 = (FourInALineBoard)b.clone(); }
        catch (CloneNotSupportedException e) { e.printStackTrace(); }

        try { b4 = (FourInALineBoard)b.clone(); }
        catch (CloneNotSupportedException e) { e.printStackTrace(); }


      }
      else {

      }
    }
}
