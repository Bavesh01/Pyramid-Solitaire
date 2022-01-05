package cs3500.pyramidsolitaire.model.hw02;

/**
 * Values taken by a {@code Card} used in PyramidSolitaireModel.
 * Values from 1 to 13 represent the face value of the {@code Card}.
 */
public enum Values {
  ONE(1), TWO(2), THREE(3),
  FOUR(4), FIVE(5), SIX(6),
  SEVEN(7), EIGHT(8), NINE(9),
  TEN(10), ELEVEN(11), TWELVE(12),
  THIRTEEN(13);

  private final int value;

  /**
   * Private constructor for the enum.
   * @param value value of the card.
   */
  private Values(int value) {
    this.value = value;
  }

  /**
   * Gets the value of the card.
   * @return the value, as an int.
   */
  public int getValue() {
    return this.value;
  }

  /**
   * Renders the object as a String.
   * @return the value, as a String. However, 11 is "J", 12 ="Q", 13="K", 1 ="A"
   */
  @Override
  public String toString() {
    switch (this) {
      case ELEVEN:
        return "J";
      case TWELVE:
        return "Q";
      case THIRTEEN:
        return "K";
      case ONE:
        return "A";
      default:
        return "" + getValue();
    }
  }
}