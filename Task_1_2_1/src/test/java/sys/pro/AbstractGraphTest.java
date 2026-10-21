package sys.pro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractGraphTest {

    protected abstract Graph createGraph();

    protected GraphNode node(int value) {
        return new GraphNode(value);
    }

    @Test
    void emptyGraphHasZeroSizeAndNoNodes() {
        Graph graph = createGraph();

        assertEquals(0, graph.size());
        assertTrue(graph.nodes().isEmpty());
    }

    @Test
    void addNodeAddsNode() {
        Graph graph = createGraph();
        GraphNode a = node(1);

        graph.addNode(a);

        assertEquals(1, graph.size());
        assertTrue(graph.nodes().contains(a));
    }

    @Test
    void addNodeIsIdempotent() {
        Graph graph = createGraph();
        GraphNode a = node(1);

        graph.addNode(a);
        graph.addNode(a);

        assertEquals(1, graph.size());
    }

    @Test
    void addEdgeCreatesMissingNodes() {
        Graph graph = createGraph();

        graph.addEdge(node(1), node(2));

        assertEquals(2, graph.size());
        assertTrue(graph.nodes().contains(node(1)));
        assertTrue(graph.nodes().contains(node(2)));
    }

    @Test
    void addEdgeIsDirected() {
        Graph graph = createGraph();
        GraphNode a = node(1);
        GraphNode b = node(2);

        graph.addEdge(a, b);

        assertTrue(graph.getNeighbors(a).contains(b));
        assertFalse(graph.getNeighbors(b).contains(a));
    }

    @Test
    void addEdgeIsIdempotent() {
        Graph graph = createGraph();
        GraphNode a = node(1);
        GraphNode b = node(2);

        graph.addEdge(a, b);
        graph.addEdge(a, b);

        assertEquals(1L, graph.getNeighbors(a).stream().filter(n -> n.equals(b)).count());
    }

    @Test
    void removeEdgeRemovesOnlyGivenDirection() {
        Graph graph = createGraph();
        GraphNode a = node(1);
        GraphNode b = node(2);

        graph.addEdge(a, b);
        graph.addEdge(b, a);
        graph.removeEdge(a, b);

        assertFalse(graph.getNeighbors(a).contains(b));
        assertTrue(graph.getNeighbors(b).contains(a));
    }

    @Test
    void removeEdgeOnMissingEdgeIsNoOp() {
        Graph graph = createGraph();
        graph.addNode(node(1));
        graph.addNode(node(2));

        assertDoesNotThrow(() -> graph.removeEdge(node(1), node(2)));
        assertEquals(2, graph.size());
    }

    @Test
    void removeNodeRemovesIncidentEdges() {
        Graph graph = createGraph();
        GraphNode a = node(1);
        GraphNode b = node(2);
        GraphNode c = node(3);

        graph.addEdge(a, b);
        graph.addEdge(b, c);

        graph.removeNode(b);

        assertEquals(2, graph.size());
        assertFalse(graph.nodes().contains(b));
        assertFalse(graph.getNeighbors(a).contains(b));
    }

    @Test
    void removeNodeOnMissingNodeIsNoOp() {
        Graph graph = createGraph();
        graph.addNode(node(1));

        assertDoesNotThrow(() -> graph.removeNode(node(99)));
        assertEquals(1, graph.size());
    }

    @Test
    void getNeighborsOnMissingNodeReturnsEmptyList() {
        Graph graph = createGraph();
        graph.addNode(node(1));

        List<GraphNode> neighbors = graph.getNeighbors(node(99));

        assertNotNull(neighbors);
        assertTrue(neighbors.isEmpty());
    }

    @Test
    void clearRemovesEverything() {
        Graph graph = createGraph();
        graph.addEdge(node(1), node(2));
        graph.addEdge(node(2), node(3));

        graph.clear();

        assertEquals(0, graph.size());
        assertTrue(graph.nodes().isEmpty());
    }

    @Test
    void selfLoopIsSupported() {
        Graph graph = createGraph();
        GraphNode a = node(1);

        graph.addEdge(a, a);

        assertEquals(1, graph.size());
        assertEquals(1L, graph.getNeighbors(a).stream().filter(n -> n.equals(a)).count());
    }

    @Test
    void removeNodeRemovesSelfLoop() {
        Graph graph = createGraph();
        GraphNode a = node(1);

        graph.addEdge(a, a);
        graph.removeNode(a);

        assertEquals(0, graph.size());
    }

    @Test
    void removeEdgeRemovesSelfLoop() {
        Graph graph = createGraph();
        GraphNode a = node(1);

        graph.addEdge(a, a);
        graph.removeEdge(a, a);

        assertTrue(graph.getNeighbors(a).isEmpty());
        assertEquals(1, graph.size());
    }

    @Test
    void crateGraphFromFileLoadsNodesAndEdges(@TempDir Path dir) throws IOException {
        Path file = dir.resolve("graph.txt");
        // формат: N (= число строк), строка узлов, затем N-2 строк рёбер "from to..."
        Files.writeString(file, """
                4
                1 2 3
                1 2
                2 3
                """);

        Graph graph = createGraph();
        graph.crateGraphFromFile(file.toString());

        assertEquals(3, graph.size());
        assertTrue(graph.getNeighbors(node(1)).contains(node(2)));
        assertTrue(graph.getNeighbors(node(2)).contains(node(3)));
        assertTrue(graph.getNeighbors(node(3)).isEmpty());
    }

    @Test
    void crateGraphFromFileWithNoEdges(@TempDir Path dir) throws IOException {
        Path file = dir.resolve("graph.txt");
        Files.writeString(file, """
                2
                1 2
                """);

        Graph graph = createGraph();
        graph.crateGraphFromFile(file.toString());

        assertEquals(2, graph.size());
        assertTrue(graph.getNeighbors(node(1)).isEmpty());
        assertTrue(graph.getNeighbors(node(2)).isEmpty());
    }

    @Test
    void crateGraphFromFileClearsPreviousContent(@TempDir Path dir) throws IOException {
        Path file = dir.resolve("graph.txt");
        Files.writeString(file, """
                2
                1 2
                """);

        Graph graph = createGraph();
        graph.addEdge(node(99), node(100));   // старый контент
        graph.crateGraphFromFile(file.toString());

        assertEquals(2, graph.size());
        assertFalse(graph.nodes().contains(node(99)));
    }
}