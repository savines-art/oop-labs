package sys.pro;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Tests for {@link Sub}.
 */
public class SubTest {
    /**
     * Tests {@link Sub#toString()} for a number minus a variable.
     */
    @Test
    void testToString() {
        Expression e = new Sub(new Number(1), new Variable("x"));
        assertEquals("1 - x", e.toString());
    }

    /**
     * Tests evaluation of a constant subtraction.
     */
    @Test
    void testEval() {
        Expression e = new Sub(new Number(5), new Number(2));
        assertEquals(3, e.eval());
    }

    /**
     * Tests evaluation of a subtraction with variable bindings.
     */
    @Test
    void testEvalWithVariables() {
        Expression e = new Sub(new Variable("x"), new Variable("y"));
        assertEquals(3, e.eval("x = 5; y = 2"));
    }

    /**
     * Tests equality for {@link Sub} expressions.
     */
    @Test
    void testEquals() {
        Expression e1 = new Sub(new Number(1), new Variable("x"));
        assertNotEquals(e1, null);
        Expression e2 = new Sub(new Number(1), new Variable("x"));
        assertEquals(e1, e2);
        Expression e3 = new Sub(new Number(2), new Variable("x"));
        assertNotEquals(e1, e3);
        Expression e4 = new Sub(new Variable("x"), new Number(1));
        assertNotEquals(e1, e4);
    }

    /**
     * Tests differentiation of subtraction.
     */
    @Test
    void testDerivative() {
        Expression e = new Sub(new Variable("x"), new Number(2));
        Expression d = e.derivative("x");
        Expression expected = new Sub(new Number(1), new Number(0));
        assertEquals(expected, d);
    }

    /**
     * Tests simplification when both operands are the same expression.
     */
    @Test
    void testSimplifySame() {
        Expression e = new Sub(new Variable("x"), new Variable("x"));
        assertEquals(new Number(0), e.simplify());
    }

    /**
     * Tests simplification of a constant subtraction.
     */
    @Test
    void testSimplifyConstants() {
        Expression e = new Sub(new Number(5), new Number(2));
        assertEquals(new Number(3), e.simplify());
    }

    /**
     * Tests simplification when both operands are variables.
     */
    @Test
    void testSimplifyVariables() {
        Expression e = new Sub(new Variable("x"), new Variable("y"));
        assertEquals(new Sub(new Variable("x"), new Variable("y")), e.simplify());
    }

    /**
     * Tests simplification when one operand is a variable and the other is a constant.
     */
    @Test
    void testSimplifyMixed() {
        Expression e = new Sub(new Variable("x"), new Number(2));
        assertEquals(new Sub(new Variable("x"), new Number(2)), e.simplify());
    }
}
