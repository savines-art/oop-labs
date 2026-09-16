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
    public int[] results;
    Scanner playerMoves;
    int rounds;

    /**
     * Constructor: initializes game deck and players.
     * @param deck the deck used during the game.
     */
    Blackjack(Deck deck) {
        this.deck = deck;
        this.dealer = new Dealer();
        this.user = new User();
    }


    /**
     * Start of the game.
     * @param playerMoves player's input.
     * @param rounds how many rounds are you going to play.
     */
    public void start(InputStream playerMoves, int rounds) {
        this.playerMoves = new Scanner(playerMoves);
        this.results = new int[rounds];
        this.rounds = rounds;
        System.out.println("Welcome to blackjack!");
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
            this.results[number] = 1;
            return;
        } else if (playerRes == -1) {
            System.out.println("Dealer wins the round!");
            this.results[number] = -1;
            return;
        }

        int dealerRes = dealerTurn();
        if (dealerRes == 1) {
            System.out.println("Dealer wins the round!");
            this.results[number] = -1;
            return;
        } else if (dealerRes == -1) {
            System.out.println("You win the round!");
            this.results[number] = 1;
            return;
        }
        if (this.user.score > this.dealer.score) {
            System.out.println("You win the round!");
            this.results[number] = 1;
            return;
        } else if (this.user.score < this.dealer.score) {
            System.out.println("Dealer wins the round!");
            this.results[number] = -1;
            return;
        }
        System.out.println("Draw!");
        this.results[number] = 0;
    }

    /**
     * Checking if player scored 21.
     * @param player player or dealer.
     * @return true if player scored 21, false otherwise.
     */
    private static boolean win(Player player) {
        return player.score == 21;
    }

    /**
     * Checking if player scored above 21.
     * @param player player or dealer.
     * @return true if player scored above 21, false otherwise.
     */
    private static boolean lose(Player player) {
        return player.score > 21;
    }


    /**
     * turn of the player.
     * @return 1, -1 or 0 if player won, lost or scored less than 21.
     */
    private int userTurn() {
        System.out.println("Your turn: enter 1 to take card or 0 to stop: ");
        this.user.countScore();
        if (win(this.user)) {
            return 1;
        }
        while (this.playerMoves.hasNextInt() && this.playerMoves.nextInt() == 1) {
            this.user.makeMove(this.deck);
            if (win(this.user)) {
                return 1;
            } else if (lose(this.user)) {
                return -1;
            }
            if (this.deck.isEmpty()) {
                System.out.println("The deck is empty!");
                return 0;
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
        this.dealer.makeMove(this.deck);
        if (win(this.dealer)) {
            return 1;
        } else if (lose(this.dealer)) {
            return -1;
        }
        System.out.println("Dealer finished their turn.");
        System.out.println("Your hand: " + this.user.toString());
        System.out.println("Dealer's hand: " + this.dealer.toString());
        return 0;
    }

    /**
     * Plays the game round by round, clearing hands after each round.
     * @param playerMoves player's input.
     * @param rounds amount of rounds.
     */
    public void game(InputStream playerMoves, int rounds) {
        start(playerMoves, rounds);
        for (int i = 0; i < this.rounds; i++) {
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
        Blackjack game = new Blackjack(new Deck(number));
        game.game(System.in, 5);
    }
}
