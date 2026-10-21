package sys.pro;

import java.util.ArrayList;

public abstract class Graph implements AbstractGraph {
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (GraphNode k : this.nodes()) {
            StringBuilder raw = new StringBuilder();
            raw.append(k.toString());
            raw.append(" -> ");
            for (GraphNode node : this.getNeighbors(k)) {
                raw.append(node.toString());
                raw.append(", ");
            }
            raw.append("\n");
            ret.append(raw);
        }
        return ret.toString();
    }

    public static boolean equals(Graph graph1, Graph graph2) {
        if (!graph1.nodes().containsAll(graph2.nodes())
                || !graph2.nodes().containsAll(graph1.nodes())) {
            return false;
        }

        for (GraphNode node : graph1.nodes()) {
            for (GraphNode k : graph2.nodes()) {
                if (!k.equals(node)) {
                    continue;
                }
                ArrayList<GraphNode> neighbors1 = graph1.getNeighbors(node);
                ArrayList<GraphNode> neighbors2 = graph2.getNeighbors(k);
                if (!neighbors1.containsAll(neighbors2)
                        || !neighbors2.containsAll(neighbors1)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int size() {
        return this.nodes().size();
    }
}
