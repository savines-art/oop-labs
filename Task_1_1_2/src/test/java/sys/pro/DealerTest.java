package sys.pro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Tests for dealer's hand.
 */
public class DealerTest {
    /**
     * Testing dealer's hand if his card is hidden or not.
     */
    @Test
    void dealerHand() {
        Dealer dealer = new Dealer();
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("ace", "hearts"));
        cards.add(new Card("four", "clubs"));
        cards.add(new Card("three", "spades"));
        cards.add(new Card("king", "diamonds"));

        Deck deck = new Deck(cards);

        dealer.start(deck);
        assertEquals("[diamonds king(10), *hidden*]", dealer.toString());
        dealer.makeMove(deck);

        assertEquals("[diamonds king(10), spades three(3), clubs four(4)] => 17", dealer.toString());
    }
}
