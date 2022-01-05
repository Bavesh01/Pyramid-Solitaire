import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;

/**
 * Do not modify this file. This file should compile correctly with your code!
 * You DO NOT need to submit this file.
 */
public class Hw04TypeChecks {
  /**
   * The contents of this method are meaningless.
   * They are only here to ensure that your code compiles properly.
   */
  public static void main(String[] args) {
    cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel<?> model;
    model = PyramidSolitaireCreator.create(PyramidSolitaireCreator.GameType.BASIC);
    model = PyramidSolitaireCreator.create(PyramidSolitaireCreator.GameType.RELAXED);
    model = PyramidSolitaireCreator.create(PyramidSolitaireCreator.GameType.MULTIPYRAMID);
  }
}
