package cs3500.pyramidsolitaire.view;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;
import java.util.Objects;

/**
 * Class for rendering the model from {@code PyramidSolitaireModel}
 * interface. For viewing the state of the model as a string
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {

  private final PyramidSolitaireModel<?> model;
  private Appendable app;

  /**
   * Constructor for a {@code PyramidSolitaireTextualView} object.
   * @param model The {@code PyramidSolitaireModel} object at current state.
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    Objects.requireNonNull(model);
    this.model = model;
  }

  /**
   * Second constructor for {@code PyramidSolitaireTextualView} object.
   * @param model the model taken to view
   * @param app the appendable for returning the output
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable app) {
    this(model);
    this.app = app;
  }

  /**
   * Used for getting the current state of the game,
   * formatted as a String.
   * @return The rendering of the model as a String
   */
  @Override
  public String toString() {
    String s = "";
    if (model.getNumRows() == -1) {
      return "";
    }
    else if (model.isGameOver()) {
      if (model.getScore() == 0) {
        return "You win!";
      }
      else {
        return "Game over. Score: " + model.getScore();
      }
    }
    else {
      s = formatPyramid();
      s = s + formatDrawPile();
    }
    return s;
  }

  /**
   * Appends the space for the String output for the viewer.
   * @param s given string to append space.
   * @param n the row the string is in.
   * @return
   */
  private String addSpace(String s, int n) {
    for (int i = 0; i < n; i++) {
      s = s + "  ";
    }
    return s;
  }

  /**
   * Formats the given model's deck Pyramid into a String.
   * @return A String which looks like a pyramid.
   */
  private String formatPyramid() {
    String s = "";
    for (int i = 0; i < model.getNumRows(); i++) {
      s = addSpace(s, model.getNumRows() - i - 1);
      for (int j = 0; j < model.getRowWidth(i) - 1; j++) {
        String card = this.getString(i, j);
        if (card.length() == 2) {
          s = s + this.getString(i, j) + "  ";
        }
        else if (card.length() == 1) {
          s = s + this.getString(i, j) + "   ";
        }
        else {
          s = s + this.getString(i, j) + " ";
        }
      }
      s = s + getString(i, model.getRowWidth(i) - 1) + "\n";
    }
    return s;
  }

  /**
   * Formats the cards as a Draw pile.
   * @return A String in the format of a Draw pile.
   */
  private String formatDrawPile() {
    String s = "Draw:";
    if (model.getDrawCards().size() == 0) {
      return s;
    }
    else {
      s = s + " ";
      for (int o = 0; o < model.getDrawCards().size(); o++) {
        s = s +  model.getDrawCards().get(o).toString() + ", ";
      }
    }
    s = s.substring(0, s.length() - 2);
    return s;
  }

  /**
   * Used for getting the card info as a String or "." for null.
   * @param i the row of the card, 0 indexed.
   * @param j the column of the card, 0 indexed.
   * @return the card as a String or "." if null.
   */
  private String getString(int i, int j) {
    if (model.getCardAt(i, j) == null) {
      return ".";
    }
    else {
      return this.model.getCardAt(i, j).toString();
    }
  }

  /**
   * Renders the given {@code PyramidSolitaireModel} model.
   * i.e. appends the String output to the output stream.
   * @throws IOException if generating the output is unsuccessful.
   */
  @Override
  public void render() throws IOException {
    if (app != null) {
      try {
        app.append(this.toString() + "\n");
        if (!model.isGameOver()) {
          app.append("Score: " + model.getScore() + "\n");
        }
      } catch (IOException e) {
        throw new IOException("Unreadable data.");
      }
    } else {
      System.out.println(this.toString());
    }
  }
}
