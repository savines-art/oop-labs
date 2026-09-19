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
        String input = "1 1 0";

        cards.add(new Card(CardName.TEN, CardSuit.CLUBS));
        cards.add(new Card(CardName.NINE, CardSuit.SPADES));
        cards.add(new Card(CardName.TEN, CardSuit.HEARTS));
        cards.add(new Card(CardName.EIGHT, CardSuit.SPADES));
        cards.add(new Card(CardName.TEN, CardSuit.SPADES));
        cards.add(new Card(CardName.FIVE, CardSuit.HEARTS));
        cards.add(new Card(CardName.NINE, CardSuit.HEARTS));
        cards.add(new Card(CardName.ACE, CardSuit.DIAMONDS));
        cards.add(new Card(CardName.ACE, CardSuit.CLUBS));
        cards.add(new Card(CardName.ACE, CardSuit.HEARTS));
        cards.add(new Card(CardName.ACE, CardSuit.SPADES));
        cards.add(new Card(CardName.NINE, CardSuit.CLUBS));
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck, in, rounds);
        game.game();
        assertArrayEquals(new GameCase[] {GameCase.USER_SCORED, GameCase.DEALER_SCORED, GameCase.DRAW, GameCase.DRAW, GameCase.DRAW}, game.results);
    }

    @Test
    void exhaustDeckByPlayer() {
        List<Card> cards = new ArrayList<>();
        int rounds = 5;
        String input = "1 1 0 1 1";

        cards.add(new Card(CardName.TWO, CardSuit.HEARTS));
        cards.add(new Card(CardName.TEN, CardSuit.CLUBS));
        cards.add(new Card(CardName.NINE, CardSuit.SPADES));
        cards.add(new Card(CardName.TEN, CardSuit.HEARTS));
        cards.add(new Card(CardName.EIGHT, CardSuit.SPADES));
        cards.add(new Card(CardName.TEN, CardSuit.SPADES));
        cards.add(new Card(CardName.FIVE, CardSuit.HEARTS));
        cards.add(new Card(CardName.NINE, CardSuit.HEARTS));
        cards.add(new Card(CardName.ACE, CardSuit.DIAMONDS));
        cards.add(new Card(CardName.ACE, CardSuit.CLUBS));
        cards.add(new Card(CardName.ACE, CardSuit.HEARTS));
        cards.add(new Card(CardName.ACE, CardSuit.SPADES));
        cards.add(new Card(CardName.NINE, CardSuit.CLUBS));
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck, in, rounds);
        game.game();
        assertArrayEquals(new GameCase[] {GameCase.USER_SCORED, GameCase.USER_SCORED, GameCase.DRAW, GameCase.DRAW, GameCase.DRAW}, game.results);
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
        Blackjack game = new Blackjack(deck, in, rounds);
        game.game();
        assertArrayEquals(new GameCase[] {GameCase.DRAW, GameCase.DRAW, GameCase.DRAW, GameCase.DRAW, GameCase.DRAW}, game.results);
    }
    /**
     * When user scores 21 in the beginning.
     */
    @Test
    void userWinsAtStart() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card(CardName.ACE, CardSuit.CLUBS));
        cards.add(new Card(CardName.ACE, CardSuit.HEARTS));
        cards.add(new Card(CardName.ACE, CardSuit.SPADES));
        cards.add(new Card(CardName.TEN, CardSuit.CLUBS));
        int rounds = 1;
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck, in, rounds);
        game.game();
        assertArrayEquals(new GameCase[] {GameCase.USER_BLACKJACK}, game.results);
    }

    /**
     * The user scores 21 during the game.
     */
    @Test
    void userWins21() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card(CardName.FOUR, CardSuit.SPADES));
        cards.add(new Card(CardName.ACE, CardSuit.CLUBS));
        cards.add(new Card(CardName.ACE, CardSuit.HEARTS));
        cards.add(new Card(CardName.SEVEN, CardSuit.SPADES));
        cards.add(new Card(CardName.TEN, CardSuit.CLUBS));

        Deck deck = new Deck(cards);

        int rounds = 1;
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Blackjack game = new Blackjack(deck, in, rounds);
        game.game();
        assertArrayEquals(new GameCase[] {GameCase.USER_BLACKJACK}, game.results);
    }

    /**
     * Dealer scores 21 during the game.
     */
    @Test
    void dealerWins21() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card(CardName.SIX, CardSuit.SPADES));
        cards.add(new Card(CardName.TWO, CardSuit.HEARTS));
        cards.add(new Card(CardName.TEN, CardSuit.CLUBS));
        cards.add(new Card(CardName.FIVE, CardSuit.HEARTS));
        cards.add(new Card(CardName.ACE, CardSuit.SPADES));
        cards.add(new Card(CardName.TWO, CardSuit.CLUBS));

        Deck deck = new Deck(cards);

        int rounds = 1;
        String input = "1 0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Blackjack game = new Blackjack(deck, in, rounds);
        game.game();
        assertArrayEquals(new GameCase[] {GameCase.DEALER_BLACKJACK}, game.results);
    }

    /**
     * User wins by scoring higher than dealer.
     */
    @Test
    void userWinsEnd() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card(CardName.TEN, CardSuit.HEARTS));
        cards.add(new Card(CardName.FIVE, CardSuit.HEARTS));
        cards.add(new Card(CardName.ACE, CardSuit.CLUBS));
        cards.add(new Card(CardName.ACE, CardSuit.HEARTS));
        cards.add(new Card(CardName.ACE, CardSuit.SPADES));
        cards.add(new Card(CardName.NINE, CardSuit.CLUBS));

        Deck deck = new Deck(cards);

        int rounds = 1;
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Blackjack game = new Blackjack(deck, in, rounds);
        game.game();
        assertArrayEquals(new GameCase[] {GameCase.USER_SCORED}, game.results);
    }

    /**
     * Dealer wins by scoring higher than user.
     */
    @Test
    void dealerWinsEnd() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card(CardName.ACE, CardSuit.CLUBS));
        cards.add(new Card(CardName.NINE, CardSuit.HEARTS));
        cards.add(new Card(CardName.ACE, CardSuit.SPADES));
        cards.add(new Card(CardName.ACE, CardSuit.DIAMONDS));

        Deck deck = new Deck(cards);

        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        int rounds = 1;
        Blackjack game = new Blackjack(deck, in, rounds);
        game.game();
        assertArrayEquals(new GameCase[] {GameCase.DEALER_SCORED}, game.results);
    }

    /**
     * Draw case.
     */
    @Test
    void draw() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card(CardName.TEN, CardSuit.CLUBS));
        cards.add(new Card(CardName.SEVEN, CardSuit.HEARTS));
        cards.add(new Card(CardName.TEN, CardSuit.SPADES));
        cards.add(new Card(CardName.SEVEN, CardSuit.DIAMONDS));

        Deck deck = new Deck(cards);

        int rounds = 1;
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Blackjack game = new Blackjack(deck, in, rounds);
        game.game();
        assertArrayEquals(new GameCase[] {GameCase.DRAW}, game.results);
    }

    /**
     * User scores above 21.
     */
    @Test
    void userLoss() {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card(CardName.JACK, CardSuit.CLUBS));
        cards.add(new Card(CardName.TEN, CardSuit.CLUBS));
        cards.add(new Card(CardName.SEVEN, CardSuit.HEARTS));
        cards.add(new Card(CardName.TEN, CardSuit.SPADES));
        cards.add(new Card(CardName.SEVEN, CardSuit.DIAMONDS));

        Deck deck = new Deck(cards);

        int rounds = 1;
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Blackjack game = new Blackjack(deck, in, rounds);
        game.game();
        assertArrayEquals(new GameCase[] {GameCase.USER_OVERSCORED}, game.results);
    }

    /**
     * Dealer scores above 21.
     */
    @Test
    void dealerLoss() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardName.JACK, CardSuit.CLUBS));
        cards.add(new Card(CardName.TEN, CardSuit.CLUBS));
        cards.add(new Card(CardName.SIX, CardSuit.HEARTS));
        cards.add(new Card(CardName.TEN, CardSuit.SPADES));
        cards.add(new Card(CardName.SEVEN, CardSuit.DIAMONDS));

        Deck deck = new Deck(cards);

        int rounds = 1;
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Blackjack game = new Blackjack(deck, in, rounds);
        game.game();
        assertArrayEquals(new GameCase[] {GameCase.DEALER_OVERSCORED}, game.results);
    }
}
