package sys.pro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for printing cards.
 */
public class CardTest {
    /**
     * Checking if hiding and opening card works correctly.
     */
    @Test
    void testPrinting() {
        Card card = new Card(CardName.ACE, CardSuit.CLUBS);
        assertFalse(card.isHidden);
        assertEquals("clubs ace", card.toString());
        card.flip();
        assertEquals("*hidden*", card.toString());
    }
}
