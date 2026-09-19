package sys.pro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Tests for user's hand.
 */
public class UserTest {
    /**
     * Testing that user's hand and scores are printed correctly.
     */
    @Test
    void userHand() {
        User user = new User();
        List<Card> cards = new ArrayList<>();

        cards.add(new Card(CardName.ACE, CardSuit.HEARTS));
        cards.add(new Card(CardName.FOUR, CardSuit.CLUBS));
        cards.add(new Card(CardName.THREE, CardSuit.SPADES));
        cards.add(new Card(CardName.KING, CardSuit.DIAMONDS));

        Deck deck = new Deck(cards);

        user.start(deck);
        user.getCard(deck);
        user.getCard(deck);

        assertEquals("[diamonds king, spades three, clubs four, hearts ace] => 18",
                user.toString());
    }
}
