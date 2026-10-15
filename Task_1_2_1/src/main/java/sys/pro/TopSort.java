package sys.pro;

import java.util.ArrayList;
import java.util.HashMap;

public class TopSort {
    private static GraphNode entrance;
    private static HashMap<GraphNode, Boolean> visited;
    public static ArrayList<GraphNode> result;

    private static void sortIter(Graph graph) {
        visited.put(entrance, true);
        GraphNode e = entrance;
        for (GraphNode node : graph.getNeighbors(entrance)) {
            if (visited.get(node)) continue;

            entrance = node;
            sortIter(graph);
        }
        result.add(e);
    }

    public static ArrayList<GraphNode> sort(Graph graph) {
        visited = new HashMap<>();
        result = new ArrayList<>(graph.size());
        ArrayList<GraphNode> nodes = graph.nodes();
        for (GraphNode node : nodes) {
            if (visited.get(node) == null) {
                entrance = node;

                sortIter(graph);
            }
        }
        return result;
    }
}
