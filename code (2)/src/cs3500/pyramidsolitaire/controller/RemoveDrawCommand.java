package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

class RemoveDrawCommand extends AbstractCommand {

  private Integer row;
  private Integer col;
  private Integer drawIndex;

  public RemoveDrawCommand(PyramidSolitaireModel model) {
    super(model);
    row = null;
    col = null;
    drawIndex = null;
  }

  @Override
  public void run(Scanner scn, Appendable out) throws IOException {
    nextIntOrQuit(scn);
    drawIndex = nextInt;
    nextIntOrQuit(scn);
    row = nextInt;
    nextIntOrQuit(scn);
    col = nextInt;
    try {
      if (!Arrays.asList(row, col, drawIndex).contains(null)) {
        model.removeUsingDraw(drawIndex - 1,row - 1, col - 1);
      }
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
  }
}
