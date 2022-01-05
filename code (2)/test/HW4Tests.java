import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class for testing the controller and the functionality of the ICommand classes.
 */
public class HW4Tests {

  PyramidSolitaireModel relaxed = PyramidSolitaireCreator.create(GameType.RELAXED);
  PyramidSolitaireModel multi = PyramidSolitaireCreator.create(GameType.MULTIPYRAMID);

  @Test
  public void TestRelaxedRemove1() {
    relaxed.startGame(this.makeReverseDeck(), false,9,3);
    relaxed.remove(8, 6, 8, 0);
    relaxed.remove(7,0,8,1);
    Assert.assertEquals(""
        + "                K♠\n"
        + "              Q♠  J♠\n"
        + "            10♠ 9♠  8♠\n"
        + "          7♠  6♠  5♠  4♠\n"
        + "        3♠  2♠  A♠  K♣  Q♣\n"
        + "      J♣  10♣ 9♣  8♣  7♣  6♣\n"
        + "    5♣  4♣  3♣  2♣  A♣  K♦  Q♦\n"
        + "  .   10♦ 9♦  8♦  7♦  6♦  5♦  4♦\n"
        + ".   .   A♦  K♥  Q♥  J♥  .   9♥  8♥\n"
        + "Draw: 7♥, 6♥, 5♥", new PyramidSolitaireTextualView(relaxed).toString());
  }

