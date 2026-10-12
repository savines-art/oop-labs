package sys.pro;

import java.util.Scanner;

/**
 * Main class for demo.
 * accepts user's input and tries
 * to evaluate an expression if it has no variables.
 */
public class Main {
    /**
     * main method
     * accepts user's input and tries
     * to evaluate an expression if it has no variables.
     * @param args accepts command line args (there's none).
     */
    public static void main(String[] args) {
        Expression expr = Parser.parse(new Scanner(System.in));
        try {
            if (expr != null) {
                expr = expr.simplify();
                System.out.println(expr.eval());
            } else {
                System.out.println("It is an empty expression!");
            }
        } catch (IllegalArgumentException gotVar) {
            System.out.println("It has variables!");
        }
    }
}
