package sys.pro;

import java.util.List;
import java.util.ArrayList;

/**
 * Class for any type player. Contains the score player got and their hand.
 */
abstract public class Player {
    List<Card> hand = new ArrayList<>();
    int score = 0;

    public void start(Deck deck){}
    public void makeMove(Deck deck){}
    public String toString(){ return ""; }

    /**
     * Take the card from the deck and place it in your hand.
     * @param deck deck you're taking card from.
     */
    public void getCard(Deck deck) {
        Card card = deck.take();
        if (card == null) {
            return;
        }
        this.hand.add(card);
    }


    /**
     * Counts the score and sets it (checking the aces too).
     */
    public void countScore() {
        this.score = 0;
        for (Card card : this.hand) {
            this.score += card.value;
        }
        if (this.score > 21) {
            for (Card card : this.hand) {
                if (card.value == 11) {
                    card.value = 1;
                    this.score -= 10;
                }
            }
        }
    }
}
