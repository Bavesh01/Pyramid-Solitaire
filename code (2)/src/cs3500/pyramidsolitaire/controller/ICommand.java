package cs3500.pyramidsolitaire.controller;

import java.io.IOException;
import java.util.Scanner;

interface ICommand {

  /**
   * Runs the command.
   * @param scn The Scanner to parse.
   * @param out The Appendable.
   * @throws IOException if the data is unreadable.
   */
  void run(Scanner scn, Appendable out) throws IOException;

  /**
   * Either assigns the next integer in the Readable or quits the method if it has a "q".
   * Also assigns an error message if there is no integer to retrieve.
   * @param scn The Scanner to parse through the tokens in the Readable.
   */
  void nextIntOrQuit(Scanner scn);

  /**
   * Returns whether the "quit" toggle is on.
   * @return Quit.
   */
  boolean isQuit();

  /**
   * Gets the error message for Appendable.
   * @return the String for the specific error.
   */
  String getError();
}
