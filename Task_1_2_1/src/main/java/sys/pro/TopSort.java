package sys.pro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Provides topological sorting for directed graphs.
 * The sorting is performed using depth-first search and produces an ordering
 * of nodes such that for every directed edge from {@code u} to {@code v},
 * {@code u} appears before {@code v} in the resulting list.
 * If the graph contains a cycle, topological sorting is impossible and
 * an {@link IllegalStateException} is thrown.
 */
public class TopSort {
    /**
     * Performs a recursive depth-first search step for topological sorting.
     * The method visits the specified entrance node and recursively processes
     * all of its neighbors. Nodes are appended to {@code result} after all their
     * descendants have been processed, producing a reverse topological order.
     * The {@code chain} stack is used to detect cycles: if the entrance node is
     * already present in the current DFS chain, a cycle has been found.
     *
     * @param graph the graph being sorted
     * @param visited map tracking nodes that have already been fully processed
     * @param result list accumulating nodes in reverse topological order
     * @param entrance the node currently being visited
     * @param chain the current DFS path used for cycle detection
     * @throws IllegalStateException if a cycle is detected on {@code entrance}
     */
    private static void sortIter(Graph graph,
                                 HashMap<GraphNode, Boolean> visited, ArrayList<GraphNode> result,
                                 GraphNode entrance, Stack<GraphNode> chain) {
        if (chain.contains(entrance)) {
            throw new IllegalStateException("Found cycle on node " + entrance.toString());
        }

        if (visited.containsKey(entrance)) {
            return;
        }

        visited.put(entrance, true);
        chain.add(entrance);
        for (GraphNode node : graph.getNeighbors(entrance)) {
            sortIter(graph, visited, result, node, chain);
        }
        chain.remove(entrance);
        result.add(entrance);
    }

    /**
     * Returns a topological ordering of the nodes in the specified graph.
     * The returned list contains all nodes of the graph ordered so that for every
     * directed edge from {@code u} to {@code v}, {@code u} precedes {@code v}.
     * If the graph is empty, an empty list is returned.
     *
     * @param graph the graph to sort
     * @return a list of graph nodes in topological order
     * @throws IllegalStateException if the graph contains a cycle
     */
    public static List<GraphNode> sort(Graph graph) {
        HashMap<GraphNode, Boolean> visited = new HashMap<>();
        ArrayList<GraphNode> result = new ArrayList<>(graph.size());
        ArrayList<GraphNode> nodes = graph.nodes();
        Stack<GraphNode> chain = new Stack<>();
        for (GraphNode node : nodes) {
            if (visited.get(node) == null) {
                sortIter(graph, visited, result, node, chain);
            }
        }
        return result.reversed();
    }
}
