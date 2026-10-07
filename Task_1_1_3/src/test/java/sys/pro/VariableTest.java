package sys.pro;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class VariableTest {
    @Test
    void testToString() {
        assertEquals("x", new Variable("x").toString());
    }

    @Test
    void testEvalWithVariables() {
        Variable v = new Variable("x");
        assertEquals(10, v.eval("x = 10"));
    }

    @Test
    void testEvalWithVariablesMissing() {
        Variable v = new Variable("x");
        HashMap<String, Integer> vars = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> v.eval(vars));
    }

    @Test
    void testEvalNoVariables() {
        Variable v = new Variable("x");
        assertThrows(IllegalArgumentException.class, () -> v.eval());
    }

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

    @Test
    void testDerivative() {
        Variable v = new Variable("x");
        assertEquals(new Number(1), v.derivative("x"));
        assertEquals(new Number(0), v.derivative("y"));
    }

    @Test
    void testSimplify() {
        Variable v = new Variable("x");
        assertSame(v, v.simplify());
    }
}
