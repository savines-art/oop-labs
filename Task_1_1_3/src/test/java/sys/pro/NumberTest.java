package sys.pro;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NumberTest {
    @Test
    void testToString() {
        assertEquals("5", new Number(5).toString());
        assertEquals("0", new Number(0).toString());
        assertEquals("(-3)", new Number(-3).toString());
    }

    @Test
    void testEval() {
        assertEquals(5, new Number(5).eval());
        assertEquals(-3, new Number(-3).eval());
    }

    @Test
    void testEvalWithVariables() {
        assertEquals(5, new Number(5).eval("x = 10"));
    }

    @Test
    void testEquals() {
        Number n1 = new Number(5);
        Number n2 = new Number(5);
        Number n3 = new Number(6);
        assertEquals(n1, n2);
        assertNotEquals(n1, n3);
        assertNotEquals(n1, null);
    }

    @Test
    void testDerivative() {
        Number n = new Number(5);
        assertEquals(new Number(0), n.derivative("x"));
    }

    @Test
    void testSimplify() {
        Number n = new Number(5);
        assertSame(n, n.simplify());
    }
}


