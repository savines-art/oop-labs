package sys.pro;

import org.junit.jupiter.api.Test;

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
        Expression e = Parser.parse("42");
        assertEquals(new Number(42), e);
    }

    /**
     * Tests parsing a variable.
     */
    @Test
    void testParseVariable() {
        Expression e = Parser.parse("x");
        assertEquals(new Variable("x"), e);
    }

    /**
     * Tests parsing an addition expression.
     */
    @Test
    void testParseAddition() {
        Expression e = Parser.parse("1 + 2");
        assertEquals(new Add(new Number(1), new Number(2)), e);
    }

    /**
     * Tests parsing a subtraction expression.
     */
    @Test
    void testParseSubtraction() {
        Expression e = Parser.parse("5 - 3");
        assertEquals(new Sub(new Number(5), new Number(3)), e);
    }

    /**
     * Tests parsing a multiplication expression.
     */
    @Test
    void testParseMultiplication() {
        Expression e = Parser.parse("2 * 3");
        assertEquals(new Mul(new Number(2), new Number(3)), e);
    }

    /**
     * Tests parsing a division expression.
     */
    @Test
    void testParseDivision() {
        Expression e = Parser.parse("6 / 2");
        assertEquals(new Div(new Number(6), new Number(2)), e);
    }

    /**
     * Tests that multiplication has higher precedence than addition.
     */
    @Test
    void testParseOrderOfOperations() {
        Expression e = Parser.parse("1 + 2 * 3");
        assertEquals(new Add(new Number(1), new Mul(new Number(2), new Number(3))), e);
    }

    /**
     * Tests parsing parentheses around an addition.
     */
    @Test
    void testParseParentheses() {
        Expression e = Parser.parse("(1 + 2) * 3");
        assertEquals(new Mul(new Parenthed(new Add(new Number(1), new Number(2))), new Number(3)), e);
    }

    /**
     * Tests parsing nested parentheses.
     */
    @Test
    void testParseDoubleParentheses() {
        Expression e = Parser.parse("((x + 2))");
        assertEquals(new Parenthed(new Parenthed(new Add(new Variable("x"), new Number(2)))), e);
    }

    /**
     * Tests parsing with extra whitespace.
     */
    @Test
    void testParseExtraSpaces() {
        Expression e = Parser.parse("  1   +   2 * 3  ");
        assertEquals(new Add(new Number(1), new Mul(new Number(2), new Number(3))), e);
    }

    /**
     * Tests left associativity of subtraction.
     */
    @Test
    void testParseLeftAssociativity() {
        Expression e = Parser.parse("x - y - z");
        assertEquals(new Sub(new Sub(new Variable("x"), new Variable("y")), new Variable("z")), e);
    }

    /**
     * Tests parsing a mixed-precedence expression.
     */
    @Test
    void testParseMixed() {
        Expression e = Parser.parse("x + y * z - w / v");
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
        Expression e = Parser.parse("(x + y) * (z - w)");
        Expression expected = new Mul(
                new Parenthed(new Add(new Variable("x"), new Variable("y"))),
                new Parenthed(new Sub(new Variable("z"), new Variable("w")))
        );
        assertEquals(expected, e);
    }
}
