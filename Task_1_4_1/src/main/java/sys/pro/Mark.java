package sys.pro;

/**
 * Represents a mark that can be received for a subject.
 */
public enum Mark {
    THREE(3), FOUR(4), FIVE(5),
    CREDIT(0), NO_CREDIT(0);

    /**
     * Numeric value of the mark.
     */
    public final int value;

    /**
     * Creates a mark with the given numeric value.
     *
     * @param value the numeric value of the mark
     */
    private Mark(int value) {
        this.value = value;
    }

    /**
     * Returns the numeric value of this mark.
     *
     * @return the numeric value of this mark
     * @throws IllegalArgumentException if this mark is {@link #CREDIT} or {@link #NO_CREDIT}
     */
    public int getValue() {
        if (this == CREDIT || this == NO_CREDIT) {
            throw new IllegalArgumentException("Cannot calculate a value of credit mark.");
        }
        return this.value;
    }

    /**
     * Checks whether this mark is considered good.
     *
     * @return {@code true} if this mark is {@link #CREDIT}, {@link #FOUR}, or {@link #FIVE};
     *         otherwise {@code false}
     */
    public boolean isGood() {
        return this == CREDIT || this == FOUR || this == FIVE;
    }

    /**
     * Checks whether this mark is considered excellent.
     *
     * @return {@code true} if this mark is {@link #CREDIT} or {@link #FIVE};
     *         otherwise {@code false}
     */
    public boolean isExcellent() {
        return this == CREDIT || this == FIVE;
    }
}
