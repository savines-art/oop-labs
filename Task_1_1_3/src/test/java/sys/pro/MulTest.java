package sys.pro;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Tests for {@link Mul}.
 */
public class MulTest {
    /**
     * Tests {@link Mul#toString()} for a number times a variable.
     */
    @Test
    void testToString() {
        Expression e = new Mul(new Number(2), new Variable("x"));
        assertEquals("2 * x", e.toString());
    }

    /**
     * Tests evaluation of a constant multiplication.
     */
    @Test
    void testEval() {
        Expression e = new Mul(new Number(3), new Number(4));
        assertEquals(12, e.eval());
    }

    /**
     * Tests evaluation of a multiplication with variable bindings.
     */
    @Test
    void testEvalWithVariables() {
        Expression e = new Mul(new Variable("x"), new Variable("y"));
        assertEquals(12, e.eval("x = 3; y = 4"));
    }

    /**
     * Tests equality for {@link Mul} expressions.
     */
    @Test
    void testEquals() {
        Expression e1 = new Mul(new Number(2), new Variable("x"));
        assertNotEquals(e1, null);
        Expression e2 = new Mul(new Number(2), new Variable("x"));
        assertEquals(e1, e2);
        Expression e3 = new Mul(new Number(3), new Variable("x"));
        assertNotEquals(e1, e3);
        Expression e4 = new Mul(new Variable("x"), new Number(2));
        assertNotEquals(e1, e4);
    }

    /**
     * Tests differentiation of multiplication.
     */
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

    /**
     * Tests simplification when the left factor is zero.
     */
    @Test
    void testSimplifyZeroLeft() {
        Expression e = new Mul(new Number(0), new Variable("x"));
        assertEquals(new Number(0), e.simplify());
    }

    /**
     * Tests simplification when the left factor is one.
     */
    @Test
    void testSimplifyOneLeft() {
        Expression e = new Mul(new Number(1), new Variable("x"));
        assertEquals(new Variable("x"), e.simplify());
    }

    /**
     * Tests simplification when the right factor is zero.
     */
    @Test
    void testSimplifyZeroRight() {
        Expression e = new Mul(new Variable("x"), new Number(0));
        assertEquals(new Number(0), e.simplify());
    }

    /**
     * Tests simplification when the right factor is one.
     */
    @Test
    void testSimplifyOneRight() {
        Expression e = new Mul(new Variable("x"), new Number(1));
        assertEquals(new Variable("x"), e.simplify());
    }

    /**
     * Tests simplification of a constant multiplication.
     */
    @Test
    void testSimplifyConstants() {
        Expression e = new Mul(new Number(2), new Number(3));
        assertEquals(new Number(6), e.simplify());
    }

    /**
     * Tests simplification when both factors are variables.
     */
    @Test
    void testSimplifyVariables() {
        Expression e = new Mul(new Variable("x"), new Variable("y"));
        assertEquals(new Mul(new Variable("x"), new Variable("y")), e.simplify());
    }

    /**
     * Tests simplification when one factor is a variable and the other is a constant.
     */
    @Test
    void testSimplifyMixed() {
        Expression e = new Mul(new Variable("x"), new Number(2));
        assertEquals(new Mul(new Variable("x"), new Number(2)), e.simplify());
    }
}
