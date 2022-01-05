package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;
import java.util.Scanner;

class DiscardCommand extends AbstractCommand {
  private Integer numDraw;

  public DiscardCommand(PyramidSolitaireModel model) {
    super(model);
    this.numDraw = null;
  }

  @Override
  public void run(Scanner scn, Appendable out) throws IOException {
    nextIntOrQuit(scn);
    numDraw = nextInt;
    try {
      if (numDraw != null) {
        model.discardDraw(numDraw - 1);
      }
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
  }
}
