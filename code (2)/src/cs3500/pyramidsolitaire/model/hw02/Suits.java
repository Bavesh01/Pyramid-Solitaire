package cs3500.pyramidsolitaire.model.hw02;

/**
 * Suites of the {@code Card} used in PyramidSolitaireModel.
 * Suits HEARTS, SPADES, DIAMONS, CLUBS represent the suit of the {@code Card}.
 */
public enum Suits {
  HEARTS, SPADES, DIAMONDS, CLUBS;

  /**
   * Renders the object as a String.
   * @return the value, as a symbol of the suit
   */
  @Override
  public String toString() {
    switch (this) {
      case HEARTS:
        return "♥";
      case SPADES:
        return "♠";
      case DIAMONDS:
        return "♦";
      case CLUBS:
        return "♣";
      default:
        throw new IllegalArgumentException();
    }
  }
}
