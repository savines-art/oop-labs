package sys.pro;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AddTest {
    @Test
    void testToString() {
        Expression e = new Add(new Number(1), new Variable("x"));
        assertEquals("1 + x", e.toString());
    }

    @Test
    void testEval() {
        Expression e = new Add(new Number(1), new Number(2));
        assertEquals(3, e.eval());
    }

    @Test
    void testEvalWithVariables() {
        Expression e = new Add(new Variable("x"), new Variable("y"));
        assertEquals(3, e.eval("x = 1; y = 2"));
    }

    @Test
    void testEquals() {
        Expression e1 = new Add(new Number(1), new Variable("x"));
        Expression e2 = new Add(new Number(1), new Variable("x"));
        Expression e3 = new Add(new Number(2), new Variable("x"));
        Expression e4 = new Add(new Variable("x"), new Number(1));
        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
        assertNotEquals(e1, null);
        assertNotEquals(e1, e4);
    }

    @Test
    void testDerivative() {
        Expression e1 = new Add(new Variable("x"), new Number(2));
        Expression d1 = e1.derivative("x");
        Expression expected1 = new Add(new Number(1), new Number(0));
        Expression e2 = new Add(new Variable("x"), new Variable("y"));
        Expression d2 = e2.derivative("x");
        assertEquals(expected1, d1);
        assertEquals(expected1, d2);
    }

    @Test
    void testSimplifyZeroLeft() {
        Expression e = new Add(new Number(0), new Variable("x"));
        assertEquals(new Variable("x"), e.simplify());
    }

    @Test
    void testSimplifyZeroRight() {
        Expression e = new Add(new Variable("x"), new Number(0));
        assertEquals(new Variable("x"), e.simplify());
    }

    @Test
    void testSimplifyConstants() {
        Expression e = new Add(new Number(2), new Number(3));
        assertEquals(new Number(5), e.simplify());
    }

    @Test
    void testSimplifyVariables() {
        Expression e = new Add(new Variable("x"), new Variable("y"));
        assertEquals(new Add(new Variable("x"), new Variable("y")), e.simplify());
    }

    @Test
    void testSimplifyMixed() {
        Expression e = new Add(new Variable("x"), new Number(2));
        assertEquals(new Add(new Variable("x"), new Number(2)), e.simplify());
    }
}
