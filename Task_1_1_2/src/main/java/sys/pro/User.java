package sys.pro;

/**
 * User player class.
 */
public class User extends Player{
    /**
     * How user starts every round.
     * @param deck game's deck.
     */
    @Override
    public void start(Deck deck) {
        this.hand.add(deck.take());
        this.hand.add(deck.take());
        this.countScore();
    }

    /**
     * One iteration of user's move.
     * @param deck game's deck.
     */
    @Override
    public void makeMove(Deck deck) {
        this.getCard(deck);
        System.out.println("Your hand: " + this.toString());
    }

    /**
     * Returns user's hand with scores of all cards and their score overall.
     * @return user's hand and score.
     */
    @Override
    public String toString() {
        this.countScore();
        return this.hand.toString() + " => " + this.score;
    }
}
