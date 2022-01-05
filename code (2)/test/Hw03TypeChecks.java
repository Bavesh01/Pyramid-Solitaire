import java.io.StringReader;

/**
 * Do not modify this file. This file should compile correctly with your code!
 * You DO NOT need to submit this file.
 */
public class Hw03TypeChecks {

  /**
   * The contents of this method are meaningless.
   * They are only here to ensure that your code compiles properly.
   */
  public static void main(String[] args) {
    Readable rd = new StringReader("");
    Appendable ap = new StringBuilder();
    helper(new cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire(),
        new cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController(rd, ap));
  }

  private static <K> void helper(
      cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel<K> model,
      cs3500.pyramidsolitaire.controller.PyramidSolitaireController controller) {
    controller.playGame(model, model.getDeck(), false, 7, 4);
  }

}
