package sys.pro;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GraphNodeTest {

    @Test
    void equalsByValue() {
        assertEquals(new GraphNode(10), new GraphNode(10));
    }

    @Test
    void notEqualsForDifferentValues() {
        assertNotEquals(new GraphNode(1), new GraphNode(2));
    }

    @Test
    void notEqualsToOtherTypes() {
        assertNotEquals(new GraphNode(1), 1);
        assertNotEquals(new GraphNode(1), null);
    }

    @Test
    void hashCodeConsistentWithEquals() {
        assertEquals(new GraphNode(10).hashCode(), new GraphNode(10).hashCode());
    }

    @Test
    void worksInHashSet() {
        Set<GraphNode> set = new HashSet<>();
        set.add(new GraphNode(1));
        set.add(new GraphNode(1));
        set.add(new GraphNode(2));

        assertEquals(2, set.size());
    }

    @Test
    void toStringContainsValue() {
        assertEquals("(42)", new GraphNode(42).toString());
    }
}