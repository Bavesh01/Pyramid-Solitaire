import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Class for testing the "view" methods from the
 * {@code PyramidSolitaireTextualView} class.
 * Evaluates its only method, toString, under various conditions.
 */
public class PyramidSolitaireViewTests {
  private static PyramidSolitaireModel<Card> model
      = new BasicPyramidSolitaire();

  //TODO: Add more tests

  // Non display cases
  @Test
  public void testViewUnstartedGame() {
    PyramidSolitaireTextualView render
        = new PyramidSolitaireTextualView(new BasicPyramidSolitaire());
    assertEquals("", render.toString());
  }

  @Test
  public void testViewLostGame() {
    model.startGame(model.getDeck(), false, 3, 0);
    PyramidSolitaireTextualView render = new PyramidSolitaireTextualView(model);
    assertEquals("Game over. Score: 21", render.toString());

    model.startGame(makeReverseDeck(), false, 3, 0);
    render = new PyramidSolitaireTextualView(model);
    assertEquals("Game over. Score: 63", render.toString());
  }

  @Test
  public void testViewWonGame() {
    model.startGame(makeReverseDeck(), false, 1, 1);
    model.remove(0,0);
    PyramidSolitaireTextualView render = new PyramidSolitaireTextualView(model);
    assertEquals("You win!", render.toString());
  }

  //Display cases
  @Test
  public void testSimpleSetup() {
    model.startGame(model.getDeck(), false, 2, 2);
    PyramidSolitaireTextualView render = new PyramidSolitaireTextualView(model);
    assertEquals(
        "  A♥\n" 
            + "2♥  3♥\n" 
            + "Draw: 4♥, 5♥",
        render.toString());
  }

  @Test
  public void testSimpleSetup2() {
    model.startGame(makeReverseDeck(), false, 3, 4);
    PyramidSolitaireTextualView render = new PyramidSolitaireTextualView(model);
    assertEquals(
        "    K♠\n" 
            + "  Q♠  J♠\n" 
            + "10♠ 9♠  8♠\n" 
            + "Draw: 7♠, 6♠, 5♠, 4♠",
        render.toString());
    render = new PyramidSolitaireTextualView(model);
    System.out.println(render.toString());
  }

  @Test
  public void testZeroNumDraw() {
    model.startGame(makeReverseDeck(), false, 1, 0);
    PyramidSolitaireTextualView render = new PyramidSolitaireTextualView(model);
    assertEquals("Draw:",
        render.toString().substring(render.toString().length() - 5));
  }

  @Test
  public void testRemovedCards() {
    model.startGame(model.getDeck(), false, 3, 3);
    model.removeUsingDraw(2,2,0);
    model.removeUsingDraw(1,2,1);
    model.removeUsingDraw(1,1,0);

    PyramidSolitaireTextualView render = new PyramidSolitaireTextualView(model);
    assertEquals(
        "    A♥\n"
            + "  . 3♥\n"
            + ". . 6♥\n"
            + "Draw: 7♥, Q♥, 10♥",
        render.toString());
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
  private List makeReverseDeck() {
    List winDeck = new ArrayList<>();
    List deck = new BasicPyramidSolitaire().getDeck();
    for (int i = 51; i >= 0; i--) {
      winDeck.add(deck.get(i));
    }
    return winDeck;
  }
}
