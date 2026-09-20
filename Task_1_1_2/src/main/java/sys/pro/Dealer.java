package sys.pro;

import java.util.List;
import java.util.ArrayList;

/**
 * Dealer player class.
 */
public class Dealer extends Player {
    /**
     * How dealer starts the round (hides the card in the end).
     * @param deck game's deck.
     */
    @Override
    public void start(Deck deck) {
        this.getCard(deck);
        this.getCard(deck);
        this.hand.getLast().flip();
    }

    /**
     * Returns dealer's hand with hidden card or not.
     * @return dealer's hand and score (if last card is open).
     */
    @Override
    public String toString() {
        Card last = this.hand.getLast();
        if (!last.isHidden) {
            return this.hand.toString() + " => " + this.countScore();
        }
        return this.hand.toString();
    }
}
