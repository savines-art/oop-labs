package sys.pro;

/**
 * Enum for a card suits.
 */
public enum CardSuit {
    HEARTS("hearts"), SPADES("spades"), DIAMONDS("diamonds"), CLUBS("clubs");

    public final String name;

    private CardSuit(String suit) {
        this.name = suit;
    }
}
