package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A mock model class for testing wiring.
 */
public class MockSolitaire implements PyramidSolitaireModel {

  final StringBuilder log;

  public MockSolitaire(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public List getDeck() {
    return null;
  }

  @Override
  public void startGame(List deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {
    log.append("numDraw: " + numDraw);
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    row1 = row1 + 0;
  }

  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {
    row = row + 0;
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    row = row + 0;
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {
    drawIndex = drawIndex * 1;
  }

  @Override
  public int getNumRows() {
    return 0;
  }

  @Override
  public int getNumDraw() {
    return 0;
  }

  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    return false;
  }

  @Override
  public int getScore() throws IllegalStateException {
    return 0;
  }

  @Override
  public Object getCardAt(int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public List getDrawCards() throws IllegalStateException {
    return new ArrayList();
  }
}
