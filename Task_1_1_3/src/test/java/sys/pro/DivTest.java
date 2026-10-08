package sys.pro;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Tests for {@link Div}.
 */
public class DivTest {
    /**
     * Tests {@link Div#toString()} for a number divided by a variable.
     */
    @Test
    void testToString() {
        Expression e = new Div(new Number(6), new Variable("x"));
        assertEquals("6 / x", e.toString());
    }

    /**
     * Tests evaluation of a constant division.
     */
    @Test
    void testEval() {
        Expression e = new Div(new Number(6), new Number(2));
        assertEquals(3, e.eval());
    }

    /**
     * Tests evaluation of a division with variable bindings.
     */
    @Test
    void testEvalWithVariables() {
        Expression e = new Div(new Variable("x"), new Variable("y"));
        assertEquals(3, e.eval("x = 6; y = 2"));
    }

    /**
     * Tests equality for {@link Div} expressions.
     */
    @Test
    void testEquals() {
        Expression e1 = new Div(new Number(6), new Variable("x"));
        assertNotEquals(e1, null);
        Expression e2 = new Div(new Number(6), new Variable("x"));
        assertEquals(e1, e2);
        Expression e3 = new Div(new Number(6), new Variable("y"));
        assertNotEquals(e1, e3);
        Expression e4 = new Div(new Variable("x"), new Number(6));
        assertNotEquals(e1, e4);
    }

    /**
     * Tests differentiation of division using the quotient rule.
     */
    @Test
    void testDerivative() {
        Expression e = new Div(new Variable("x"), new Variable("y"));
        Expression d = e.derivative("x");
        Expression expected = new Div(
                new Sub(
                        new Mul(new Number(1), new Variable("y")),
                        new Mul(new Variable("x"), new Number(0))
                ),
                new Mul(new Variable("y"), new Variable("y"))
        );
        assertEquals(expected, d);
    }

    /**
     * Tests simplification when the divisor is one.
     */
    @Test
    void testSimplifyRightOne() {
        Expression e = new Div(new Variable("x"), new Number(1));
        assertEquals(new Variable("x"), e.simplify());
    }

    /**
     * Tests simplification of a constant division.
     */
    @Test
    void testSimplifyConstants() {
        Expression e = new Div(new Number(6), new Number(2));
        assertEquals(new Number(3), e.simplify());
    }

    /**
     * Tests simplification when both dividend and divisor are variables.
     */
    @Test
    void testSimplifyVariables() {
        Expression e = new Div(new Variable("x"), new Variable("y"));
        assertEquals(new Div(new Variable("x"), new Variable("y")), e.simplify());
    }

    /**
     * Tests simplification when the dividend is a variable and the divisor is a constant.
     */
    @Test
    void testSimplifyMixed() {
        Expression e = new Div(new Variable("x"), new Number(2));
        assertEquals(new Div(new Variable("x"), new Number(2)), e.simplify());
    }
}
