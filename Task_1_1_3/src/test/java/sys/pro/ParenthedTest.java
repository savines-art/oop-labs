package sys.pro;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Tests for {@link Parenthed}.
 */
public class ParenthedTest {
    /**
     * Tests {@link Parenthed#toString()} for a parenthesized addition.
     */
    @Test
    void testToString() {
        Expression e = new Parenthed(new Add(new Variable("x"), new Number(1)));
        assertEquals("(x + 1)", e.toString());
    }

    /**
     * Tests evaluation of a parenthesized constant expression.
     */
    @Test
    void testEval() {
        Expression e = new Parenthed(new Add(new Number(1), new Number(2)));
        assertEquals(3, e.eval());
    }

    /**
     * Tests evaluation of a parenthesized expression with variable bindings.
     */
    @Test
    void testEvalWithVariables() {
        Expression e = new Parenthed(new Add(new Variable("x"), new Number(2)));
        assertEquals(3, e.eval("x = 1"));
    }

    /**
     * Tests equality for {@link Parenthed} expressions.
     */
    @Test
    void testEquals() {
        Expression e1 = new Parenthed(new Add(new Number(1), new Variable("x")));
        assertNotEquals(e1, null);
        Expression e2 = new Parenthed(new Add(new Number(1), new Variable("x")));
        assertEquals(e1, e2);
        Expression e3 = new Parenthed(new Add(new Number(2), new Variable("x")));
        assertNotEquals(e1, e3);
        Expression e4 = new Parenthed(new Add(new Variable("x"), new Number(1)));
        assertNotEquals(e1, e4);
    }

    /**
     * Tests differentiation of a parenthesized variable.
     */
    @Test
    void testDerivative() {
        Expression e = new Parenthed(new Variable("x"));
        assertEquals(new Number(1), e.derivative("x"));
    }

    /**
     * Tests that simplification delegates to the wrapped expression.
     */
    @Test
    void testSimplify() {
        Expression inner = new Add(new Variable("x"), new Number(1));
        Expression e = new Parenthed(inner);
        assertEquals(inner.simplify(), e.simplify());
    }
}
