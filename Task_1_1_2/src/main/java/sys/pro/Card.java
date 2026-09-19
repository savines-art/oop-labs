package sys.pro;

/**
 * Card class.
 */
public class Card {
    //todo: make enums for names-vals, suits
    public final CardName value;
    public final CardSuit suit;
    boolean isHidden;

    /**
     * Constructor: gives the card its name, suit and score, based on card's name.
     * @param value name of the card.
     * @param suit suit of the card.
     */
    Card(CardName value, CardSuit suit) {
        this.value = value;
        this.suit = suit;
        this.isHidden = false;
    }

    public String name() {
        return this.value.name;
    }

    public String suit() {
        return this.suit.name;
    }

    public int score() {
        return this.value.score;
    }

    /**
     * Hide or open the card (for dealer).
     */
    public void flip() {
        this.isHidden = !this.isHidden;
    }

    /**
     * Method to print card as its suit, name and score.
     * @return "*hidden*" if card is hidden or cards suit, name and score in parentheses.
     */
    public String toString() {
        if (this.isHidden) {
            return "*hidden*";
        }
        return this.suit() + " " + this.name();
    }
}
