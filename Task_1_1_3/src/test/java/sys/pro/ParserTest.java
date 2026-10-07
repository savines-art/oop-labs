package sys.pro;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link Parser}.
 */
public class ParserTest {
    /**
     * Tests parsing a numeric literal.
     */
    @Test
    void testParseNumber() {
        String input = "42";

        Expression e = Parser.parse(new Scanner(input));
        assertEquals(new Number(42), e);
    }

    /**
     * Tests parsing a variable.
     */
    @Test
    void testParseVariable() {
        String input = "x";
        Expression e = Parser.parse(new Scanner(input));
        assertEquals(new Variable("x"), e);
    }

    /**
     * Tests parsing an addition expression.
     */
    @Test
    void testParseAddition() {
        String input = "1 + 2";
        Expression e = Parser.parse(new Scanner(input));
        assertEquals(new Add(new Number(1), new Number(2)), e);
    }

    /**
     * Tests parsing a subtraction expression.
     */
    @Test
    void testParseSubtraction() {
        String input = "5 - 3";
        Expression e = Parser.parse(new Scanner(input));
        assertEquals(new Sub(new Number(5), new Number(3)), e);
    }

    /**
     * Tests parsing a multiplication expression.
     */
    @Test
    void testParseMultiplication() {
        String input = "2 * 3";
        Expression e = Parser.parse(new Scanner(input));
        assertEquals(new Mul(new Number(2), new Number(3)), e);
    }

    /**
     * Tests parsing a division expression.
     */
    @Test
    void testParseDivision() {
        String input = "6 / 2";
        Expression e = Parser.parse(new Scanner(input));
        assertEquals(new Div(new Number(6), new Number(2)), e);
    }

    /**
     * Tests that multiplication has higher precedence than addition.
     */
    @Test
    void testParseOrderOfOperations() {
        String input = "1 + 2 * 3";
        Expression e = Parser.parse(new Scanner(input));
        assertEquals(new Add(new Number(1), new Mul(new Number(2), new Number(3))), e);
    }

    /**
     * Tests parsing parentheses around an addition.
     */
    @Test
    void testParseParentheses() {
        String input = "(1 + 2) * 3";
        Expression e = Parser.parse(new Scanner(input));
        assertEquals(new Mul(new Parenthed(new Add(new Number(1),
                new Number(2))),
                new Number(3)), e);
    }

    /**
     * Tests parsing nested parentheses.
     */
    @Test
    void testParseDoubleParentheses() {
        String input = "((x + 2))";
        Expression e = Parser.parse(new Scanner(input));
        assertEquals(new Parenthed(new Parenthed(new Add(new Variable("x"), new Number(2)))), e);
    }

    /**
     * Tests parsing with extra whitespace.
     */
    @Test
    void testParseExtraSpaces() {
        String input = "  1   +   2 * 3  ";
        Expression e = Parser.parse(new Scanner(input));
        assertEquals(new Add(new Number(1), new Mul(new Number(2), new Number(3))), e);
    }

    /**
     * Tests left associativity of subtraction.
     */
    @Test
    void testParseLeftAssociativity() {
        String input = "x - y - z";
        Expression e = Parser.parse(new Scanner(input));
        assertEquals(new Sub(new Sub(new Variable("x"), new Variable("y")), new Variable("z")), e);
    }

    /**
     * Tests parsing a mixed-precedence expression.
     */
    @Test
    void testParseMixed() {
        String input = "x + y * z - w / v";
        Expression e = Parser.parse(new Scanner(input));
        Expression expected = new Sub(
                new Add(new Variable("x"), new Mul(new Variable("y"), new Variable("z"))),
                new Div(new Variable("w"), new Variable("v"))
        );
        assertEquals(expected, e);
    }

    /**
     * Tests parsing a complex expression with multiple parenthesized groups.
     */
    @Test
    void testParseComplexWithParentheses() {
        String input = "(x + y) * (z - w)";
        Expression e = Parser.parse(new Scanner(input));
        Expression expected = new Mul(
                new Parenthed(new Add(new Variable("x"), new Variable("y"))),
                new Parenthed(new Sub(new Variable("z"), new Variable("w")))
        );
        assertEquals(expected, e);
    }
}
