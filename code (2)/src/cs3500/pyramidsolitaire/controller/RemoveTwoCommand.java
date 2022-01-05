package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.Arrays;
import java.util.Scanner;

class RemoveTwoCommand extends AbstractCommand {

  private Integer row1;
  private Integer row2;
  private Integer col1;
  private Integer col2;

  public RemoveTwoCommand(PyramidSolitaireModel model) {
    super(model);
    row1 = null;
    row2 = null;
    col1 = null;
    col2 = null;
  }

  @Override
   public void run(Scanner scn, Appendable out) {
    nextIntOrQuit(scn);
    row1 = nextInt;
    nextIntOrQuit(scn);
    col1 = nextInt;
    nextIntOrQuit(scn);
    row2 = nextInt;
    nextIntOrQuit(scn);
    col2 = nextInt;
    try {
      if (!Arrays.asList(row1, row2, col1, col2).contains(null)) {
        model.remove(row1 - 1, col1 - 1, row2 - 1, col2 - 1);
      }
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
  }
}
