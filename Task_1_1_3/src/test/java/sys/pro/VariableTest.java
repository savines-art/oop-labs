package sys.pro;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Tests for {@link Variable}.
 */
public class VariableTest {
    /**
     * Tests {@link Variable#toString()}.
     */
    @Test
    void testToString() {
        assertEquals("x", new Variable("x").toString());
    }

    /**
     * Tests evaluation when the variable is bound.
     */
    @Test
    void testEvalWithVariables() {
        Variable v = new Variable("x");
        assertEquals(10, v.eval("x = 10"));
    }

    /**
     * Tests evaluation when the variable binding is missing from a map.
     */
    @Test
    void testEvalWithVariablesMissing() {
        Variable v = new Variable("x");
        HashMap<String, Integer> vars = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> v.eval(vars));
    }

    /**
     * Tests evaluation when no variables are supplied.
     */
    @Test
    void testEvalNoVariables() {
        Variable v = new Variable("x");
        assertThrows(IllegalArgumentException.class, () -> v.eval());
    }

    /**
     * Tests equality for {@link Variable} names.
     */
    @Test
    void testEquals() {
        Variable v1 = new Variable("x");
        Variable v2 = new Variable("x");
        Variable v3 = new Variable("y");
        assertEquals(v1, v2);
        assertNotEquals(v1, v3);
        assertNotEquals(v1, null);
        assertNotEquals(v1, "x");
    }

    /**
     * Tests differentiation with respect to the variable itself and another variable.
     */
    @Test
    void testDerivative() {
        Variable v = new Variable("x");
        assertEquals(new Number(1), v.derivative("x"));
        assertEquals(new Number(0), v.derivative("y"));
    }

    /**
     * Tests that simplification returns the same variable instance.
     */
    @Test
    void testSimplify() {
        Variable v = new Variable("x");
        assertSame(v, v.simplify());
    }
}
