package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


/**
 * Class for testing/playing Pyramid Solitaire on the console with user commands.
 */
public class PyramidSolitaire {

  /**
   * Method to take in arguments and play the game.
   * @param args Arguments to build the instance of {@code PyramidSolitaireModel} obj.
   */
  public static final void main(String[] args) {
    Builder bld = null;
    int num = args.length;
    if (num > 0) {
      try {
        bld = new Builder(args[0]);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
        return;
      }
      if (num > 1) {
        bld.setRow(Integer.valueOf(args[1]));
      }
      if (num > 2) {
        bld.setDraw(Integer.valueOf(args[2]));
      }
    }
    if (bld == null) {
      System.out.println("Invalid inputs. restart game.");
      return;
    }
    PyramidSolitaireModel model = bld.buildStart();
    PyramidSolitaireController controller
        = new PyramidSolitaireTextualController(
        new InputStreamReader(System.in), System.out);
    controller.playGame(model,
        model.getDeck(), false, model.getNumRows(), model.getNumDraw());
  }

  private static class Builder {
    private int row = 7;
    private int draw = 3;
    private final String model;
    private final Map<String, GameType> set = new HashMap<>();

    private Builder(String model) {
      set.putIfAbsent("basic", GameType.BASIC);
      set.putIfAbsent("multipyramid", GameType.MULTIPYRAMID);
      set.putIfAbsent("relaxed", GameType.RELAXED);

      if (set.getOrDefault(model, null) == null) {
        throw new IllegalArgumentException("Invalid input. restart game.");
      }
      this.model = model;
    }

    private Builder setRow(Integer r) {
      if (r != null) {
        this.row = r;
      }
      return this;
    }

    private Builder setDraw(Integer d) {
      if (d != null) {
        this.draw = d;
      }
      return this;
    }

    private PyramidSolitaireModel buildStart() {
      PyramidSolitaireModel game
          = PyramidSolitaireCreator.create(set.getOrDefault(model, null));
      game.startGame(game.getDeck(), false, row, draw);
      return game;
    }
  }

}