package sys.pro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Abstract contract test for all {@link Graph} implementations.
 * <p>
 * Concrete implementations extend this class and supply a {@link #createGraph()}
 * factory method. Every inherited test verifies the same public contract, so
 * {@link GraphList}, {@link GraphMatrix} and {@link GraphInc} are guaranteed
 * to behave identically from the client's point of view.
 */
abstract class AbstractGraphTest {

    /**
     * Creates a new empty graph of the concrete implementation under test.
     *
     * @return a fresh empty {@link Graph} instance
     */
    protected abstract Graph createGraph();

    /**
     * Convenience factory for graph nodes used by the tests.
     *
     * @param value node identifier
     * @return a new {@link GraphNode} with the given value
     */
    protected GraphNode node(int value) {
        return new GraphNode(value);
    }

    /** A freshly created graph must be empty. */
    @Test
    void emptyGraphHasZeroSizeAndNoNodes() {
        Graph graph = createGraph();

        assertEquals(0, graph.size());
        assertTrue(graph.nodes().isEmpty());
    }

    /** {@code addNode} makes the node visible via {@code size()} and {@code nodes()}. */
    @Test
    void addNodeAddsNode() {
        Graph graph = createGraph();
        GraphNode a = node(1);

        graph.addNode(a);

        assertEquals(1, graph.size());
        assertTrue(graph.nodes().contains(a));
    }

    /** Adding the same node twice must not change the size. */
    @Test
    void addNodeIsIdempotent() {
        Graph graph = createGraph();
        GraphNode a = node(1);

        graph.addNode(a);
        graph.addNode(a);

        assertEquals(1, graph.size());
    }

    /** {@code addEdge} implicitly creates both endpoint nodes. */
    @Test
    void addEdgeCreatesMissingNodes() {
        Graph graph = createGraph();

        graph.addEdge(node(1), node(2));

        assertEquals(2, graph.size());
        assertTrue(graph.nodes().contains(node(1)));
        assertTrue(graph.nodes().contains(node(2)));
    }

    /** Edges are directed: {@code a -> b} does not imply {@code b -> a}. */
    @Test
    void addEdgeIsDirected() {
        Graph graph = createGraph();
        GraphNode a = node(1);
        GraphNode b = node(2);

        graph.addEdge(a, b);

        assertTrue(graph.getNeighbors(a).contains(b));
        assertFalse(graph.getNeighbors(b).contains(a));
    }

    /** Adding the same directed edge twice must not create a duplicate. */
    @Test
    void addEdgeIsIdempotent() {
        Graph graph = createGraph();
        GraphNode a = node(1);
        GraphNode b = node(2);

        graph.addEdge(a, b);
        graph.addEdge(a, b);

        assertEquals(1L, graph.getNeighbors(a).stream().filter(n -> n.equals(b)).count());
    }

    /** Removing {@code a -> b} must not affect {@code b -> a}. */
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

    /** Removing a non-existent edge is a no-op. */
    @Test
    void removeEdgeOnMissingEdgeIsNoOp() {
        Graph graph = createGraph();
        graph.addNode(node(1));
        graph.addNode(node(2));

        assertDoesNotThrow(() -> graph.removeEdge(node(1), node(2)));
        assertEquals(2, graph.size());
    }

    /** Removing a node also removes all incident edges. */
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

    /** Removing a non-existent node is a no-op. */
    @Test
    void removeNodeOnMissingNodeIsNoOp() {
        Graph graph = createGraph();
        graph.addNode(node(1));

        assertDoesNotThrow(() -> graph.removeNode(node(99)));
        assertEquals(1, graph.size());
    }

    /** {@code getNeighbors} returns an empty (but not null) list for unknown nodes. */
    @Test
    void getNeighborsOnMissingNodeReturnsEmptyList() {
        Graph graph = createGraph();
        graph.addNode(node(1));

        List<GraphNode> neighbors = graph.getNeighbors(node(99));

        assertNotNull(neighbors);
        assertTrue(neighbors.isEmpty());
    }

    /** {@code clear} resets the graph to its initial empty state. */
    @Test
    void clearRemovesEverything() {
        Graph graph = createGraph();
        graph.addEdge(node(1), node(2));
        graph.addEdge(node(2), node(3));

        graph.clear();

        assertEquals(0, graph.size());
        assertTrue(graph.nodes().isEmpty());
    }

    /** A self-loop {@code a -> a} is a valid edge. */
    @Test
    void selfLoopIsSupported() {
        Graph graph = createGraph();
        GraphNode a = node(1);

        graph.addEdge(a, a);

        assertEquals(1, graph.size());
        assertEquals(1L, graph.getNeighbors(a).stream().filter(n -> n.equals(a)).count());
    }

    /** Removing a node removes its self-loop as well. */
    @Test
    void removeNodeRemovesSelfLoop() {
        Graph graph = createGraph();
        GraphNode a = node(1);

        graph.addEdge(a, a);
        graph.removeNode(a);

        assertEquals(0, graph.size());
    }

    /** Removing a self-loop leaves the node in place. */
    @Test
    void removeEdgeRemovesSelfLoop() {
        Graph graph = createGraph();
        GraphNode a = node(1);

        graph.addEdge(a, a);
        graph.removeEdge(a, a);

        assertTrue(graph.getNeighbors(a).isEmpty());
        assertEquals(1, graph.size());
    }

    /** Loads nodes and edges from a well-formed input file. */
    @Test
    void crateGraphFromFileLoadsNodesAndEdges(@TempDir Path dir) throws IOException {
        Path file = dir.resolve("graph.txt");
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

    /** A graph file with no edge lines produces isolated nodes. */
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

    /** Loading from file first clears any previous graph content. */
    @Test
    void crateGraphFromFileClearsPreviousContent(@TempDir Path dir) throws IOException {
        Path file = dir.resolve("graph.txt");
        Files.writeString(file, """
                2
                1 2
                """);

        Graph graph = createGraph();
        graph.addEdge(node(99), node(100));
        graph.crateGraphFromFile(file.toString());

        assertEquals(2, graph.size());
        assertFalse(graph.nodes().contains(node(99)));
    }

    /** Extra whitespace between tokens is tolerated. */
    @Test
    void crateGraphFromFileToleratesExtraSpaces(@TempDir Path dir) throws IOException {
        Path file = dir.resolve("graph.txt");
        Files.writeString(file, """
                3
                   1    2
                1   2
                """);

        Graph graph = createGraph();
        graph.crateGraphFromFile(file.toString());

        assertEquals(2, graph.size());
        assertTrue(graph.getNeighbors(node(1)).contains(node(2)));
    }

    /** Self-loops can be parsed from the input file. */
    @Test
    void crateGraphFromFileWithSelfLoop(@TempDir Path dir) throws IOException {
        Path file = dir.resolve("graph.txt");
        Files.writeString(file, """
                3
                1
                1 1
                """);

        Graph graph = createGraph();
        graph.crateGraphFromFile(file.toString());

        assertEquals(1, graph.size());
        assertTrue(graph.getNeighbors(node(1)).contains(node(1)));
    }

    /** {@code equals} is reflexive. */
    @Test
    void equalsIsReflexive() {
        Graph graph = createGraph();
        graph.addEdge(node(1), node(2));

        assertEquals(graph, graph);
        assertTrue(graph.equals(graph));
    }

    /** {@code equals(null)} returns {@code false}. */
    @Test
    void equalsWithNullIsFalse() {
        Graph graph = createGraph();

        assertNotEquals(null, graph);
        assertFalse(graph.equals(null));
    }

    /** {@code equals} with a non-graph object returns {@code false}. */
    @Test
    void equalsWithNonGraphIsFalse() {
        Graph graph = createGraph();

        assertNotEquals(graph, "not a graph");
        assertNotEquals(graph, 42);
        assertFalse(graph.equals(new Object()));
        assertFalse(graph.equals(node(1)));
    }

    /** Two empty graphs are equal. */
    @Test
    void equalsEmptyGraphsAreEqual() {
        assertEquals(createGraph(), createGraph());
    }

    /** Two graphs with identical content are equal in both directions. */
    @Test
    void equalsSameContentIsEqual() {
        Graph a = createGraph();
        Graph b = createGraph();

        a.addEdge(node(1), node(2));
        a.addEdge(node(2), node(3));
        b.addEdge(node(1), node(2));
        b.addEdge(node(2), node(3));

        assertEquals(a, b);
        assertEquals(b, a);
    }

    /** Equality breaks symmetrically as soon as one graph changes. */
    @Test
    void equalsIsSymmetric() {
        Graph a = createGraph();
        Graph b = createGraph();
        a.addEdge(node(1), node(2));
        b.addEdge(node(1), node(2));

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));

        b.addEdge(node(2), node(1));

        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
    }

    /** Different node sets are not equal. */
    @Test
    void equalsDifferentNodeSetsNotEqual() {
        Graph a = createGraph();
        Graph b = createGraph();

        a.addNode(node(1));
        b.addNode(node(2));

        assertNotEquals(a, b);
    }

    /** A strict subset of nodes is not equal to the superset. */
    @Test
    void equalsSubsetNodeSetsNotEqual() {
        Graph a = createGraph();
        Graph b = createGraph();

        a.addNode(node(1));
        a.addNode(node(2));
        b.addNode(node(1));

        assertNotEquals(a, b);
    }

    /** Different edge sets are not equal. */
    @Test
    void equalsDifferentEdgesNotEqual() {
        Graph a = createGraph();
        Graph b = createGraph();

        a.addEdge(node(1), node(2));
        b.addEdge(node(1), node(3));

        assertNotEquals(a, b);
    }

    /** Direction matters for equality. */
    @Test
    void equalsReversedEdgeNotEqual() {
        Graph a = createGraph();
        Graph b = createGraph();

        a.addEdge(node(1), node(2));
        b.addEdge(node(2), node(1));

        assertNotEquals(a, b);
    }

    /** Presence of a self-loop breaks equality. */
    @Test
    void equalsSelfLoopMatters() {
        Graph a = createGraph();
        Graph b = createGraph();

        a.addEdge(node(1), node(1));

        assertNotEquals(a, b);
    }

    /** Insertion order does not affect equality. */
    @Test
    void equalsIgnoresInsertionOrder() {
        Graph a = createGraph();
        Graph b = createGraph();

        a.addEdge(node(1), node(2));
        a.addEdge(node(2), node(3));

        b.addEdge(node(2), node(3));
        b.addEdge(node(1), node(2));

        assertEquals(a, b);
    }

    /** Adding then removing an edge leaves the graph equal to a graph built without it. */
    @Test
    void equalsAfterAddThenRemoveIsEqual() {
        Graph a = createGraph();
        Graph b = createGraph();

        a.addEdge(node(1), node(2));
        a.removeEdge(node(1), node(2));

        b.addNode(node(1));
        b.addNode(node(2));

        assertEquals(a, b);
    }

    /** An empty graph renders as an empty string. */
    @Test
    void toStringOnEmptyGraphIsEmpty() {
        assertEquals("", createGraph().toString());
    }

    /** {@code toString} includes every node at least once. */
    @Test
    void toStringContainsEveryNode() {
        Graph graph = createGraph();
        graph.addEdge(node(1), node(2));
        graph.addEdge(node(2), node(3));

        String s = graph.toString();

        assertTrue(s.contains("(1)"), "toString missing (1): " + s);
        assertTrue(s.contains("(2)"), "toString missing (2): " + s);
        assertTrue(s.contains("(3)"), "toString missing (3): " + s);
    }

    /** {@code toString} reflects directed edges but not their reverse. */
    @Test
    void toStringReflectsEdgesButNotReverse() {
        Graph graph = createGraph();
        graph.addEdge(node(1), node(2));

        String s = graph.toString();

        assertTrue(
                s.lines().anyMatch(line -> line.startsWith("(1)") && line.contains("(2)")),
                "toString does not reflect edge 1 -> 2: " + s
        );
        assertTrue(
                s.lines()
                        .filter(line -> line.startsWith("(2)"))
                        .noneMatch(line -> line.contains("(1)")),
                "toString incorrectly shows reverse edge 2 -> 1: " + s
        );
    }

    /** {@code toString} produces one line per node. */
    @Test
    void toStringHasOneLinePerNode() {
        Graph graph = createGraph();
        graph.addEdge(node(1), node(2));
        graph.addEdge(node(2), node(3));

        long nonEmptyLines = graph.toString().lines()
                .filter(line -> !line.isBlank())
                .count();

        assertEquals(3, nonEmptyLines);
    }
}