package sys.pro;

public class User extends Player{
    @Override
    public void start(Deck deck) {
        this.hand.add(deck.take());
        this.hand.add(deck.take());
        this.countScore();
    }

    @Override
    public void makeMove(Deck deck) {
        this.getCard(deck);
        System.out.println("Your hand: " + this.toString());
    }

    @Override
    public String toString() {
        this.countScore();
        return this.hand.toString() + " => " + this.score;
    }
}
