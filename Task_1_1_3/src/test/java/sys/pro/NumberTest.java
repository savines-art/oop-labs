package sys.pro;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Number}.
 */
public class NumberTest {
    /**
     * Tests {@link Number#toString()}, including negative values.
     */
    @Test
    void testToString() {
        assertEquals("5", new Number(5).toString());
        assertEquals("0", new Number(0).toString());
        assertEquals("(-3)", new Number(-3).toString());
    }

    /**
     * Tests constant evaluation.
     */
    @Test
    void testEval() {
        assertEquals(5, new Number(5).eval());
        assertEquals(-3, new Number(-3).eval());
    }

    /**
     * Tests evaluation with an unused variable binding.
     */
    @Test
    void testEvalWithVariables() {
        assertEquals(5, new Number(5).eval("x = 10"));
    }

    /**
     * Tests equality for {@link Number} values.
     */
    @Test
    void testEquals() {
        Number n1 = new Number(5);
        assertNotEquals(n1, null);
        Number n2 = new Number(5);
        assertEquals(n1, n2);
        Number n3 = new Number(6);
        assertNotEquals(n1, n3);
    }

    /**
     * Tests differentiation of a constant.
     */
    @Test
    void testDerivative() {
        Number n = new Number(5);
        assertEquals(new Number(0), n.derivative("x"));
    }

    /**
     * Tests that simplification returns the same number instance.
     */
    @Test
    void testSimplify() {
        Number n = new Number(5);
        assertSame(n, n.simplify());
    }
}


