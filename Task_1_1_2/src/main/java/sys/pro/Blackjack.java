package sys.pro;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Blackjack game implementation.
 */
public class Blackjack {
    public Deck deck;
    public Dealer dealer;
    public User user;
    public GameCase[] results;
    Scanner playerMoves;
    int rounds;

    /**
     * Constructor: initializes game deck and players.
     * @param deck the deck used during the game.
     */
    Blackjack(Deck deck, InputStream playerMoves, int rounds) {
        this.deck = deck;
        this.dealer = new Dealer();
        this.user = new User();
        this.playerMoves = new Scanner(playerMoves);
        this.results = new GameCase[rounds];
        this.rounds = rounds;
    }

    /**
     * One game round.
     * @param number number of the current round.
     */
    private void round(int number) {
        System.out.println("Round " + number);
        System.out.println("Dealer gave cards.");
        this.user.start(this.deck);
        System.out.println("Your hand: " + this.user.toString());
        this.dealer.start(this.deck);
        System.out.println("Dealer's hand: " + this.dealer.toString());
        int playerRes = userTurn();
        if (playerRes == 1) {
            System.out.println("You win the round!");
            this.results[number] = GameCase.USER_BLACKJACK;
            return;
        } else if (playerRes == -1) {
            System.out.println("Dealer wins the round!");
            this.results[number] = GameCase.USER_OVERSCORED;
            return;
        }
        int dealerRes = dealerTurn();
        if (dealerRes == 1) {
            System.out.println("Dealer wins the round!");
            this.results[number] = GameCase.DEALER_BLACKJACK;
            return;
        } else if (dealerRes == -1) {
            System.out.println("You win the round!");
            this.results[number] = GameCase.DEALER_OVERSCORED;
            return;
        }
        if (this.user.countScore() > this.dealer.countScore()) {
            System.out.println("You win the round!");
            this.results[number] = GameCase.USER_SCORED;
            return;
        } else if (this.user.countScore() < this.dealer.countScore()) {
            System.out.println("Dealer wins the round!");
            this.results[number] = GameCase.DEALER_SCORED;
            return;
        }
        System.out.println("Draw!");
        this.results[number] = GameCase.DRAW;
    }

    /**
     * Checking if player scored 21.
     * @param player player or dealer.
     * @return true if player scored 21, false otherwise.
     */
    private static boolean winByBlackjack(Player player) {
        return player.countScore() == 21;
    }

    /**
     * Checking if player scored above 21.
     * @param player player or dealer.
     * @return true if player scored above 21, false otherwise.
     */
    private static boolean loseByOverscore(Player player) {
        return player.countScore() > 21;
    }


    /**
     * turn of the player.
     * @return 1, -1 or 0 if player won, lost or scored less than 21.
     */
    private int userTurn() {
        this.user.countScore();
        if (winByBlackjack(this.user)) {
            return 1;
        }
        while (true) {
            System.out.println("Your turn: enter 1 to take card or 0 to stop: ");
            if (!this.playerMoves.hasNextInt() || this.playerMoves.nextInt() == 0) {
                break;
            }
            this.user.getCard(this.deck);
            System.out.println("Your hand: " + this.user.toString());
            if (winByBlackjack(this.user)) {
                return 1;
            } else if (loseByOverscore(this.user)) {
                return -1;
            }
            if (this.deck.isEmpty()) {
                System.out.println("The deck is empty!");
                break;
            }
        }
        System.out.println("You finished your turn.");
        System.out.println("Your hand: " + this.user.toString());
        System.out.println("Dealer's hand: " + this.dealer.toString());
        return 0;
    }

    /**
     * Turn of the dealer.
     * @return 1, -1 or 0 if dealer won, lost or scored less than 21.
     */
    private int dealerTurn() {
        System.out.println("Dealer's turn");
        this.dealer.countScore();
        this.dealer.hand.getLast().flip();
        while (dealer.countScore() < 17 && !this.deck.isEmpty()) {
            this.dealer.getCard(this.deck);
            System.out.println("Dealer's hand: " + this.dealer.toString());
        }

        if (winByBlackjack(this.dealer)) {
            return 1;
        } else if (loseByOverscore(this.dealer)) {
            return -1;
        }
        System.out.println("Dealer finished their turn.");
        System.out.println("Your hand: " + this.user.toString());
        System.out.println("Dealer's hand: " + this.dealer.toString());
        return 0;
    }

    /**
     * Plays the game round by round, clearing hands after each round.
     */
    public void game() {
        System.out.println("Welcome to Blackjack!");
        for (int i = 0; i < this.rounds; i++) {
            if (this.deck.isEmpty()) {
                for (int j = i; j < this.rounds; j++) {
                    results[j] = GameCase.DRAW;
                }
                System.out.println("The deck is empty!");
                break;
            }
            round(i);
            this.user.hand = new ArrayList<>();
            this.dealer.hand = new ArrayList<>();
        }
    }

    /**
     * main method for playing the game yourself.
     * @param args no arguments there.
     */
    public static void main(String[] args) {
        int number = 1;
        Blackjack game = new Blackjack(new Deck(number), System.in, number);
        game.game();
    }
}
