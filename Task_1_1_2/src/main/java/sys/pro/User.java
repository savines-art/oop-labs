package sys.pro;

/**
 * User player class.
 */
public class User extends Player {
    /**
     * How user starts every round.
     * @param deck game's deck.
     */
    @Override
    public void start(Deck deck) {
        this.getCard(deck);
        this.getCard(deck);
    }

    /**
     * Returns user's hand with scores of all cards and their score overall.
     * @return user's hand and score.
     */
    @Override
    public String toString() {
        return this.hand.toString() + " => " + this.countScore();
    }
}
