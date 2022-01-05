package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import java.io.InputStreamReader;


class Main {

  /**
   * A console based testing system.
   * @param args the stream of input strings
   */
  public static void main(String [] args) {
    PyramidSolitaireModel model
        = PyramidSolitaireCreator.create(GameType.MULTIPYRAMID);
    PyramidSolitaireController controller
        = new PyramidSolitaireTextualController(
            new InputStreamReader(System.in), System.out);

    controller.playGame(model,
        model.getDeck(), false, 1, 5);

  }
}

