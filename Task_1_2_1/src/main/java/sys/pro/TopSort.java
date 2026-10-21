package sys.pro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class TopSort {
    private static void sortIter(Graph graph, HashMap<GraphNode, Boolean> visited, ArrayList<GraphNode> result, GraphNode entrance, Stack<GraphNode> chain) {
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
