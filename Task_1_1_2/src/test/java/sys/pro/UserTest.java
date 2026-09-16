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

        cards.add(new Card("ace", "hearts"));
        cards.add(new Card("four", "clubs"));
        cards.add(new Card("three", "spades"));
        cards.add(new Card("king", "diamonds"));

        Deck deck = new Deck(cards);

        user.start(deck);
        user.makeMove(deck);
        user.makeMove(deck);

        assertEquals("[diamonds king(10), spades three(3), clubs four(4), hearts ace(1)] => 18",
                user.toString());
    }
}
