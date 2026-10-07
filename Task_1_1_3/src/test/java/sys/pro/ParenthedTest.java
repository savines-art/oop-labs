package sys.pro;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ParenthedTest {
    @Test
    void testToString() {
        Expression e = new Parenthed(new Add(new Variable("x"), new Number(1)));
        assertEquals("(x + 1)", e.toString());
    }

    @Test
    void testEval() {
        Expression e = new Parenthed(new Add(new Number(1), new Number(2)));
        assertEquals(3, e.eval());
    }

    @Test
    void testEvalWithVariables() {
        Expression e = new Parenthed(new Add(new Variable("x"), new Number(2)));
        assertEquals(3, e.eval("x = 1"));
    }

    @Test
    void testEquals() {
        Expression e1 = new Parenthed(new Add(new Number(1), new Variable("x")));
        Expression e2 = new Parenthed(new Add(new Number(1), new Variable("x")));
        Expression e3 = new Parenthed(new Add(new Number(2), new Variable("x")));
        Expression e4 = new Parenthed(new Add(new Variable("x"), new Number(1)));
        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
        assertNotEquals(e1, null);
        assertNotEquals(e1, e4);
    }

    @Test
    void testDerivative() {
        Expression e = new Parenthed(new Variable("x"));
        assertEquals(new Number(1), e.derivative("x"));
    }

    @Test
    void testSimplify() {
        Expression inner = new Add(new Variable("x"), new Number(1));
        Expression e = new Parenthed(inner);
        assertEquals(inner.simplify(), e.simplify());
    }
}
