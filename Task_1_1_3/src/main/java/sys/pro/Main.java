package sys.pro;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
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
