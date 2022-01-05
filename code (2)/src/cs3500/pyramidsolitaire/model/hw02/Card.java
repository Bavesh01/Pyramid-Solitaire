package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

/**
 * A class representing a Card used BasicPyramidSolitaireModel.
 * Used as a unit of decks in the model.
 */
public class Card implements Cloneable {

  private final Values value;
  private final Suits suite;

  /**
   * Constructor for the {@code Card} object.
   * @param suite The suit of the card.
   * @param value The value the card holds.
   *              J = 11, Q =12, K =13, A =1.
   * @throws NullPointerException if arguments are null.
   */
  public Card(Suits suite, Values value) {
    Objects.requireNonNull(suite);
    Objects.requireNonNull(value);
    this.value = value;
    this.suite = suite;
  }

  /**
   * The integer value of the card, defined by the game.
   * @return the value of the card, as an int.
   */
  public int getValue() {
    return value.getValue();
  }


  /**
   * Checks the {@code suit} and the {@code value} to establish equality.
   * @return returns true if 2 cards have same {@code suit} and {@code value}.
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Card)) {
      return false;
    }
    else {
      return this.suite == ((Card) obj).suite
          && this.value == ((Card) obj).value;
    }
  }

  /**
   * Gets the hashCode of the object.
   * @return if 2 cards have same {@code suit} and {@code value}, they get the same hashCode.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.suite, this.value);
  }

  /**
   * Clones the object with same parameters.
   * @return returns a type {@code Card} with same @code suit} and {@code value}.
   */
  @Override
  public Card clone() {
    return new Card(this.suite, this.value);
  }

  /** Renders the object as a string.
   * @return returns {@code suit} and {@code value} as a String.
   */
  @Override
  public String toString() {
    String s = this.value.toString() + this.suite.toString();
    return s;
  }
}


