package sys.pro;

import java.util.ArrayList;

public abstract class AbstractGraph implements Graph {
    /**
     * Returns a string representation of this graph.
     * <p>
     * For each node, the output contains the node followed by an arrow and
     * a comma-separated list of its neighbors.
     *
     * @return a string representation of the graph
     */
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



    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Graph)) {
            return false;
        }
        return equals(this, (Graph) obj);
    }

    /**
     * Compares two graphs for equality.
     * <p>
     * Graphs are considered equal if they contain the same nodes and, for every
     * matching node, the same sets of neighbors. The comparison is order-insensitive.
     *
     * @param graph1 the first graph to compare
     * @param graph2 the second graph to compare
     * @return {@code true} if the graphs contain the same nodes and neighbor sets;
     *         {@code false} otherwise
     */
    static boolean equals(Graph graph1, Graph graph2) {
        if (graph1 == graph2) {
            return true;
        }
        if (graph1 == null || graph2 == null) {
            return false;
        }

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

    /**
     * Returns the number of nodes in this graph.
     *
     * @return the number of nodes
     */
    @Override
    public int size() {
        return this.nodes().size();
    }
}
