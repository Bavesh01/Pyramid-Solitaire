package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.Scanner;

class RemoveOneCommand extends AbstractCommand {

  private Integer row;
  private Integer col;

  public RemoveOneCommand(PyramidSolitaireModel model) {
    super(model);
    this.row = null;
    this.col = null;
  }

  @Override
  public void run(Scanner scn, Appendable out) {
    nextIntOrQuit(scn);
    row = nextInt;
    nextIntOrQuit(scn);
    col = nextInt;
    try {
      if (row != null && col != null) {
        model.remove(row - 1, col - 1);
      }
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
  }
}
