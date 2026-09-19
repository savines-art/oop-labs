package sys.pro;

/**
 * Enum for a pair of card names and values.
 */
public enum CardName {
    TWO("two", 2), THREE("three", 3), FOUR("four", 4), FIVE("five", 5),
    SIX("six", 6), SEVEN("seven", 7), EIGHT("eight", 8), NINE("nine", 9), TEN("ten", 10),
    JACK("jack", 10), QUEEN("queen", 10), KING("king", 10), ACE("ace", 11);

    public final String name;
    public final int score;

    private CardName(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