  @Test
  public void TestRelaxedRemove2() {
    relaxed.startGame(this.makeReverseDeck(), false,9,3);
    relaxed.removeUsingDraw(2,8,8);
    relaxed.discardDraw(1);
    relaxed.remove(8,3);
    relaxed.remove(8,7,7,7);
    Assert.assertEquals(""
        + "                K♠\n"
        + "              Q♠  J♠\n"
        + "            10♠ 9♠  8♠\n"
        + "          7♠  6♠  5♠  4♠\n"
        + "        3♠  2♠  A♠  K♣  Q♣\n"
        + "      J♣  10♣ 9♣  8♣  7♣  6♣\n"
        + "    5♣  4♣  3♣  2♣  A♣  K♦  Q♦\n"
        + "  J♦  10♦ 9♦  8♦  7♦  6♦  5♦  .\n"
        + "3♦  2♦  A♦  .   Q♥  J♥  10♥ .   .\n"
        + "Draw: 7♥, 3♥, 4♥", new PyramidSolitaireTextualView(relaxed).toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void TestRelaxedRemoveInvalid1() {
    relaxed.startGame(this.makeReverseDeck(), false,9,3);
    relaxed.remove(8,7,7,7);
  }

  @Test (expected = IllegalArgumentException.class)
  public void TestRelaxedRemoveInvalid2() {
    relaxed.startGame(this.makeReverseDeck(), false,9,3);
    relaxed.remove(8,7,8,8);
  }

  @Test (expected = IllegalArgumentException.class)
  public void TestSingleRemoveInvalid() {
    relaxed.startGame(this.makeReverseDeck(), false,9,3);
    relaxed.removeUsingDraw(2,8,8);
    relaxed.discardDraw(1);
    relaxed.removeUsingDraw(1,8,6);
    relaxed.removeUsingDraw(0,7,6);
  }

  @Test (expected = IllegalArgumentException.class)
  public void TestRelaxedRemoveInvalidForBasic() {
    PyramidSolitaireModel basic = new BasicPyramidSolitaire();
    basic.startGame(this.makeReverseDeck(), false,9,3);
    basic.removeUsingDraw(2,8,8);
    basic.discardDraw(1);
    basic.remove(8,3);
    basic.remove(8,7,7,7);
  }

  @Test
  public void TestValidMovesBasic2() {
    PyramidSolitaireModel basic = new BasicPyramidSolitaire();
    basic.startGame(this.makeReverseDeck(), false, 9, 3);
    basic.removeUsingDraw(2, 8, 8);
    basic.discardDraw(1);
    basic.remove(8, 3);
    Assert.assertEquals(""
        + "                K♠\n"
        + "              Q♠  J♠\n"
        + "            10♠ 9♠  8♠\n"
        + "          7♠  6♠  5♠  4♠\n"
        + "        3♠  2♠  A♠  K♣  Q♣\n"
        + "      J♣  10♣ 9♣  8♣  7♣  6♣\n"
        + "    5♣  4♣  3♣  2♣  A♣  K♦  Q♦\n"
        + "  J♦  10♦ 9♦  8♦  7♦  6♦  5♦  4♦\n"
        + "3♦  2♦  A♦  .   Q♥  J♥  10♥ 9♥  .\n"
        + "Draw: 7♥, 3♥, 4♥", new PyramidSolitaireTextualView(basic).toString());
  }


  @Test
  public void TestRelaxedGameOverTrue() {
    relaxed.startGame(this.makeReverseDeck(), false,3,0);
    Assert.assertEquals(true, relaxed.isGameOver());
  }

  @Test
  public void TestRelaxedGameOverFalse() {
    relaxed.startGame(this.makeReverseDeck(), false,6,0);
    Assert.assertEquals(false, relaxed.isGameOver());
  }

  //multiTests

  @Test (expected = IllegalArgumentException.class)
  public void TestMultiInvalidArgs() {
    multi.startGame(relaxed.getDeck(), false,2,0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void TestGetDeckSize() {
    multi.startGame(relaxed.getDeck(), false,2,0);
    Assert.assertEquals(104, multi.getDeck().size());
  }


  @Test
  public void TestMultiValidArgs() {
    multi.startGame(multi.getDeck(), false, 8, 5);
    System.out.println(new PyramidSolitaireTextualView(multi).toString());
    Assert.assertEquals(""
            + "              A♥  .   .   .   2♥  .   .   .   3♥\n"
            + "            4♥  5♥  .   .   6♥  7♥  .   .   8♥  9♥\n"
            + "          10♥ J♥  Q♥  .   K♥  A♦  2♦  .   3♦  4♦  5♦\n"
            + "        6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♣  2♣  3♣  4♣\n"
            + "      5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣  K♣  A♠  2♠  3♠  4♠\n"
            + "    5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠  K♠  A♥  2♥  3♥  4♥  5♥\n"
            + "  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥  A♦  2♦  3♦  4♦  5♦  6♦  7♦\n"
            + "8♦  9♦  10♦ J♦  Q♦  K♦  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣\n"
            + "Draw: J♣, Q♣, K♣, A♠, 2♠",
        new PyramidSolitaireTextualView(multi).toString());
  }

  @Test
  public void TestMultiMoves1() {
    multi.startGame(multi.getDeck(), false, 7, 3);
    multi.remove(6, 6, 6, 7);
    multi.remove(6,0);
    multi.discardDraw(0);
    multi.removeUsingDraw(1,6,12);
    System.out.println(new PyramidSolitaireTextualView(multi).toString());
    Assert.assertEquals(""
            + "            A♥  .   .   2♥  .   .   3♥\n"
            + "          4♥  5♥  .   6♥  7♥  .   8♥  9♥\n"
            + "        10♥ J♥  Q♥  K♥  A♦  2♦  3♦  4♦  5♦\n"
            + "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♣  2♣\n"
            + "    3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣  K♣\n"
            + "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n"
            + ".   A♥  2♥  3♥  4♥  5♥  .   .   8♥  9♥  10♥ J♥  .\n"
            + "Draw: 3♦, 4♦, 2♦",
        new PyramidSolitaireTextualView(multi).toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void TestMultiInvalidRemoveNullIndex() {
    multi.startGame(multi.getDeck(), false,6,3);
    multi.remove(1,2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void TestMultiInvalidRemoveRelaxed() {
    multi.startGame(multi.getDeck(), false,6,3);
    multi.remove(5,4,5,7);
    multi.remove(4,4,5,5);
  }

  @Test
  public void TestMultiGameOverTrue() {
    multi.startGame(multi.getDeck(), false,2,0);
    multi.remove(1,2,1,3);
    Assert.assertEquals(true, multi.isGameOver());
  }

  @Test
  public void TestMultiGameOverFalse() {
    multi.startGame(multi.getDeck(), false,2,0);
    Assert.assertEquals(false, multi.isGameOver());
  }

  @Test
  public void TestMultiGetScore() {
    multi.startGame(multi.getDeck(), false,2,0);
    Assert.assertEquals(28, multi.getScore());
    multi.remove(1,2,1,3);
    Assert.assertEquals(15, multi.getScore());
  }




  //TODO:
  // 1. test Relaxed Pyramid Solitaire - other tests.
  // 2. Relaxed pyramid solitaire - remove & isGameOver
  // 3. Test Multipyramid solitaire for errors

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