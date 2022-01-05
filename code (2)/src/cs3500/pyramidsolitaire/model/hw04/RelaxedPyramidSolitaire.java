package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * {@code RelaxedPyramidSolitaire} is a class which enables the user to play a version of Solitaire.
 * This model has a variation in game mechanics, which enables you to remove a pair of cards.
 * Such that they're diagonal to each other and the upper card is half-exposed.
 */
public class RelaxedPyramidSolitaire
    extends BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {

  public RelaxedPyramidSolitaire() {
    super();
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    if (!relaxedCondition(row1, card1, row2, card2)) { //added condition
      throw new IllegalArgumentException("The cards are unreachable.");
    }
    if (placeValue(getCardAt(row1, card1))
        + placeValue(getCardAt(row2, card2)) != 13) {
      throw new IllegalArgumentException("Selected combination is invalid.");
    }
    workDeck[row1][card1] = null;
    workDeck[row2][card2] = null;
  }


  private boolean relaxedCondition(int r1, int c1, int r2, int c2) {
    if (r1 > r2) { //ensures r1 is the higher (smaller index)
      int token = r1;
      int token2 = c1;
      r1 = r2;
      c1 = c2;
      r2 = token;
      c2 = token2;
    }

    // check bounds
    boolean bool = r1 >= 0 && r1 < rowHeight && c1 < getRowWidth(r1) && c1 >= 0
        && r2 >= 0 && r2 < rowHeight && c2 < getRowWidth(r2) && c2 >= 0;

    boolean condition =
        ((r1 + 1 == r2 && (c1 == c2 || c1 + 1 == c2)) && checkHalfExposed(r1, c1))
            //either r1,c1 is diagonal and half exposed
            || isCardValid(r1, c1);  //or fully exposed

    return bool && condition && isCardValid(r2, c2);
  }

  private boolean checkHalfExposed(int r, int c) {
    boolean ans = true;
    if (r < rowHeight - 1) {
      ans = ans && (placeValue(getCardAt(r + 1, c)) == 0
          || placeValue(getCardAt(r + 1, c + 1)) == 0);
    }
    return ans;
  }

  @Override
  protected boolean hasMove() {
    boolean ans = true;
    for (int i = 0; i < rowHeight; i++) {
      for (int j = 0; j < getRowWidth(i); j++) {
        if (isCardValid(i, j) && i > 0) {
          if (j > 0) {
            ans = ans && placeValue(getCardAt(i,j))
                + placeValue(getCardAt(i - 1, j - 1)) != 13;
          }
          if (j < getRowWidth(i) - 1) {
            ans = ans && placeValue(getCardAt(i,j))
                + placeValue(getCardAt(i - 1, j)) != 13;
          }
        }
      }
    }
    return super.hasMove() && ans;
  }
}
