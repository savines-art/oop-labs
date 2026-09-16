package sys.pro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    @Test
    void testPrinting() {
        Card card = new Card("ace", "clubs");
        assertFalse(card.isHidden);
        assertEquals("clubs ace(11)", card.toString());
        card.flip();
        assertEquals("*hidden*", card.toString());
    }
}
