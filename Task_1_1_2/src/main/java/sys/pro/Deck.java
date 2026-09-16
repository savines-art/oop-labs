package sys.pro;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

public class Deck {
    final int amount;
    Stack<Card> cards;

    Deck() {
        this.cards = new Stack<>();
        this.amount = 1;
        String[] values = new String[]{"two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king", "ace"};
        String[] suits = new String[]{"hearts", "spades", "diamonds", "clubs"};

        for (String suit : suits) {
            for (String val : values) {
                cards.push(new Card(val, suit));
            }
        }

        Collections.shuffle(this.cards);
    }

    Deck(int amount) {
        this.cards = new Stack<>();
        this.amount = amount;

        String[] values = new String[]{"two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king", "ace"};
        String[] suits = new String[]{"hearts", "spades", "diamonds", "clubs"};
        for (int i = 0; i < this.amount; i++) {
            for (String suit : suits) {
                for (String val : values) {
                    cards.push(new Card(val, suit));
                }
            }
        }

        Collections.shuffle(this.cards);
    }

    Deck(List<Card> cards) {
        this.amount = 1;
        this.cards = new Stack<>();
        this.cards.addAll(cards);
    }

    public Card take() {
        if (this.cards.empty()) {
            return null;
        }
        return this.cards.pop();
    }

    public boolean isEmpty() {
        return this.cards.empty();
    }
}
