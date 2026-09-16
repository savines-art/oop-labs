package sys.pro;

import java.util.List;
import java.util.ArrayList;

abstract public class Player {
    List<Card> hand = new ArrayList<>();
    int score = 0;

    public void start(Deck deck){}
    public void makeMove(Deck deck){}
    public String toString(){ return ""; }

    public void getCard(Deck deck) {
        Card card = deck.take();
        if (card == null) {
            return;
        }
        this.hand.add(card);
    }


    public void countScore() {
        this.score = 0;
        for (Card card : this.hand) {
            this.score += card.value;
        }
        if (this.score > 21) {
            for (Card card : this.hand) {
                if (card.value == 11) {
                    card.value = 1;
                    this.score -= 10;
                }
            }
        }
    }
}
