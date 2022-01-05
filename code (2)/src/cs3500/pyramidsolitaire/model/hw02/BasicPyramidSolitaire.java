package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implements the PyramidSolitaireModel(K) with K being of class {@code Card}.
 * Models a simple version of Pyramid Solitaire.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {
  protected Card[][] workDeck;
  protected List<Card> drawDeck;
  protected int drawNum;
  protected int rowHeight;

  protected static Suits[] suits = {Suits.HEARTS, Suits.DIAMONDS, Suits.CLUBS, Suits.SPADES};
  protected static Values[] values =
      {Values.ONE, Values.TWO, Values.THREE,
          Values.FOUR, Values.FIVE, Values.SIX,
          Values.SEVEN, Values.EIGHT, Values.NINE,
          Values.TEN, Values.ELEVEN, Values.TWELVE,
          Values.THIRTEEN, };

  /**
   * Constructs a {@code BasicPyramidSolitaire} object.
   * Initializes the fields to a "pre-start" state.
   */
  public BasicPyramidSolitaire() {
    workDeck         = null;
    drawDeck         = null;
    drawNum          = -1;
    rowHeight        = -1;
  }

  ///////////////////////// STARTING GAME ///////////////////////////

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
    //this.lastRow = numRows - 1;

    ///////////////////// checks

    if (numRows < 1 || numRows > 9) {
      throw new IllegalArgumentException("numRows should be between 1 and 9, inclusive");
    }
    if (numDraw > drawSize() || numDraw < 0) {
      throw new IllegalArgumentException("Invalid draw size");
    }

    ////////////////// make deck
    this.workDeck = new Card[numRows][numRows];

    List<Card> newDeck = new ArrayList<Card>();

    if (shuffle) {
      List<Integer> nums = new ArrayList<Integer>();
      for (int i = 0; i < 52; i++) {
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
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j <= i; j++) {
        workDeck[i][j] = newDeck.get(counter).clone();
        counter++;
      }
    }

    for (int c = counter; c < deck.size(); c++) {
      drawDeck.add(deck.get(c).clone());
    }
  }

  ////////////////////////// GAME MECHANICS  ////////////////////////////

  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    if (!isCardValid(row1, card1) || !isCardValid(row2, card2)) {
      throw new IllegalArgumentException("The cards are unreachable.");
    }
    if (placeValue(getCardAt(row1, card1))
        + placeValue(getCardAt(row2, card2)) != 13) {
      throw new IllegalArgumentException("Selected combination is invalid.");
    }
    workDeck[row1][card1] = null;
    workDeck[row2][card2] = null;
  }

  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    if (placeValue(getCardAt(row, card)) != 13 || !isCardValid(row, card)) {
      throw new IllegalArgumentException("Selected card cannot be removed.");
    }
    if (!isCardValid(row, card)) {
      throw new IllegalArgumentException("Selected card is unreachable.");
    }
    else {
      workDeck[row][card] = null;
    }
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    if (!isCardValid(row, card)) {
      throw new IllegalArgumentException("Selected remove is unreachable.");
    }
    if (drawIndex >= drawNum || drawIndex < 0) {
      throw new IllegalArgumentException("Selected draw is out of bounds.");
    }
    if (drawDeck.size() == 0) {
      throw new IllegalArgumentException("Drawpile empty!");
    }
    if (placeValue(getCardAt(row, card))
        + placeValue(drawDeck.get(drawIndex)) != 13) {
      throw new IllegalArgumentException("Selected combination is invalid");
    }
    else if (drawDeck.size() > drawNum) {
      drawDeck.set(drawIndex, drawDeck.remove(drawNum));
    }
    else {
      drawDeck.remove(drawIndex);
    }
    workDeck[row][card] = null;
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    if (drawIndex < 0 || drawIndex >= drawNum || drawDeck.size() <= drawIndex) {
      throw new IllegalArgumentException("Selected discard is out of bounds");
    }
    else if (drawDeck.size() > drawNum) {
      drawDeck.set(drawIndex, drawDeck.remove(drawNum));
    }
    else {
      drawDeck.remove(drawIndex);
    }
  }

  ///////////////////////////////// GETTERS  /////////////////////////////////////

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<Card>();
    for (Suits s : suits) {
      for (Values v : values) {
        deck.add(new Card(s, v));
      }
    }

    return deck;
  }

  @Override
  public int getNumRows() {
    return rowHeight;
  }

  @Override
  public int getNumDraw() {
    return this.drawNum;
  }

  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    if (row > rowHeight - 1 || row < 0) {
      throw new IllegalArgumentException("invalid row input");
    }
    return row + 1;
  }

  @Override
  public Card getCardAt(int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    if (row < 0 || row >= getNumRows() || card < 0 || card >= getRowWidth(row)
        || workDeck == null) {
      throw new IllegalArgumentException("card indices out of bounds");
    }
    if (workDeck[row][card] == null) {
      return null;
    }
    return workDeck[row][card].clone();
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    checkGameStarted();
    List<Card> ans = new ArrayList<>();
    int counter = 0;
    while (drawDeck.size() > counter && counter < getNumDraw()) {
      ans.add(drawDeck.get(counter).clone());
      counter++;
    }
    return ans;
  }

  @Override
  public int getScore() throws IllegalStateException {
    checkGameStarted();
    int sum = 0;
    for (int i = 0; i < rowHeight; i++) {
      for (int j = 0;  j < getRowWidth(i); j++) {
        sum = sum + placeValue(workDeck[i][j]);
      }
    }
    return sum;
  }

  /////////////////////////////// END GAME  ///////////////////////////

  @Override
  public boolean isGameOver() throws IllegalStateException {
    checkGameStarted();
    if (this.getScore() == 0) {
      return true;
    }
    return hasMove() && getDrawCards().size() == 0;
  }

  protected boolean hasMove() {
    boolean remove = true;
    ArrayList<Card> set = getExposedCards();
    for (Card c1 : set) {
      remove = remove &&  placeValue(c1) != 13;
      for (Card c2 : set) {
        if (c1 != c2) {
          remove = remove && (placeValue(c1) + placeValue(c2)) != 13;
        }
      }
    }
    return remove;
  }

  ////////////////////////// PRIVATE HELPERS  ///////////////////////////

  /**
   * Checks if the game has started.
   * @throws IllegalArgumentException if the game hasn't been started.
   */
  protected void checkGameStarted() {
    if (this.drawNum == -1) {
      throw new IllegalStateException("Game hasn't been started.");
    }
  }

  /**
   * Calculates the length of the total stock after dealing.
   * @return length of the total stock after dealing.
   */
  protected int drawSize() {
    int ans = 52;
    for (int i = rowHeight; i > 0; i--) {
      ans -= i;
    }
    return ans;
  }

  /**
   * Checks if the given deck is valid i.e. has all unique cards.
   * @return true if the given deck is valid.
   */
  protected boolean isValid(List<Card> deck) {
    boolean bool = true;
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 4; j++) {
        bool = bool
            && deck.contains(new Card(suits[j], values[i]));
      }
    }
    return bool && deck.size() == 52;
  }

  /**
   * Checks if the card is valid i.e. if the position is within index and it is exposed.
   * @param row the 0-indexed row value.
   * @param card the 0-indexed column value.
   * @return true if the given card is valid.
   */
  protected boolean isCardValid(int row, int card) {
    boolean bool = row >= 0 && row < rowHeight
        && card < getRowWidth(row) && card >= 0;
    if (row < rowHeight - 1) {
      bool = bool && (placeValue(getCardAt(row + 1, card)) == 0
          && placeValue(getCardAt(row + 1, card + 1)) == 0);
    }
    return bool;
  }


  /**
   * Returns all the cards in the pyramid that are exposed.
   * @return List of cards in the pyramid that are exposed.
   */
  protected ArrayList<Card> getExposedCards() {
    ArrayList<Card> ans = new ArrayList<>();
    for (int i = 0; i < rowHeight; i++) {
      for (int j = 0; j < getRowWidth(i); j++) {
        if (isCardValid(i, j)) {
          ans.add(getCardAt(i,j));
        }
      }
    }
    return ans;
  }

  /**
   * Used for getting the face value of the card or 0 for null.
   * @param card The card which has a value.
   * @return the value of the card or 0 if the arg is null.
   */
  protected int placeValue(Card card) {
    if (card == null) {
      return 0;
    }
    else {
      return card.getValue();
    }
  }
}