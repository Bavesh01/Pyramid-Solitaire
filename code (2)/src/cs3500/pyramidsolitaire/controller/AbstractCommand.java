package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

abstract class AbstractCommand implements ICommand {

  protected PyramidSolitaireModel model;
  protected Integer nextInt;
  protected boolean quit;
  protected String error;
  protected static Appendable out = ControllerUtil.out;

  protected AbstractCommand(PyramidSolitaireModel model) {
    this.model = model;
    this.nextInt = null;
    this.error = "";
    this.quit = false;
  }

  @Override
  public abstract void run(Scanner scn, Appendable out) throws IOException;

  @Override
  public void nextIntOrQuit(Scanner scn) {
    nextInt = null;
    String next = "";
    while (true) {
      try {
        next = scn.next();
        this.nextInt = Integer.parseInt(next);
        return;
      } catch (NumberFormatException e) {
        //String token = scn.next();
        if (next.equalsIgnoreCase("q")) {
          this.quit = true;
          return;
        }
      } catch (NoSuchElementException e) {
        this.error = "input doesn't have all arguments";
        return;
      }
    }
  }

  @Override
  public boolean isQuit() {
    return quit;
  }

  @Override
  public String getError() {
    return error;
  }
}
