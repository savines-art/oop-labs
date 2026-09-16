package sys.pro;

import java.util.List;
import java.util.ArrayList;

/**
 * Dealer player class.
 */
public class Dealer extends Player{
    /**
     * How dealer starts the round (hides the card in the end).
     * @param deck game's deck.
     */
    @Override
    public void start(Deck deck) {
        this.hand.add(deck.take());
        this.hand.add(deck.take());
        this.hand.get(this.hand.size() - 1).flip();
        this.countScore();
    }

    /**
     * The whole dealer's turn. Stops if the deck is empty or scored above 17.
     * @param deck game's deck.
     */
    @Override
    public void makeMove(Deck deck) {
        this.hand.get(this.hand.size() - 1).flip();
        while(this.score < 17) {
            this.getCard(deck);
            System.out.print("Dealer's hand: " + this.toString());
            if (deck.isEmpty()) {
                return;
            }
        }
    }

    /**
     * Returns dealer's hand with hidden card or not.
     * @return dealer's hand and score (if last card is open).
     */
    @Override
    public String toString() {
        this.countScore();
        Card last = this.hand.get(this.hand.size() - 1);
        if (!last.isHidden) {
            return this.hand.toString() + " => " + this.score;
        }
        return this.hand.toString();
    }
}
