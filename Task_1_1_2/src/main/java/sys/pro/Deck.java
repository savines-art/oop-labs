package sys.pro;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

/**
 * Deck class to implement Blackjack's deck.
 */
public class Deck {
    private final int amount;
    public Stack<Card> cards;

    /**
     * Default constructor, creating one deck and shuffling the stack of cards.
     */
    Deck() {
        this.cards = new Stack<>();
        this.amount = 1;

        this.fillDeck();

        Collections.shuffle(this.cards);
    }

    /**
     * Construct for multiple decks.
     * @param amount how many decks do you want to have.
     */
    Deck(int amount) {
        this.cards = new Stack<>();
        this.amount = amount;

        for (int i = 0; i < this.amount; i++) {
            this.fillDeck();
        }

        Collections.shuffle(this.cards);
    }

    private void fillDeck() {
        for (CardName name : CardName.values()) {
            for (CardSuit suit : CardSuit.values()) {
                this.cards.push(new Card(name, suit));
            }
        }
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
        if (this.isEmpty()) {
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
