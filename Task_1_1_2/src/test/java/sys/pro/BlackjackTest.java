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

public class BlackjackTest {
    @Test
    void userWinsAtStart() {
        int rounds = 1;
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("ace", "clubs"));
        cards.add(new Card("ace", "hearts"));
        cards.add(new Card("ace", "spades"));
        cards.add(new Card("ten", "clubs"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        game.game(in, rounds);
        assertArrayEquals(new int[] {1}, game.results);
    }

    @Test
    void userWins21() {
        int rounds = 1;
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("four", "spades"));
        cards.add(new Card("ace", "clubs"));
        cards.add(new Card("ace", "hearts"));
        cards.add(new Card("seven", "spades"));
        cards.add(new Card("ten", "clubs"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        game.game(in, rounds);
        assertArrayEquals(new int[] {1}, game.results);
    }

    @Test
    void dealerWins21() {
        int rounds = 1;
        String input = "1 0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("six", "spades"));
        cards.add(new Card("two", "hearts"));
        cards.add(new Card("ten", "clubs"));
        cards.add(new Card("five", "hearts"));
        cards.add(new Card("ace", "spades"));
        cards.add(new Card("two", "clubs"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        game.game(in, rounds);
        assertArrayEquals(new int[] {-1}, game.results);
    }

    @Test
    void userWinsEnd() {
        int rounds = 1;
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("ten", "hearts"));
        cards.add(new Card("five", "hearts"));
        cards.add(new Card("ace", "clubs"));
        cards.add(new Card("ace", "hearts"));
        cards.add(new Card("ace", "spades"));
        cards.add(new Card("nine", "clubs"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        game.game(in, rounds);
        assertArrayEquals(new int[] {1}, game.results);
    }

    @Test
    void dealerWinsEnd() {
        int rounds = 1;
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("ace", "clubs"));
        cards.add(new Card("nine", "hearts"));
        cards.add(new Card("ace", "spades"));
        cards.add(new Card("ace", "diamonds"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        game.game(in, rounds);
        assertArrayEquals(new int[] {-1}, game.results);
    }

    @Test
    void draw() {
        int rounds = 1;
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("ten", "clubs"));
        cards.add(new Card("seven", "hearts"));
        cards.add(new Card("ten", "spades"));
        cards.add(new Card("seven", "diamonds"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        game.game(in, rounds);
        assertArrayEquals(new int[] {0}, game.results);
    }

    @Test
    void userLoss() {
        int rounds = 1;
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("jack", "clubs"));
        cards.add(new Card("ten", "clubs"));
        cards.add(new Card("seven", "hearts"));
        cards.add(new Card("ten", "spades"));
        cards.add(new Card("seven", "diamonds"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        game.game(in, rounds);
        assertArrayEquals(new int[] {-1}, game.results);
    }

    @Test
    void dealerLoss() {
        int rounds = 1;
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        List<Card> cards = new ArrayList<>();

        cards.add(new Card("jack", "clubs"));
        cards.add(new Card("ten", "clubs"));
        cards.add(new Card("six", "hearts"));
        cards.add(new Card("ten", "spades"));
        cards.add(new Card("seven", "diamonds"));

        Deck deck = new Deck(cards);
        Blackjack game = new Blackjack(deck);
        game.game(in, rounds);
        assertArrayEquals(new int[] {1}, game.results);
    }
}
