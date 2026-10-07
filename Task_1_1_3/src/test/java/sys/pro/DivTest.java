package sys.pro;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DivTest {
    @Test
    void testToString() {
        Expression e = new Div(new Number(6), new Variable("x"));
        assertEquals("6 / x", e.toString());
    }

    @Test
    void testEval() {
        Expression e = new Div(new Number(6), new Number(2));
        assertEquals(3, e.eval());
    }

    @Test
    void testEvalWithVariables() {
        Expression e = new Div(new Variable("x"), new Variable("y"));
        assertEquals(3, e.eval("x = 6; y = 2"));
    }

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

    @Test
    void testSimplifyRightOne() {
        Expression e = new Div(new Variable("x"), new Number(1));
        assertEquals(new Variable("x"), e.simplify());
    }

    @Test
    void testSimplifyConstants() {
        Expression e = new Div(new Number(6), new Number(2));
        assertEquals(new Number(3), e.simplify());
    }

    @Test
    void testSimplifyVariables() {
        Expression e = new Div(new Variable("x"), new Variable("y"));
        assertEquals(new Div(new Variable("x"), new Variable("y")), e.simplify());
    }

    @Test
    void testSimplifyMixed() {
        Expression e = new Div(new Variable("x"), new Number(2));
        assertEquals(new Div(new Variable("x"), new Number(2)), e.simplify());
    }
}
