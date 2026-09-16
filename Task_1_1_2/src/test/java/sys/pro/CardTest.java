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
        Card card = new Card("ace", "clubs");
        assertFalse(card.isHidden);
        assertEquals("clubs ace(11)", card.toString());
        card.flip();
        assertEquals("*hidden*", card.toString());
    }
}
