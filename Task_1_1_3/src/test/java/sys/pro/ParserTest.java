package sys.pro;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    @Test
    void parseTest() {
        String expr = "1 - 3 * 3 + 4 * 2 - 5";
        Parser parser = new Parser(expr);
        assertEquals(-5, parser.parse().eval(""));
    }

    @Test
    void parseParentheses() {
        String expr = "(1 - 3) * 3 + 4 * (2 - 5)";
        Parser parser = new Parser(expr);
        assertEquals(-18, parser.parse().eval(""));
    }

    @Test
    void testVariables() {
        String expr = "x + y + z";
        Parser parser = new Parser(expr);
        assertEquals(6, parser.parse().eval("x = 1; y = 2; z = 3"));
    }
}
