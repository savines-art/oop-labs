package sys.pro;

import java.util.List;
import java.util.ArrayList;

/**
 * Class for any type player. Contains the score player got and their hand.
 */
public abstract class Player {
    public List<Card> hand = new ArrayList<>();

    /**
     * Abstract method for starting the round for both players.
     * @param deck game's deck, which players take two cards from.
     */
    public abstract void start(Deck deck);

    /**
     * Abstract method to print hands.
     * @return returns the hand as all card names and total score.
     */
    public abstract String toString();

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
    public int countScore() {
        int score = 0;
        for (Card card : this.hand) {
            score += card.score();
        }
        if (score > 21) {
            for (Card card : this.hand) {
                if (card.value == CardName.ACE) {
                    score -= 10;
                }
            }
        }
        return score;
    }
}
