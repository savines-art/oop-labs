package sys.pro;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GraphEqualsTest {

    private GraphNode n(int v) { return new GraphNode(v); }

    @Test
    void reflexive() {
        Graph g = new GraphList();
        g.addEdge(n(1), n(2));
        assertTrue(Graph.equals(g, g));
    }

    @Test
    void emptyGraphsAreEqual() {
        assertTrue(Graph.equals(new GraphList(), new GraphMatrix()));
        assertTrue(Graph.equals(new GraphMatrix(), new GraphInc()));
        assertTrue(Graph.equals(new GraphInc(), new GraphList()));
    }

    @Test
    void sameShapeAcrossImplementationsIsEqual() {
        GraphList list = new GraphList();
        GraphMatrix matrix = new GraphMatrix();
        GraphInc inc = new GraphInc();
        for (Graph g : List.of(list, matrix, inc)) {
            g.addEdge(n(1), n(2));
            g.addEdge(n(2), n(3));
        }

        assertTrue(Graph.equals(list, matrix));
        assertTrue(Graph.equals(matrix, inc));
        assertTrue(Graph.equals(list, inc));
    }

    @Test
    void differentNodeSetsAreNotEqual() {
        Graph a = new GraphList();
        Graph b = new GraphMatrix();
        a.addNode(n(1));
        b.addNode(n(2));

        assertFalse(Graph.equals(a, b));
    }

    @Test
    void differentEdgeSetsAreNotEqual() {
        Graph a = new GraphList();
        Graph b = new GraphMatrix();
        a.addEdge(n(1), n(2));
        b.addEdge(n(1), n(3));

        assertFalse(Graph.equals(a, b));
    }

    @Test
    void reversedDirectionIsNotEqual() {
        Graph a = new GraphList();
        Graph b = new GraphMatrix();
        a.addEdge(n(1), n(2));
        b.addEdge(n(2), n(1));

        assertFalse(Graph.equals(a, b));
    }

    @Test
    void selfLoopMatters() {
        Graph a = new GraphInc();
        Graph b = new GraphList();
        a.addEdge(n(1), n(1));

        assertFalse(Graph.equals(a, b));
    }
}