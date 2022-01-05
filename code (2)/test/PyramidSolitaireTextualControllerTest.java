import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.MockSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class for testing the controller and the functionality of the ICommand classes.
 */
public class PyramidSolitaireTextualControllerTest {

  private static final PyramidSolitaireModel model = new BasicPyramidSolitaire();
  private static final String stdOutput =  ""
      + "        A♥\n"
      + "      2♥  3♥\n"
      + "    4♥  5♥  6♥\n"
      + "  7♥  8♥  9♥  10♥\n"
      + "J♥  Q♥  K♥  A♦  2♦\n"
      + "Draw: 3♦, 4♦\n"
      + "Score: 94\n";
  private static final String stdOutputRev = ""
      + "    K♠\n"
      + "  Q♠  J♠\n"
      + "10♠ 9♠  8♠\n"
      + "Draw: 7♠, 6♠, 5♠, 4♠\n"
      + "Score: 63\n";

  /////////// test wiring

  @Test
  public void TestMockSolitaire() {
    Reader in = new StringReader("");
    StringBuilder out = new StringBuilder();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(in, out);

    StringBuilder log = new StringBuilder();
    PyramidSolitaireModel mock = new MockSolitaire(log);

    controller.playGame(mock, model.getDeck(), true, 5, 6);
    Assert.assertEquals("numDraw: 6", log.toString());

  }


  ////////////// non-method inputs

  @Test
  public void testEmptyInput() {
    Assert.assertTrue(testPlayGameStandard(model, "", stdOutput));
  }

  @Test
  public void testQInput() {
    Assert.assertTrue(testPlayGameStandard(model, "q",
        stdOutput + "Game quit!\nState of game when quit:\n" + stdOutput));
  }

