import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Values;
import cs3500.pyramidsolitaire.model.hw02.Suits;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * All the tests for checking the (public)
 * methods in the {@code BasicPyramidSolitaire} class.
 */
public class BasicPyramidSolitaireChecks1 {
  private PyramidSolitaireModel<Card> model
      = new BasicPyramidSolitaire();

  /*
   * returns  (in order)
   *                 A♥
   *               2♥  3♥
   *             4♥  5♥  6♥
   *           7♥  8♥  9♥  10♥
   *         J♥  Q♥  K♥  A♦  2♦
   *       3♦  4♦  5♦  6♦  7♦  8♦
   *     9♦  10♦ J♦  Q♦  K♦  A♣  2♣
   *   3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣
   * J♣  Q♣  K♣  A♠  2♠  3♠  4♠  5♠  6♠
   *     7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠
   */
  private final List<Card> deck = model.getDeck();


  //////////////// START GAME //////////////////////////

  /////////// model.startGame() -- Illegal arguments
  // Illegal decks
  @Test (expected = IllegalArgumentException.class)
  public void testStartGameNullDeck() {
    model.startGame(null, false, 6,6);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeckSmallSize() {
    List<Card> deck2 = model.getDeck();
    deck2.remove(10);
    model.startGame(deck2, false, 4,8);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeckBigSize() {
    List<Card> deck2 = model.getDeck();
    deck2.add(model.getDeck().get(0));
    model.startGame(deck2, false, 3,0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeckInvalidCard() {
    List<Card> deck2 = model.getDeck();
    deck2.set(46, null);
    model.startGame(deck2, false, 5,8);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameInvalidNull() {
    List<Card> deck2 = model.getDeck();
    deck2.set(46, null);
    model.startGame(deck2, false, 5,8);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeckDuplicateCard() {
    List<Card> deck2 = model.getDeck();
    deck2.set(37, model.getDeck().get(17));
    model.startGame(deck2, false, 9,5);
  }

  // Other illegal arguments
  @Test (expected = IllegalArgumentException.class)
  public void testStartGameInvalidNumRowsSmall() {
    model.startGame(deck, false, -4,5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameInvalidNumRowsZero() {
    model.startGame(deck, false, 0,5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameInvalidNumRowsLarge() {
    model.startGame(deck, false, 10,5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameInvalidNumDrawNegative() {
    model.startGame(deck, false, 7,-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameInvalidNumDrawLarge() {
    model.startGame(deck, false, 7,25);
  }

  ////////// model.startGame() - legal arguments

  // deck
  @Test
  public void checkValidDeckSize() {
    model.startGame(deck, false, 4,0);
    assertEquals(52, deck.size());
  }

  @Test
  public void checkValidDeckUnique() {
    model.startGame(deck, false, 6,2);
    Assert.assertTrue(isUnique(deck));
  }

  @Test
  public void checkValidDeckAfterMutation() {
    List<Card> deck2 = model.getDeck();
    deck2.set(51, model.getDeck().get(17));
    deck2.set(51, model.getDeck().get(51));
    model.startGame(deck2, false, 6,2);
    Assert.assertTrue(isUnique(deck2));
  }

  @Test
  public void checkDeckNonShuffleOrder() {
    model.startGame(deck, false, 7,0);
    assertEquals(deck.get(0), model.getCardAt(0,0));
    assertEquals(deck.get(5), model.getCardAt(2,2));
  }

  // other args
  @Test
  public void checkDeckShuffleRandomness() {
    List<Card> deck = model.getDeck();
    model.startGame(deck, true, 7, 0);
    Assert.assertFalse(
        deck.get(0).equals(model.getCardAt(0, 0))
            && deck.get(5).equals(model.getCardAt(2, 2)));
  }

  @Test
  public void checkNumRowsAndNumDraws() {
    List deck = model.getDeck();
    for (int numRows = 1; numRows < 10; numRows++) {
      for (int numDraw = 0; numDraw < drawSize(numRows); numDraw++) {
        model.startGame(deck, true, numRows,numDraw);
        assertEquals(model.getNumDraw(), numDraw);
        assertEquals(model.getNumRows(), numRows);
      }
    }
  }

  /////////////////// GAME MECHANICS ///////////////////////

  ////// remove - 2 cards
  @Test (expected = IllegalStateException.class)
  public void testTwoRemoveStateException() {
    model.remove(6,0,2,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidTwoRemoveInvalidIndicies1() {
    model.startGame(deck, false, 5, 2);
    model.remove(6,0,2,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidTwoRemoveInvalidIndicies2() {
    model.startGame(deck, false, 5, 2);
    model.remove(4,0,4,-3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidTwoRemoveNotExposed() {
    model.startGame(deck, false, 5, 2);
    model.remove(4,2,2,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidTwoRemoveInvalidSum() {
    model.startGame(deck, false, 5, 2);
    model.remove(4,0,4,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidTwoRemoveSameCard() {
    model.startGame(deck, false, 6, 0);
    model.remove(5,3,5,3);
  }

  @Test
  public void testValidTwoRemove1() {
    model.startGame(deck, false, 5, 2);
    model.remove(4,1,4,3);
    assertEquals(null, model.getCardAt(4,1));
    assertEquals(null, model.getCardAt(4,3));
  }

  @Test
  public void testValidTwoRemove2() {
    model.startGame(deck, false, 5, 2);
    model.remove(4,0,4,4);
    assertEquals(null, model.getCardAt(4,0));
    assertEquals(null, model.getCardAt(4,4));
  }

  ////// remove - 1 card
  @Test (expected = IllegalStateException.class)
  public void testOneRemoveStateException() {
    model.remove(6,0,2,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidOneRemoveInvalidIndicies1() {
    model.startGame(deck, false, 5, 2);
    model.remove(6,0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidOneRemoveInvalidIndicies2() {
    model.startGame(deck, false, 5, 2);
    model.remove(4,-3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidOneRemoveNotExposed() {
    model.startGame(deck, false, 5, 2);
    model.remove(2,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidOneRemoveInvalidSum() {
    model.startGame(deck, false, 5, 2);
    model.remove(4,1);
  }

  @Test
  public void testValidOneRemove() {
    model.startGame(deck, false, 5, 2);
    model.remove(4,2);
    assertEquals(null, model.getCardAt(4,2));
  }

  ////// draw-remove - 2 cards
  @Test (expected = IllegalStateException.class)
  public void testDrawRemoveStateException() {
    model.removeUsingDraw(0,1,2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDrawRemoveInvalidIndicies1() {
    model.startGame(deck, false, 5, 2);
    model.removeUsingDraw(2,4,2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDrawRemoveInvalidIndicies2() {
    model.startGame(deck, false, 5, 2);
    model.removeUsingDraw(1,5,0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDrawRemoveNotExposed() {
    model.startGame(deck, false, 5, 2);
    model.removeUsingDraw(0,2,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDrawRemoveInvalidSum() {
    model.startGame(deck, false, 5, 2);
    model.removeUsingDraw(0,4,0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDrawRemoveDrawFromEmpty() {
    model.startGame(deck, false, 9, 1);
    for (int i = 0; i < 7; i++) {
      model.discardDraw(0);
    }
    model.removeUsingDraw(2,8,0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDrawRemoveDrawFromZero() {
    model.startGame(deck, false, 5, 0);
    model.removeUsingDraw(2,4,0);
  }

  @Test
  public void testValidDrawRemove1() {
    model.startGame(makeReverseDeck(), false, 3, 4);
    Card removedCard = model.getDrawCards().get(2);
    model.removeUsingDraw(2,2,2);
    assertEquals(null, model.getCardAt(2,2));
    Assert.assertFalse(model.getDrawCards().contains(removedCard));
  }

  @Test
  public void testValidDrawRemove2() {
    model.startGame(makeReverseDeck(), false, 3, 4);
    Card removedCard = model.getDrawCards().get(2);
    model.removeUsingDraw(2,2,2);
    Card removedCard2 = model.getDrawCards().get(3);
    model.removeUsingDraw(3,2,1);
    Card removedCard3 = model.getDrawCards().get(3);
    model.removeUsingDraw(3,1,1);
    assertEquals(null, model.getCardAt(2,2));
    assertEquals(null, model.getCardAt(2,1));
    assertEquals(null, model.getCardAt(1,1));
    Assert.assertFalse(model.getDrawCards().contains(removedCard));
    Assert.assertFalse(model.getDrawCards().contains(removedCard2));
    Assert.assertFalse(model.getDrawCards().contains(removedCard3));
  }

  ////// discard-draw
  @Test (expected = IllegalStateException.class)
  public void testDiscardDrawStateException() {
    model.discardDraw(0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDiscardDrawDrawFromEmpty() {
    model.startGame(deck, false, 9, 1);
    for (int i = 0; i < 7; i++) {
      model.discardDraw(0);
    }
    model.discardDraw(0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDiscardDrawDrawFromZero() {
    model.startGame(deck, false, 5, 0);
    model.discardDraw(0);
  }


  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDiscardDrawInvalidIndicies1() {
    model.startGame(deck, false, 5, 2);
    model.discardDraw(3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidDiscardDrawInvalidIndicies2() {
    model.startGame(deck, false, 9, 1);
    for (int i = 0; i < 6; i++) {
      model.discardDraw(0);
    }
    System.out.println(model.getDrawCards());
    model.discardDraw(1);
  }

  @Test
  public void testValidDiscardDraw1() {
    model.startGame(makeReverseDeck(), false, 3, 4);
    ArrayList<Card> removedCards = new ArrayList<>();
    for (int i = 1; i < 20; i++) {
      Object removedCard = model.getDrawCards().get(i % 4);
      model.discardDraw(i % 4);
      Assert.assertFalse(model.getDrawCards().contains(removedCard));
    }
  }

  @Test
  public void testValidDiscardDraw2() {
    model.startGame(makeReverseDeck(), false, 3, 4);
    model.discardDraw(3);
    assertEquals(model.getNumDraw(), model.getDrawCards().size());
  }



  /////////////////////////// TESTING GETTERS ////////////////////

  // getNumRow
  @Test
  public void testGetNumRowsPreStart() {
    assertEquals(-1, model.getNumRows());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetNumRowsInvalid() {
    model.startGame(deck, false, 0,6);
    model.getNumRows();
  }

  @Test
  public void testGetNumRowsValid() {
    model.startGame(deck, false, 3,4);
    assertEquals(3, model.getNumRows());
  }

  //GetNumDraw
  @Test
  public void testGetNumDrawPreStart() {
    assertEquals(-1, model.getNumDraw());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetNumDrawIllegal() {
    model.startGame(deck, false, 5,-6);
    assertEquals(-1, model.getNumDraw());
  }

  @Test
  public void testGetNumDrawValid() {
    model.startGame(deck, false, 3,4);
    assertEquals(4, model.getNumDraw());
  }

  //GetRowWidth
  @Test (expected = IllegalStateException.class)
  public void testRowWidthPreStart() {
    model.getRowWidth(0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetRowWidthInvalidArgumentLarge() {
    model.startGame(deck, false, 5,6);
    model.getRowWidth(5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetRowWidthInvalidArgumentSmall() {
    model.startGame(deck, false, 5,6);
    model.getRowWidth(-1);
  }

  @Test
  public void testGetRowWidthValid1() {
    model.startGame(deck, false, 5,6);
    assertEquals(5, model.getRowWidth(4));
  }

  @Test
  public void testGetRowWidthValid2() {
    model.startGame(deck, false, 5,6);
    assertEquals(3, model.getRowWidth(2));
  }

  //getScore
  @Test (expected = IllegalStateException.class)
  public void testGetScorePreStart() {
    model.getScore();
  }

  @Test
  public void testGetScoreValidSmall() {
    model.startGame(deck,false,3,6);
    assertEquals(21,model.getScore());
  }

  @Test
  public void testGetScoreValidLarge() {
    model.startGame(makeReverseDeck(),false,5,6);
    assertEquals(116,model.getScore());
  }

  //GetCardAt
  @Test (expected = IllegalStateException.class)
  public void testGetCardAtPreStart() {
    model.getCardAt(2,6);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetCardAtInvalidArgumentLarge() {
    model.startGame(deck, false, 5,6);
    model.getCardAt(5,5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetCardAtInvalidArgumentSmall() {
    model.startGame(deck, false, 5,6);
    model.getCardAt(0,-1);
  }

  @Test
  public void testGetCardAtValid1() {
    model.startGame(makeReverseDeck(), false, 5,6);
    assertEquals("K♠", model.getCardAt(0,0).toString());
  }

  @Test
  public void testGetCardAtValid2() {
    model.startGame(deck, false, 6,6);
    assertEquals("5♦", model.getCardAt(5,2).toString());
  }

  //GetDrawCards
  @Test (expected = IllegalStateException.class)
  public void testGetDrawCardsPreStart() {
    model.getDrawCards();
  }

  @Test
  public void testGetDrawCardsEmpty() {
    model.startGame(deck, false, 6,0);
    assertEquals("[]", model.getDrawCards().toString());
  }

  @Test
  public void testGetDrawCardsValid1() {
    model.startGame(deck, false, 7,2);
    assertEquals("[3♣, 4♣]", model.getDrawCards().toString());
  }

  @Test
  public void testGetDrawCardsValidUpdate() {
    model.startGame(deck, false, 7,2);
    model.discardDraw(0);
    assertEquals("[5♣, 4♣]", model.getDrawCards().toString());
  }

  ///////////isGameOver
  @Test (expected = IllegalStateException.class)
  public void testIsGameOver() {
    model.isGameOver();
  }

  @Test
  public void testIsGameOverTrueWin() {
    model.startGame(makeReverseDeck(), false, 1,0);
    model.remove(0,0);
    assertEquals(true, model.isGameOver());
  }

  @Test
  public void testIsGameOverTrueLost() {
    model.startGame(makeReverseDeck(), false, 5,0);
    assertEquals(false, model.isGameOver());
  }

  @Test
  public void testIsGameOverFalseDiscardAvailable() {
    model.startGame(makeReverseDeck(), false, 2,1);
    Assert.assertFalse(model.isGameOver());
  }

  @Test
  public void testIsGameOverFalseRemoveAvailable() {
    model.startGame(makeReverseDeck(), false, 5,0);
    Assert.assertFalse(model.isGameOver());
  }

  @Test
  public void testIsGameOverFalseDrawRemoveAvailable() {
    model.startGame(makeReverseDeck(), false, 3,3);
    Assert.assertFalse(model.isGameOver());
  }


  ////////////////// private helpers for testing

  /**
   * Calculates the size of the stock pile after dealing the pyramid.
   * @param numRows The number of rows the Pyramid has.
   * @return The size of stock as an int.
   */
  private int drawSize(int numRows) {
    int ans = 52;
    for (int i = numRows; i > 0; i--) {
      ans -= i;
    }
    return ans;
  }

  /**
   * Evaluates if the given deck is unique.
   * @param deck A list of {@code Card} objects.
   * @return true if the each card is present and unique.
   */
  private boolean isUnique(List<Card> deck) {
    Suits[] suits = {Suits.HEARTS, Suits.DIAMONDS, Suits.CLUBS, Suits.SPADES};
    Values[] values =
        {Values.ONE, Values.TWO, Values.THREE,
            Values.FOUR, Values.FIVE, Values.SIX,
            Values.SEVEN, Values.EIGHT, Values.NINE,
            Values.TEN, Values.ELEVEN, Values.TWELVE,
            Values.THIRTEEN,};
    boolean bool = true;
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 4; j++) {
        bool = bool
            && deck.contains(new Card(suits[j], values[i]));
      }
    }
    return bool && deck.size() == 52;
  }

  /*
   *
   * returns  (in order)
   *                 K♠
   *               Q♠  J♠
   *             10♠ 9♠  8♠
   *           7♠  6♠  5♠  4♠
   *         3♠  2♠  A♠  K♣  Q♣
   *       J♣  10♣ 9♣  8♣  7♣  6♣
   *     5♣  4♣  3♣  2♣  A♣  K♦  Q♦
   *   J♦  10♦ 9♦  8♦  7♦  6♦  5♦  4♦
   * 3♦  2♦  A♦  K♥  Q♥  J♥  10♥ 9♥  8♥
   * 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥
   */

  /**
   * Makes a deck in reverse, with the King on top.
   * @return A list of Cards in descending order of value.
   */
  protected List<Card> makeReverseDeck() {
    List winDeck = new ArrayList<>();
    List deck = new BasicPyramidSolitaire().getDeck();
    for (int i = 51; i >= 0; i--) {
      winDeck.add(deck.get(i));
    }
    return winDeck;
  }

}
