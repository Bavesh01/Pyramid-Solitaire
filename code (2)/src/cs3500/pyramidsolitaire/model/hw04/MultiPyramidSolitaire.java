package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suits;
import cs3500.pyramidsolitaire.model.hw02.Values;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * {@code MultiPyramidSolitaire} is a class which enables the user to play a version of Solitaire.
 * This model has a variation in the pyramid design, and everything else is the same.
 */
public class MultiPyramidSolitaire
    extends BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {

  public MultiPyramidSolitaire() {
    super();
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {

    if (deck == null) {
      throw new IllegalArgumentException("Deck is null");
    }
    if (!isValid(deck)) {
      throw new IllegalArgumentException("Deck is not Valid");
    }

    ////////////////////// assign
    this.drawNum   = numDraw;
    this.rowHeight = numRows;
    this.drawDeck = new ArrayList<Card>();

    ///////////////////// checks
    if (numRows < 1 || numRows > 8) { //check validity
      throw new IllegalArgumentException("numRows should be between 1 and 8, inclusive");
    }
    if (numDraw > drawSize() || numDraw < 0) {
      throw new IllegalArgumentException("Invalid draw size");
    }

    ////////////////// make deck
    this.workDeck = new Card[getRowWidth(getNumRows() - 1)][getRowWidth(getNumRows() - 1)];

    List<Card> newDeck = new ArrayList<>();

    if (shuffle) {
      List<Integer> nums = new ArrayList<>();
      for (int i = 0; i < 104; i++) {
        nums.add(i);
      }
      while (nums.size() > 0) {
        Random ran = new Random();
        newDeck.add(deck.get(nums.remove(ran.nextInt(nums.size()))));
      }
    }
    else {
      newDeck = deck;
    }

    int counter = 0;
    int posnCounter = 0;
    ArrayList<Point> nullPosns = getNullPosns();
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < getRowWidth(i); j++) {
        if (posnCounter < nullPosns.size()
            && i == nullPosns.get(posnCounter).x
            && j == nullPosns.get(posnCounter).y) {
          workDeck[i][j] = null;
          posnCounter++;
        } else {
          try {
            workDeck[i][j] = newDeck.get(counter).clone();
            counter++;
          } catch (Exception e) {
            workDeck[i][j] = null;
          }
        }
      }
    }

    for (int c = counter; c < deck.size(); c++) {
      drawDeck.add(deck.get(c).clone());
    }
  }

  private ArrayList<Point> getNullPosns() {
    ArrayList<Point> posns = new ArrayList<>();
    double mid;
    for (int i = 0; i < rowHeight; i++) {
      if (getRowWidth(i) > cardsInRow(i)) {
        for (int j = i + 1; j < Math.ceil(getRowWidth(i) / 2.0) - 1 - i / 2; j++) {
          posns.add(new Point(i, j));
        }
        for (int j = (int) (Math.ceil(getRowWidth(i) / 2.0) + i % 2) + i / 2;
            j < getRowWidth(i) - 1 - i; j++) {
          posns.add(new Point(i, j));
        }
      }
    }
    return posns;
  }

  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {
    super.checkGameStarted();
    if (row > getNumRows() - 1 || row < 0) {
      throw new IllegalArgumentException("invalid row input");
    }
    return 1 + row + 2 * Math.floorDiv(getNumRows(), 2);
  }


  @Override
  protected int drawSize() {
    int ans = 104;
    for (int i = 0; i < rowHeight; i++) {
      ans -= cardsInRow(i);
    }
    return ans;
  }


  @Override
  protected boolean isValid(List<Card> deck) {
    boolean bool = true;
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 4; j++) {
        bool = bool
            && Collections.frequency(deck, new Card(suits[j], values[i])) == 2;
      }
    }
    return bool && deck.size() == 104;
  }

  private int cardsInRow(int row) {
    return Math.min(3 * (row + 1), row + 1 + 2 * Math.floorDiv(getNumRows(), 2));
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>();
    for (Suits s : suits) {
      for (Values v : values) {
        deck.add(new Card(s, v));
      }
    }
    for (Suits s : suits) {
      for (Values v : values) {
        deck.add(new Card(s, v));
      }
    }
    return deck;
  }
}
