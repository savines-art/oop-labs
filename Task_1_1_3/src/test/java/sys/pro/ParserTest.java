package sys.pro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    void testParseNumber() {
        Expression e = Parser.parse("42");
        assertEquals(new Number(42), e);
    }

    @Test
    void testParseVariable() {
        Expression e = Parser.parse("x");
        assertEquals(new Variable("x"), e);
    }

    @Test
    void testParseAddition() {
        Expression e = Parser.parse("1 + 2");
        assertEquals(new Add(new Number(1), new Number(2)), e);
    }

    @Test
    void testParseSubtraction() {
        Expression e = Parser.parse("5 - 3");
        assertEquals(new Sub(new Number(5), new Number(3)), e);
    }

    @Test
    void testParseMultiplication() {
        Expression e = Parser.parse("2 * 3");
        assertEquals(new Mul(new Number(2), new Number(3)), e);
    }

    @Test
    void testParseDivision() {
        Expression e = Parser.parse("6 / 2");
        assertEquals(new Div(new Number(6), new Number(2)), e);
    }

    @Test
    void testParseOrderOfOperations() {
        Expression e = Parser.parse("1 + 2 * 3");
        assertEquals(new Add(new Number(1), new Mul(new Number(2), new Number(3))), e);
    }

    @Test
    void testParseParentheses() {
        Expression e = Parser.parse("(1 + 2) * 3");
        assertEquals(new Mul(new Parenthed(new Add(new Number(1), new Number(2))), new Number(3)), e);
    }

    @Test
    void testParseDoubleParentheses() {
        Expression e = Parser.parse("((x + 2))");
        assertEquals(new Parenthed(new Parenthed(new Add(new Variable("x"), new Number(2)))), e);
    }

    @Test
    void testParseExtraSpaces() {
        Expression e = Parser.parse("  1   +   2 * 3  ");
        assertEquals(new Add(new Number(1), new Mul(new Number(2), new Number(3))), e);
    }

    @Test
    void testParseLeftAssociativity() {
        Expression e = Parser.parse("x - y - z");
        assertEquals(new Sub(new Sub(new Variable("x"), new Variable("y")), new Variable("z")), e);
    }

    @Test
    void testParseMixed() {
        Expression e = Parser.parse("x + y * z - w / v");
        Expression expected = new Sub(
                new Add(new Variable("x"), new Mul(new Variable("y"), new Variable("z"))),
                new Div(new Variable("w"), new Variable("v"))
        );
        assertEquals(expected, e);
    }

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
