package sys.pro;

public enum Mark {
    THREE(3), FOUR(4), FIVE(5),
    CREDIT(0), NO_CREDIT(0);

    public final int value;

    private Mark(int value) {
        this.value = value;
    }

    public int getValue() {
        if (this == CREDIT || this == NO_CREDIT) {
            throw new IllegalArgumentException("Cannot calculate a value of credit mark.");
        }
        return this.value;
    }

    public boolean isGood() {
        return this == CREDIT || this == FOUR || this == FIVE;
    }

    public boolean isExcellent() {
        return this == CREDIT || this == FIVE;
    }
}
