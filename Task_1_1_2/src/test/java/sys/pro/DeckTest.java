package sys.pro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Tests for different constructs of decks.
 */
public class DeckTest {
    /**
     * Default deck.
     */
    @Test
    void exhaustDeck() {
        Deck deck = new Deck();
        Collections.shuffle(deck.cards);
        int i = 0;
        Card card = deck.take();
        while (card != null) {
            i++;
            card = deck.take();
        }
        assertTrue(deck.isEmpty());
        assertEquals(52, i);
    }

    /**
     * Multiple decks from 2 to 10.
     */
    @Test
    void twoDecks() {
        for (int i = 2; i < 11; i++) {
            Deck deck = new Deck(i);
            Collections.shuffle(deck.cards);
            int j = 0;
            Card card = deck.take();
            while (card != null) {
                j++;
                card = deck.take();
            }
            assertTrue(deck.isEmpty());
            assertEquals(52 * i, j);
        }
    }

    /**
     * Custom deck test.
     */
    @Test
    void checkDeck() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card(CardName.ACE, CardSuit.HEARTS));
        cards.add(new Card(CardName.FOUR, CardSuit.CLUBS));
        cards.add(new Card(CardName.THREE, CardSuit.SPADES));
        cards.add(new Card(CardName.KING, CardSuit.DIAMONDS));
        Deck deck = new Deck(cards);

        for (int i = cards.size() - 1; i > -1; i--) {
            assertEquals(cards.get(i).toString(), deck.take().toString());
        }
        assertTrue(deck.isEmpty());
    }
}
