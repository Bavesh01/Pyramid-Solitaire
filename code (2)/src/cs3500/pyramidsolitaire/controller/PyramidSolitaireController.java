package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.List;

/**
 * Provides a controller for interacting with a game of Pyramid Solitaire.
 */
public interface PyramidSolitaireController {

  /**
   * The primary method for beginning and playing a game.
   *
   * @param <K> the type of cards used by the model.
   * @param model The game of solitaire to be played
   * @param deck The deck of cards to be used
   * @param shuffle Whether to shuffle the deck or not
   * @param numRows How many rows should be in the pyramid
   * @param numDraw How many draw cards should be visible
   * @throws IllegalArgumentException if the model or deck is null
   * @throws IllegalStateException if the game cannot be started,
   *          or if the controller cannot interact with the player.
   */
  <K> void playGame(PyramidSolitaireModel<K> model,
      List<K> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException, IllegalStateException;
}
