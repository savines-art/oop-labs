package sys.pro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DeckTest {
    @Test
    void exhaustDeck() {
        Deck deck = new Deck();
        Collections.shuffle(deck.cards);
        int i = 0;
        Card card = deck.take();
        while(card != null) {
            i++;
            card = deck.take();
        }
        assertTrue(deck.isEmpty());
        assertEquals(52, i);
    }

    @Test
    void twoDecks() {
        for (int i = 2; i < 11; i++) {
            Deck deck = new Deck(i);
            Collections.shuffle(deck.cards);
            int j = 0;
            Card card = deck.take();
            while(card != null) {
                j++;
                card = deck.take();
            }
            assertTrue(deck.isEmpty());
            assertEquals(52 * i, j);
        }
    }

    @Test
    void checkDeck() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("ace", "hearts"));
        cards.add(new Card("four", "clubs"));
        cards.add(new Card("three", "spades"));
        cards.add(new Card("king", "diamonds"));
        Deck deck = new Deck(cards);

        for (int i = cards.size() - 1; i > -1; i--) {
            assertEquals(cards.get(i).toString(), deck.take().toString());
        }
        assertTrue(deck.isEmpty());
    }
}
