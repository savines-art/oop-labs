package sys.pro;

public class Card {
    final String name;
    final String suit;
    int value;
    boolean isHidden;

    Card(String name, String suit) {
        this.name = name;
        this.suit = suit;
        this.isHidden = false;

        switch (this.name) {
            case "two": this.value = 2; break;
            case "three": this.value = 3; break;
            case "four": this.value = 4; break;
            case "five": this.value = 5; break;
            case "six": this.value = 6; break;
            case "seven": this.value = 7; break;
            case "eight": this.value = 8; break;
            case "nine": this.value = 9; break;
            case "ace": this.value = 11; break;
            default: this.value = 10;
        }
    }

    public void flip() {
        this.isHidden = !this.isHidden;
    }

    public String toString() {
        if (this.isHidden) {
            return "*hidden*";
        }

        return this.suit + " " + this.name + "(" + this.value + ")";
    }
}
