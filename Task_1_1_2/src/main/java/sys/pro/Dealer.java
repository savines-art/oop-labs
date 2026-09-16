package sys.pro;

import java.util.List;
import java.util.ArrayList;

public class Dealer extends Player{
    @Override
    public void start(Deck deck) {
        this.hand.add(deck.take());
        this.hand.add(deck.take());
        this.hand.getLast().flip();
        this.countScore();
    }

    @Override
    public void makeMove(Deck deck) {
        this.hand.get(this.hand.size() - 1).flip();
        while(this.score < 17) {
            this.getCard(deck);
            System.out.print("Dealer's hand: " + this.toString());
            if (deck.isEmpty()) {
                return;
            }
        }
    }

    @Override
    public String toString() {
        this.countScore();
        Card last = this.hand.get(this.hand.size() - 1);
        if (!last.isHidden) {
            return this.hand.toString() + " => " + this.score;
        }
        return this.hand.toString();
    }
}
