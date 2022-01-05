package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;


/**
 * The controller class implemented from {@code PyramidSolitaireController}.
 * Useful for managing inputs/outputs, i.e. playing a game with certain commands.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {

  private final Readable input;
  private final Appendable output;
  private PyramidSolitaireModel model = new BasicPyramidSolitaire();

  /**
   * Constructor for object {@code PyramidSolitaireTextualController}.
   * For manipulating inputs/outputs.
   * @param rd The readable, i.e. the input.
   * @param ap The appendable, i.e. the output.
   * @throws IllegalArgumentException if either of the args is null.
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Input arguments cannot be null!");
    }
    this.input = rd;
    this.output = ap;
  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model,
      List<K> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException, IllegalStateException {
    if (model == null || deck == null || deck.size() == 0) {
      throw new IllegalArgumentException("inputs cannot be null/empty!");
    }
    this.model = model;
    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Game cannot be started with given inputs.");
    }
    Scanner scn = new Scanner(this.input);
    ControllerUtil.out = output;
    Map<String, Function<Scanner, ICommand>> knownCommands
        = ControllerUtil.getCommands(model, output);

    try {
      output.append(new PyramidSolitaireTextualView(model).toString() + "\n");
      if (model.isGameOver() && model.getScore() != 0) {
        return;
      }
      output.append("Score: " + model.getScore() + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("Unreadable input/output");
    }

    Function<Scanner, ICommand> funcCommand;
    while (scn.hasNext()) {
      String in = scn.next();
      if (in.equalsIgnoreCase("q")) {
        quitGame();
        return;
      }
      funcCommand = knownCommands.getOrDefault(in, null);
      if (funcCommand != null) {
        ICommand cmd = funcCommand.apply(scn);
        try {
          cmd.run(scn, output);
        } catch (IOException e) {
          throw new IllegalStateException("");
        }
        if (cmd.isQuit()) {
          quitGame();
          return;
        }
        if (model.isGameOver()) {
          this.appendRenderOutput();
          return;
        }
        this.appendRenderOutput();
        this.appendError(cmd);
      }
    }

  }

  /**
   * Appends the encoded error message on to the output.
   * @param cmd the command the error is related to.
   */
  private void appendError(ICommand cmd) {
    if (!cmd.getError().equals("")) {
      try {
        output.append("Invalid move. Play again. " + cmd.getError() + "\n");
      } catch (IOException e) {
        throw new IllegalStateException("Unreadable input/output");
      }
    }
  }


  /**
   * Generates the output that is related to quitting the game.
   */
  private void quitGame() {
    try {
      output.append("Game quit!\nState of game when quit:\n");
      new PyramidSolitaireTextualView(model, output).render();
    } catch (IOException e) {
      throw new IllegalStateException("Unreadable output/input");
    }
  }

  /**
   * Appends the view render on to the output.
   */
  private void appendRenderOutput() {
    try {
      new PyramidSolitaireTextualView(model, output).render();
    } catch (IOException e) {
      throw new IllegalStateException("Unreadable output/input.");
    }
  }
}
