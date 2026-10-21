package sys.pro;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractTopSortTest {

    protected abstract Graph createGraph();

    protected GraphNode node(int value) {
        return new GraphNode(value);
    }

    @Test
    void emptyGraphGivesEmptyOrder() {
        Graph graph = createGraph();

        assertTrue(TopSort.sort(graph).isEmpty());
    }

    @Test
    void singleNodeGraphGivesThatNode() {
        Graph graph = createGraph();
        GraphNode a = node(1);
        graph.addNode(a);

        List<GraphNode> result = TopSort.sort(graph);

        assertEquals(1, result.size());
        assertEquals(a, result.get(0));
    }

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
        for (GraphNode from : graph.nodes()) {
            int fromIdx = result.indexOf(from);
            for (GraphNode to : graph.getNeighbors(from)) {
                int toIdx = result.indexOf(to);
                assertTrue(fromIdx < toIdx,
                        "Edge " + from + " -> " + to + " violates order " + result);
            }
        }
    }

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
        assertTrue(result.indexOf(a) < result.indexOf(b));
        assertTrue(result.indexOf(c) < result.indexOf(d));
    }

    @Test
    void isolatedNodesAreAllReturned() {
        Graph graph = createGraph();
        graph.addNode(node(1));
        graph.addNode(node(2));
        graph.addNode(node(3));

        List<GraphNode> result = TopSort.sort(graph);

        assertEquals(3, result.size());
    }

    @Test
    void cycleThrows() {
        Graph graph = createGraph();
        GraphNode a = node(1);
        GraphNode b = node(2);

        graph.addEdge(a, b);
        graph.addEdge(b, a);

        assertThrows(IllegalStateException.class, () -> TopSort.sort(graph));
    }

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

    @Test
    void selfLoopThrows() {
        Graph graph = createGraph();
        GraphNode a = node(1);

        graph.addEdge(a, a);

        assertThrows(IllegalStateException.class, () -> TopSort.sort(graph));
    }
}