package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;

/**
 * {@code PyramidSolitaireCreator} is a factory method.
 * It is used to create an instance of {@code PyramidSolitaireModel}.
 */
public class PyramidSolitaireCreator {

  /**
   * represents all of the playable inherited classes in {@code PyramidSolitaireModel}.
   */
  public enum GameType { BASIC, RELAXED, MULTIPYRAMID }

  private PyramidSolitaireCreator() {

  }

  /**
   * This is the factory method that returns a {@code PyramidSolitaireModel} obj.
   * @param type The specific type, i.e. class of Pyramid Solitaire required.
   * @return A inherited class of {@code PyramidSolitaireModel} interface.
   */
  public static PyramidSolitaireModel<?> create(GameType type) {
    switch (type) {
      case BASIC:
        return new BasicPyramidSolitaire();
      case RELAXED:
        return new RelaxedPyramidSolitaire();
      case MULTIPYRAMID:
        return new MultiPyramidSolitaire();
      default:
        throw new IllegalArgumentException("Stupid input!");
    }
  }
}
