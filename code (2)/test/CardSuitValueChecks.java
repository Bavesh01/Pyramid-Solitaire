import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Suits;
import cs3500.pyramidsolitaire.model.hw02.Values;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Checks for the public methods in Card, Suits, Values classes.
 */
public class CardSuitValueChecks {

  @Test (expected = NullPointerException.class)
  public void checkSuitNull() {
    new Card(null, Values.EIGHT);
  }

  @Test (expected = NullPointerException.class)
  public void checkValueNull() {
    new Card(Suits.CLUBS, null);
  }

  @Test
  public void checkEquals() {
    Assert.assertTrue(new Card(Suits.CLUBS, Values.EIGHT)
        .equals(new Card(Suits.CLUBS, Values.EIGHT)));
  }

  @Test
  public void checkNonEquals() {
    Assert.assertFalse(new Card(Suits.CLUBS, Values.EIGHT)
        .equals(new Card(Suits.CLUBS, Values.NINE)));
  }

  @Test
  public void testGetValue1() {
    assertEquals(9, new Card(Suits.SPADES, Values.NINE).getValue());
  }

  @Test
  public void testSameHash() {
    Card c8 = new Card(Suits.CLUBS, Values.EIGHT);
    assertEquals(c8.hashCode(), new Card(Suits.CLUBS,Values.EIGHT).hashCode());
  }

  @Test
  public void testNotSameHash() {
    Card c8 = new Card(Suits.CLUBS, Values.EIGHT);
    assertNotEquals(c8.hashCode(), new Card(Suits.CLUBS,Values.NINE).hashCode());
  }

  @Test
  public void testSameClone() {
    Card c8 = new Card(Suits.CLUBS, Values.EIGHT);
    assertEquals(c8, new Card(Suits.CLUBS,Values.EIGHT).clone());
  }

  @Test
  public void testNotSameClone() {
    Card c8 = new Card(Suits.CLUBS, Values.EIGHT);
    assertNotEquals(c8, new Card(Suits.CLUBS,Values.NINE).clone());
  }


  @Test
  public void testToStringC8() {
    Card c8 = new Card(Suits.CLUBS, Values.EIGHT);
    assertEquals("8♣", c8.toString());
  }

  @Test
  public void testToStringDK() {
    Card c8 = new Card(Suits.CLUBS, Values.EIGHT);
    assertEquals("K♦",
        new Card(Suits.DIAMONDS,Values.THIRTEEN).clone().toString());
  }

  ////// Suits and Values

  @Test
  public void checkSuits() {
    assertEquals(Suits.DIAMONDS, Suits.valueOf("DIAMONDS"));
  }

  @Test
  public void checkSuitsToString() {
    assertEquals("♥", Suits.HEARTS.toString());
    assertEquals("♦", Suits.DIAMONDS.toString());
    assertEquals("♣", Suits.CLUBS.toString());
    assertEquals("♠", Suits.SPADES.toString());
  }

  @Test
  public void checkValues() {
    assertEquals(Values.ONE, Values.valueOf("ONE"));
  }

  @Test
  public void checkValuesGetValue() {
    assertEquals(8, Values.EIGHT.getValue());
    assertEquals(13, Values.THIRTEEN.getValue());
  }

  @Test
  public void checkValuesToString() {
    assertEquals("8", Values.EIGHT.toString());
    assertEquals("K", Values.THIRTEEN.toString());
  }
}
