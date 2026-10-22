package sys.pro;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Abstract contract test for {@link TopSort} run against a specific
 * {@link Graph} implementation.
 * <p>
 * Every subclass supplies a {@link #createGraph()} factory; the inherited tests
 * verify that the algorithm produces a valid topological order on any DAG,
 * includes all nodes, and detects cycles (including self-loops).
 */
abstract class AbstractTopSortTest {

    /**
     * Verifies that {@code result} is a valid topological order for {@code graph}.
     * <p>
     * For every edge {@code u -> v} in the graph, {@code u} must appear strictly
     * before {@code v} in {@code result}.
     *
     * @param graph  the graph whose edges are used as constraints
     * @param result the candidate topological order
     * @return {@code true} if {@code result} satisfies every edge constraint
     */
    protected static boolean isTopSorted(Graph graph, List<GraphNode> result) {
        for (GraphNode from : graph.nodes()) {
            int fromIdx = result.indexOf(from);
            for (GraphNode to : graph.getNeighbors(from)) {
                int toIdx = result.indexOf(to);
                if (fromIdx >= toIdx) {
                    return false;
                }
            }
        }
        return true;
    }

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

    /** Sorting an empty graph returns an empty list. */
    @Test
    void emptyGraphGivesEmptyOrder() {
        Graph graph = createGraph();

        assertTrue(TopSort.sort(graph).isEmpty());
    }

    /** Sorting a single-node graph returns that node. */
    @Test
    void singleNodeGraphGivesThatNode() {
        Graph graph = createGraph();
        GraphNode a = node(1);
        graph.addNode(a);

        List<GraphNode> result = TopSort.sort(graph);

        assertEquals(1, result.size());
        assertEquals(a, result.getFirst());
    }

    /** A simple chain {@code A -> B -> C} is returned in insertion order. */
    @Test
    void chainIsSortedInCorrectOrder() {
        Graph graph = createGraph();
        GraphNode a = node(1);
        GraphNode b = node(2);
        GraphNode c = node(3);

        graph.addEdge(a, b);
        graph.addEdge(b, c);

        List<GraphNode> result = TopSort.sort(graph);

        assertEquals(List.of(a, b, c), result);
    }

    /** Every edge of a diamond DAG is respected. */
    @Test
    void resultRespectsEveryEdge() {
        Graph graph = createGraph();

        GraphNode a = node(1);
        GraphNode b = node(2);
        GraphNode c = node(3);
        GraphNode d = node(4);

        graph.addEdge(a, b);
        graph.addEdge(a, c);
        graph.addEdge(b, d);
        graph.addEdge(c, d);

        List<GraphNode> result = TopSort.sort(graph);

        assertEquals(4, result.size());
        assertTrue(isTopSorted(graph, result));
    }

    /** All nodes of a disconnected graph are present and correctly ordered per component. */
    @Test
    void disconnectedGraphSortsAllNodes() {
        Graph graph = createGraph();
        GraphNode a = node(1);
        GraphNode b = node(2);
        GraphNode c = node(3);
        GraphNode d = node(4);

        graph.addEdge(a, b);
        graph.addEdge(c, d);

        List<GraphNode> result = TopSort.sort(graph);

        assertEquals(4, result.size());
        assertTrue(result.containsAll(List.of(a, b, c, d)));
        assertTrue(isTopSorted(graph, result));
    }

    /** Isolated nodes are still included in the result. */
    @Test
    void isolatedNodesAreAllReturned() {
        Graph graph = createGraph();
        graph.addNode(node(1));
        graph.addNode(node(2));
        graph.addNode(node(3));

        List<GraphNode> result = TopSort.sort(graph);

        assertEquals(3, result.size());
    }

    /** A 2-node cycle is detected. */
    @Test
    void cycleThrows() {
        Graph graph = createGraph();
        GraphNode a = node(1);
        GraphNode b = node(2);

        graph.addEdge(a, b);
        graph.addEdge(b, a);

        assertThrows(IllegalStateException.class, () -> TopSort.sort(graph));
    }

    /** A 3-node cycle is detected. */
    @Test
    void threeNodeCycleThrows() {
        Graph graph = createGraph();
        GraphNode a = node(1);
        GraphNode b = node(2);
        GraphNode c = node(3);

        graph.addEdge(a, b);
        graph.addEdge(b, c);
        graph.addEdge(c, a);

        assertThrows(IllegalStateException.class, () -> TopSort.sort(graph));
    }

    /** A self-loop is treated as a one-node cycle. */
    @Test
    void selfLoopThrows() {
        Graph graph = createGraph();
        GraphNode a = node(1);

        graph.addEdge(a, a);

        assertThrows(IllegalStateException.class, () -> TopSort.sort(graph));
    }
}