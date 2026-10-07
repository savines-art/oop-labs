package sys.pro;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MulTest {
    @Test
    void testToString() {
        Expression e = new Mul(new Number(2), new Variable("x"));
        assertEquals("2 * x", e.toString());
    }

    @Test
    void testEval() {
        Expression e = new Mul(new Number(3), new Number(4));
        assertEquals(12, e.eval());
    }

    @Test
    void testEvalWithVariables() {
        Expression e = new Mul(new Variable("x"), new Variable("y"));
        assertEquals(12, e.eval("x = 3; y = 4"));
    }

    @Test
    void testEquals() {
        Expression e1 = new Mul(new Number(2), new Variable("x"));
        Expression e2 = new Mul(new Number(2), new Variable("x"));
        Expression e3 = new Mul(new Number(3), new Variable("x"));
        Expression e4 = new Mul(new Variable("x"), new Number(2));
        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
        assertNotEquals(e1, null);
        assertNotEquals(e1, e4);
    }

    @Test
    void testDerivative() {
        Expression e = new Mul(new Variable("x"), new Variable("y"));
        Expression d = e.derivative("x");
        Expression expected = new Add(
                new Mul(new Number(1), new Variable("y")),
                new Mul(new Variable("x"), new Number(0))
        );
        assertEquals(expected, d);
    }

    @Test
    void testSimplifyZeroLeft() {
        Expression e = new Mul(new Number(0), new Variable("x"));
        assertEquals(new Number(0), e.simplify());
    }

    @Test
    void testSimplifyOneLeft() {
        Expression e = new Mul(new Number(1), new Variable("x"));
        assertEquals(new Variable("x"), e.simplify());
    }

    @Test
    void testSimplifyZeroRight() {
        Expression e = new Mul(new Variable("x"), new Number(0));
        assertEquals(new Number(0), e.simplify());
    }

    @Test
    void testSimplifyOneRight() {
        Expression e = new Mul(new Variable("x"), new Number(1));
        assertEquals(new Variable("x"), e.simplify());
    }

    @Test
    void testSimplifyConstants() {
        Expression e = new Mul(new Number(2), new Number(3));
        assertEquals(new Number(6), e.simplify());
    }

    @Test
    void testSimplifyVariables() {
        Expression e = new Mul(new Variable("x"), new Variable("y"));
        assertEquals(new Mul(new Variable("x"), new Variable("y")), e.simplify());
    }

    @Test
    void testSimplifyMixed() {
        Expression e = new Mul(new Variable("x"), new Number(2));
        assertEquals(new Mul(new Variable("x"), new Number(2)), e.simplify());
    }
}
