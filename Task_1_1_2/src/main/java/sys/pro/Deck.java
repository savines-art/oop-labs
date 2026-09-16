package sys.pro;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

/**
 * Deck class to implement Blackjack's deck.
 */
public class Deck {
    final int amount;
    Stack<Card> cards;

    /**
     * Default constructor, creating one deck and shuffling the stack of cards.
     */
    Deck() {
        this.cards = new Stack<>();
        this.amount = 1;
        String[] values = new String[]{"two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king", "ace"};
        String[] suits = new String[]{"hearts", "spades", "diamonds", "clubs"};

        for (String suit : suits) {
            for (String val : values) {
                cards.push(new Card(val, suit));
            }
        }

        Collections.shuffle(this.cards);
    }

    /**
     * Construct for multiple decks.
     * @param amount how many decks do you want to have.
     */
    Deck(int amount) {
        this.cards = new Stack<>();
        this.amount = amount;

        String[] values = new String[]{"two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king", "ace"};
        String[] suits = new String[]{"hearts", "spades", "diamonds", "clubs"};
        for (int i = 0; i < this.amount; i++) {
            for (String suit : suits) {
                for (String val : values) {
                    cards.push(new Card(val, suit));
                }
            }
        }

        Collections.shuffle(this.cards);
    }

    /**
     * Constructor for custom decks.
     * @param cards just list of cards to be turned into stack.
     */
    Deck(List<Card> cards) {
        this.amount = 1;
        this.cards = new Stack<>();
        this.cards.addAll(cards);
    }

    /**
     * Getting the card from the top of the deck.
     * @return taken card.
     */
    public Card take() {
        if (this.cards.empty()) {
            return null;
        }
        return this.cards.pop();
    }

    /**
     * Checking if the deck is empty.
     * @return true if there is no cards left.
     */
    public boolean isEmpty() {
        return this.cards.empty();
    }
}