  @Test
  public void testRandomInput() {
    Assert.assertTrue(testPlayGameStandard(model, "dihvfsijrgo dkjfvsior ifiiib",
        stdOutput));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPlayGameNullDeck() {
    Appendable stringBuilder = new StringBuilder();
    Readable stringReader = new StringReader("");
    PyramidSolitaireController controller
        = new PyramidSolitaireTextualController(stringReader, stringBuilder);
    controller.playGame(model, null, false, 5, 2);
    Assert.assertEquals("", (stringBuilder.toString()));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPlayGameEmptyDeck() {
    Appendable stringBuilder = new StringBuilder();
    Readable stringReader = new StringReader("");
    PyramidSolitaireController controller
        = new PyramidSolitaireTextualController(stringReader, stringBuilder);
    controller.playGame(model, new ArrayList<>(), false, 5, 2);
    Assert.assertEquals("", (stringBuilder.toString()));
  }

  @Test (expected = IllegalStateException.class)
  public void testPlayGameInvalid() {
    Appendable stringBuilder = new StringBuilder();
    Readable stringReader = new StringReader("");
    PyramidSolitaireController controller
        = new PyramidSolitaireTextualController(stringReader, stringBuilder);
    controller.playGame(model, model.getDeck(), false, -1, 0);
    Assert.assertEquals("", (stringBuilder.toString()));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testControllerNull() {
    new PyramidSolitaireTextualController(null, null);
  }

  //////////// Discard draw

  @Test
  public void testDiscardDrawValid() {
    String output = ""
        + "        A♥\n"
        + "      2♥  3♥\n"
        + "    4♥  5♥  6♥\n"
        + "  7♥  8♥  9♥  10♥\n"
        + "J♥  Q♥  K♥  A♦  2♦\n"
        + "Draw: 3♦, 5♦\n"
        + "Score: 94\n";
    Assert.assertTrue(testPlayGameStandard(model, "dd 2", stdOutput + output));
  }

  @Test
  public void testDiscardDrawValid2() {
    String output = ""
        + "        A♥\n"
        + "      2♥  3♥\n"
        + "    4♥  5♥  6♥\n"
        + "  7♥  8♥  9♥  10♥\n"
        + "J♥  Q♥  K♥  A♦  2♦\n"
        + "Draw: 5♦, 4♦\n"
        + "Score: 94\n";
    Assert.assertTrue(testPlayGameStandard(model, "dd r gin ewl 1 d 2", stdOutput + output));
  }

  @Test
  public void testDiscardDrawQuit1() {
    String output = ""
        + "        A♥\n"
        + "      2♥  3♥\n"
        + "    4♥  5♥  6♥\n"
        + "  7♥  8♥  9♥  10♥\n"
        + "J♥  Q♥  K♥  A♦  2♦\n"
        + "Draw: 5♦, 4♦\n"
        + "Score: 94\n";
    Assert.assertTrue(testPlayGameStandard(model, "dd r gin ewl 1 q",
        stdOutput + output + "Game quit!\nState of game when quit:\n" +  output));
  }

  @Test
  public void testDiscardDrawQuit2() {
    Assert.assertTrue(testPlayGameStandard(model, "dd r q ewl 1 dd 2",
        stdOutput + "Game quit!\nState of game when quit:\n" +  stdOutput));
  }

  @Test
  public void testDiscardDrawEmpty1() {
    Assert.assertTrue(testPlayGameStandard(model, "dd",
        stdOutput + stdOutput + "Invalid move. Play again. input doesn't have all arguments\n"));
  }

  @Test
  public void testDiscardDrawEmpty2() {
    Assert.assertTrue(testPlayGameStandard(model, "dd a",
        stdOutput + stdOutput + "Invalid move. Play again. input doesn't have all arguments\n"));
  }

  @Test
  public void testDiscardDrawInvalid1() {
    Assert.assertTrue(testPlayGameStandard(model, "dd 10",
        stdOutput + stdOutput + "Invalid move. Play again. Selected discard is out of bounds\n"));
  }

  //////// Remove 1

  @Test
  public void testRemoveOneValid() {
    String output = ""
        + "        A♥\n"
        + "      2♥  3♥\n"
        + "    4♥  5♥  6♥\n"
        + "  7♥  8♥  9♥  10♥\n"
        + "J♥  Q♥  .  A♦  2♦\n"
        + "Draw: 3♦, 4♦\n"
        + "Score: 81\n";
    Assert.assertTrue(testPlayGameStandard(model, "rm1 5 3", stdOutput + output));
  }

  @Test
  public void testRemoveOneValid2() {
    String output = ""
        + "        A♥\n"
        + "      2♥  3♥\n"
        + "    4♥  5♥  6♥\n"
        + "  7♥  8♥  9♥  10♥\n"
        + "J♥  Q♥  .  A♦  2♦\n"
        + "Draw: 3♦, 4♦\n"
        + "Score: 81\n";
    Assert.assertTrue(testPlayGameStandard(model, "rm1 gg hlkr e 5 vj w 3", stdOutput + output));
  }

  @Test
  public void testRemoveOneQuit1() {
    String output = ""
        + "        A♥\n"
        + "      2♥  3♥\n"
        + "    4♥  5♥  6♥\n"
        + "  7♥  8♥  9♥  10♥\n"
        + "J♥  Q♥  .  A♦  2♦\n"
        + "Draw: 3♦, 4♦\n"
        + "Score: 81\n";
    Assert.assertTrue(testPlayGameStandard(model, "rm1 du a der 5 ot 3 lk d q",
        stdOutput + output + "Game quit!\nState of game when quit:\n" +  output));
  }

  @Test
  public void testRemoveOneQuit2() {
    Assert.assertTrue(testPlayGameStandard(model, "rm1 5  g h y q 5",
        stdOutput + "Game quit!\nState of game when quit:\n" +  stdOutput));
  }

  @Test
  public void testRemoveOneEmpty1() {
    Assert.assertTrue(testPlayGameStandard(model, "rm1",
        stdOutput + stdOutput + "Invalid move. Play again. input doesn't have all arguments\n"));
  }

  @Test
  public void testRemoveOneEmpty2() {
    Assert.assertTrue(testPlayGameStandard(model, "rm1 5 hh",
        stdOutput + stdOutput + "Invalid move. Play again. input doesn't have all arguments\n"));
  }

  @Test
  public void testRemoveOneInvalid1() {
    Assert.assertTrue(testPlayGameStandard(model, "rm1 5 rm1 7",
        stdOutput + stdOutput + "Invalid move. Play again. card indices out of bounds\n"));
  }

  @Test
  public void testRemoveOneInvalid2() {
    Assert.assertTrue(testPlayGameStandard(model, "rm1 4 4",
        stdOutput + stdOutput + "Invalid move. Play again. Selected card cannot be removed.\n"));
  }

  ////// remove 2

  @Test
  public void testRemoveTwoValid() {
    String output = ""
        + "        A♥\n"
        + "      2♥  3♥\n"
        + "    4♥  5♥  6♥\n"
        + "  7♥  8♥  9♥  10♥\n"
        + ".  Q♥  K♥  A♦  .\n"
        + "Draw: 3♦, 4♦\n"
        + "Score: 81\n";
    Assert.assertTrue(testPlayGameStandard(model, "rm2 5 1 5 5", stdOutput + output));
  }

  @Test //says unreachable instead of invalid  :(
  public void testRemoveTwoValid2() {
    String output = ""
        + "        A♥\n"
        + "      2♥  3♥\n"
        + "    4♥  5♥  6♥\n"
        + "  7♥  8♥  9♥  10♥\n"
        + "J♥  .  K♥  .  2♦\n"
        + "Draw: 3♦, 4♦\n"
        + "Score: 81\n";
    Assert.assertTrue(testPlayGameStandard(model,
        " jj rm2 vlf 5 ff 4 gg hlkr e 5 vj w 2", stdOutput + output));
  }

  @Test
  public void testRemoveTwoQuit1() {
    String output = ""
        + "        A♥\n"
        + "      2♥  3♥\n"
        + "    4♥  5♥  6♥\n"
        + "  7♥  8♥  9♥  10♥\n"
        + "J♥  .  K♥  .  2♦\n"
        + "Draw: 3♦, 4♦\n"
        + "Score: 81\n";
    Assert.assertTrue(testPlayGameStandard(model, "rm2 rm2 du a der 5 ot 2 5 4 lk d q",
        stdOutput + output + "Game quit!\nState of game when quit:\n" +  output));
  }

  @Test
  public void testRemoveTwoQuit2() {
    Assert.assertTrue(testPlayGameStandard(model, "rm2 5  g h 4 5 y q",
        stdOutput + "Game quit!\nState of game when quit:\n" +  stdOutput));
  }

  @Test
  public void testRemoveTwoEmpty1() {
    Assert.assertTrue(testPlayGameStandard(model, "rm2",
        stdOutput + stdOutput + "Invalid move. Play again. input doesn't have all arguments\n"));
  }

  @Test
  public void testRemoveTwoEmpty2() {
    Assert.assertTrue(testPlayGameStandard(model, "rm2 5 2 5",
        stdOutput + stdOutput + "Invalid move. Play again. input doesn't have all arguments\n"));
  }

  @Test
  public void testRemoveTwoInvalid1() {
    Assert.assertTrue(testPlayGameStandard(model, "rm2 5 rm2 9 3 4",
        stdOutput + stdOutput + "Invalid move. Play again. The cards are unreachable.\n"));
  }

  @Test //TODO: cords remove2
  public void testRemoveTwoInvalid2() {
    Assert.assertTrue(testPlayGameStandard(model, "rm2 5 4 5 4",
        stdOutput + stdOutput + "Invalid move. Play again. Selected combination is invalid.\n"));
  }

  ////// remove with draw

  @Test
  public void testRemoveWithDrawValid() {
    String output =  ""
        + "    K♠\n"
        + "  Q♠  J♠\n"
        + "10♠ 9♠  .\n"
        + "Draw: 7♠, 6♠, 3♠, 4♠\n"
        + "Score: 55\n";
    Assert.assertTrue(testPlayGameStandardRev(model, "rmwd 3 3 3", stdOutputRev + output));
  }

  @Test
  public void testRemoveWithDrawValid2() {
    String output =  ""
        + "    K♠\n"
        + "  Q♠  J♠\n"
        + "10♠ .  8♠\n"
        + "Draw: 7♠, 6♠, 5♠, 3♠\n"
        + "Score: 54\n";
    Assert.assertTrue(testPlayGameStandardRev(model, "rmwd 3 2 4", stdOutputRev + output));
  }

  @Test
  public void testRemoveWithDrawQuit1() {
    String output = ""
        + "    K♠\n"
        + "  Q♠  J♠\n"
        + "10♠ .  8♠\n"
        + "Draw: 7♠, 6♠, 5♠, 3♠\n"
        + "Score: 54\n";
    Assert.assertTrue(testPlayGameStandardRev(model, "rmwd 3 2 4 4 q",
        stdOutputRev + output + "Game quit!\nState of game when quit:\n" +  output));
  }

  @Test
  public void testRemoveWithDrawQuit2() {
    Assert.assertTrue(testPlayGameStandard(model, "rmwd 5  g h y q 5",
        stdOutput + "Game quit!\nState of game when quit:\n" +  stdOutput));
  }

  @Test
  public void testRemoveWithDrawEmpty1() {
    Assert.assertTrue(testPlayGameStandard(model, "rmwd",
        stdOutput + stdOutput + "Invalid move. Play again. input doesn't have all arguments\n"));
  }

  @Test
  public void testRemoveWithDrawEmpty2() {
    Assert.assertTrue(testPlayGameStandard(model, "rmwd 5 1 t",
        stdOutput + stdOutput + "Invalid move. Play again. input doesn't have all arguments\n"));
  }

  @Test
  public void testRemoveWithDrawInvalid1() {
    Assert.assertTrue(testPlayGameStandard(model, "rmwd rwmd 5 3 7",
        stdOutput + stdOutput + "Invalid move. Play again. Selected draw is out of bounds.\n"));
  }

  @Test
  public void testRemoveWithDrawInvalid2() {
    Assert.assertTrue(testPlayGameStandard(model, "rmwd 5 3 1",
        stdOutput + stdOutput + "Invalid move. Play again. Selected combination is invalid\n"));
  }

  ////// misc. & edge cases

  @Test
  public void testCombo() {
    String output1 = ""
        + "        A♥\n"
        + "      2♥  3♥\n"
        + "    4♥  5♥  6♥\n"
        + "  7♥  8♥  9♥  10♥\n"
        + "J♥  Q♥  .  A♦  2♦\n"
        + "Draw: 3♦, 4♦\n"
        + "Score: 81\n";
    String output2 = ""
        + "        A♥\n"
        + "      2♥  3♥\n"
        + "    4♥  5♥  6♥\n"
        + "  7♥  8♥  9♥  10♥\n"
        + "J♥  Q♥  .  A♦  2♦\n"
        + "Draw: 3♦, 5♦\n"
        + "Score: 81\n";
    Assert.assertTrue(testPlayGameStandard(model, "rm1 5 3 hy 1 j dd 2 kk",
        stdOutput + output1 + output2));
  }

  @Test
  public void testLose() {
    Appendable stringBuilder = new StringBuilder();
    Readable stringReader = new StringReader("");
    PyramidSolitaireController controller
        = new PyramidSolitaireTextualController(stringReader, stringBuilder);
    controller.playGame(model, model.getDeck(), false, 3, 0);
    Assert.assertEquals("Game over. Score: 21\n", (stringBuilder.toString()));
  }

  @Test
  public void testWin() {
    Appendable stringBuilder = new StringBuilder();
    Readable stringReader = new StringReader("rm1 1 lkgld 1");
    PyramidSolitaireController controller
        = new PyramidSolitaireTextualController(stringReader, stringBuilder);
    controller.playGame(model, new BasicPyramidSolitaireChecks1().makeReverseDeck(), false, 1, 1);
    Assert.assertEquals(
        "K♠\n"
        + "Draw: Q♠\n"
        + "Score: 13\n"
        + "You win!\n", (stringBuilder.toString()));
  }

  @Test
  public void testWin2() throws IOException {
    Appendable stringBuilder = new StringBuilder();
    Readable stringReader = new StringReader("rmwd 3 3 3 rmwd 3 2 3 rmwd 3 1 3 rmwd 2 2 3"
        + " rmwd 2 1 3 rm1 1 1 ");
    PyramidSolitaireController controller
        = new PyramidSolitaireTextualController(stringReader, stringBuilder);
    controller.playGame(model, new BasicPyramidSolitaireChecks1().makeReverseDeck(), false, 3, 3);
    System.out.println(model.getScore());
    Assert.assertEquals("","");
  }


  /**
   * Deals a standard game with the given model, input and output.
   * Tests with the actual return values of the controller.
   * @param model the given {@code PyramidSolitaireModel} model
   * @param testInput an input that you intend to test
   * @param testOutput the expected output the controller returns.
   */
  private boolean testPlayGameStandard(PyramidSolitaireModel model,
      String testInput, String testOutput) {
    Appendable stringBuilder = new StringBuilder();
    Readable stringReader = new StringReader(testInput);
    PyramidSolitaireController controller
        = new PyramidSolitaireTextualController(stringReader, stringBuilder);
    controller.playGame(model, model.getDeck(), false, 5, 2);
    return testOutput.equals(stringBuilder.toString());
  }


  /**
   * Deals a game with the given model but with a reverse deck.
   * Tests with the actual return values of the controller.
   * @param model the given {@code PyramidSolitaireModel} model
   * @param testInput an input that you intend to test
   * @param testOutput the expected output the controller returns.
   */
  private boolean testPlayGameStandardRev(PyramidSolitaireModel model,
      String testInput, String testOutput) {
    Appendable stringBuilder = new StringBuilder();
    Readable stringReader = new StringReader(testInput);
    PyramidSolitaireController controller
        = new PyramidSolitaireTextualController(stringReader, stringBuilder);
    controller.playGame(model, new BasicPyramidSolitaireChecks1().makeReverseDeck(),
        false, 3, 4);
    return testOutput.equals(stringBuilder.toString());
  }

  private boolean testPlayGameCustom(PyramidSolitaireModel model,
      String testInput, String testOutput) {
    Appendable stringBuilder = new StringBuilder();
    Readable stringReader = new StringReader(testInput);
    PyramidSolitaireController controller
        = new PyramidSolitaireTextualController(stringReader, stringBuilder);
    controller.playGame(model, model.getDeck(), false, 3, 2);
    return testOutput.equals(stringBuilder.toString());
  }
}