package sys.pro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Test for whole game.
 */
public class BlackjackTest {
    /**
     * Deck gets empty.
     */
    @Test
    void exhaustDeck() {
        List<Card> cards = new ArrayList<>();
        int rounds = 5;
        String input = "1 1";
        cards.add(new Card("ace", "diamonds"));
        cards.add(new Card("ace", "clubs"));
        cards.add(new Card("ace", "hearts"));
        cards.add(new Card("ace", "spades"));
        cards.add(new Card("nine", "clubs"));
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        game.game(in, rounds);
        assertArrayEquals(new int[] {1, 0, 0, 0, 0}, game.results);
    }

    /**
     * Deck is empty at the very beginning.
     */
    @Test
    void emptyDeck() {
        List<Card> cards = new ArrayList<>();
        int rounds = 5;
        String input = "";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        game.game(in, rounds);
        assertArrayEquals(new int[] {0, 0, 0, 0, 0}, game.results);
    }
    /**
     * When user scores 21 in the beginning.
     */
    @Test
    void userWinsAtStart() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("ace", "clubs"));
        cards.add(new Card("ace", "hearts"));
        cards.add(new Card("ace", "spades"));
        cards.add(new Card("ten", "clubs"));
        int rounds = 1;
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        game.game(in, rounds);
        assertArrayEquals(new int[] {1}, game.results);
    }

    /**
     * The user scores 21 during the game.
     */
    @Test
    void userWins21() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("four", "spades"));
        cards.add(new Card("ace", "clubs"));
        cards.add(new Card("ace", "hearts"));
        cards.add(new Card("seven", "spades"));
        cards.add(new Card("ten", "clubs"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        int rounds = 1;
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        game.game(in, rounds);
        assertArrayEquals(new int[] {1}, game.results);
    }

    /**
     * Dealer scores 21 during the game.
     */
    @Test
    void dealerWins21() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("six", "spades"));
        cards.add(new Card("two", "hearts"));
        cards.add(new Card("ten", "clubs"));
        cards.add(new Card("five", "hearts"));
        cards.add(new Card("ace", "spades"));
        cards.add(new Card("two", "clubs"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        int rounds = 1;
        String input = "1 0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        game.game(in, rounds);
        assertArrayEquals(new int[] {-1}, game.results);
    }

    /**
     * User wins by scoring higher than dealer.
     */
    @Test
    void userWinsEnd() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("ten", "hearts"));
        cards.add(new Card("five", "hearts"));
        cards.add(new Card("ace", "clubs"));
        cards.add(new Card("ace", "hearts"));
        cards.add(new Card("ace", "spades"));
        cards.add(new Card("nine", "clubs"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        int rounds = 1;
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        game.game(in, rounds);
        assertArrayEquals(new int[] {1}, game.results);
    }

    /**
     * Dealer wins by scoring higher than user.
     */
    @Test
    void dealerWinsEnd() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("ace", "clubs"));
        cards.add(new Card("nine", "hearts"));
        cards.add(new Card("ace", "spades"));
        cards.add(new Card("ace", "diamonds"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        int rounds = 1;
        game.game(in, rounds);
        assertArrayEquals(new int[] {-1}, game.results);
    }

    /**
     * Draw case.
     */
    @Test
    void draw() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("ten", "clubs"));
        cards.add(new Card("seven", "hearts"));
        cards.add(new Card("ten", "spades"));
        cards.add(new Card("seven", "diamonds"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        int rounds = 1;
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        game.game(in, rounds);
        assertArrayEquals(new int[] {0}, game.results);
    }

    /**
     * User scores above 21.
     */
    @Test
    void userLoss() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("jack", "clubs"));
        cards.add(new Card("ten", "clubs"));
        cards.add(new Card("seven", "hearts"));
        cards.add(new Card("ten", "spades"));
        cards.add(new Card("seven", "diamonds"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        int rounds = 1;
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        game.game(in, rounds);
        assertArrayEquals(new int[] {-1}, game.results);
    }

    /**
     * Dealer scores above 21.
     */
    @Test
    void dealerLoss() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card("jack", "clubs"));
        cards.add(new Card("ten", "clubs"));
        cards.add(new Card("six", "hearts"));
        cards.add(new Card("ten", "spades"));
        cards.add(new Card("seven", "diamonds"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        int rounds = 1;
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        game.game(in, rounds);
        assertArrayEquals(new int[] {1}, game.results);
    }
}
