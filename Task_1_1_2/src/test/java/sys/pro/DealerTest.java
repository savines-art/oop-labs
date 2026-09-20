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
        List<Card> cards = new ArrayList<>();

        cards.add(new Card(CardName.ACE, CardSuit.HEARTS));
        cards.add(new Card(CardName.FOUR, CardSuit.CLUBS));
        cards.add(new Card(CardName.THREE, CardSuit.SPADES));
        cards.add(new Card(CardName.KING, CardSuit.DIAMONDS));

        Deck deck = new Deck(cards);
        Dealer dealer = new Dealer();
        dealer.start(deck);
        assertEquals("[diamonds king, *hidden*]", dealer.toString());
        dealer.hand.getLast().flip();
        dealer.getCard(deck);

        assertEquals("[diamonds king, spades three, clubs four] => 17",
                dealer.toString());
    }
}
