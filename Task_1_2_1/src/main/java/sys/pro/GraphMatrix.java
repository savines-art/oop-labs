package sys.pro;

import java.util.ArrayList;

/**
 * Graph implementation: Adjacency matrix.
 * {@code matrix.get(i).get(j)} is {@code true} if there is an edge
 * from {@code nodes.get(i)} to {@code nodes.get(j)}.
 */
public class GraphMatrix extends AbstractGraph {
    /**
     * List of nodes stored in this adjacency-matrix graph.
     * The index of a node in this list corresponds to its row and column
     * index in {@link #matrix}.
     */
    public ArrayList<GraphNode> nodes;
    private ArrayList<ArrayList<Boolean>> matrix;

    /**
     * Creates an empty adjacency-matrix graph.
     */
    GraphMatrix() {
        this.nodes = new ArrayList<>();
        this.matrix = new ArrayList<>();
    }

    @Override
    public void addNode(GraphNode node) {
        if (this.nodes.contains(node)) {
            return;
        }

        this.nodes.add(node);
        int n = this.nodes.size();
        for (ArrayList<Boolean> row : this.matrix) {
            row.add(false);
        }
        ArrayList<Boolean> newRow = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            newRow.add(false);
        }
        this.matrix.add(newRow);
    }

    @Override
    public void removeNode(GraphNode node) {
        int index = this.nodes.indexOf(node);

        if (index == -1) {
            return;
        }

        this.nodes.remove(index);
        this.matrix.remove(index);
        for (ArrayList<Boolean> m : this.matrix) {
            m.remove(index);
        }
    }

    @Override
    public void addEdge(GraphNode node1, GraphNode node2) {
        addNode(node1);
        addNode(node2);
        int index1 = this.nodes.indexOf(node1);
        int index2 = this.nodes.indexOf(node2);

        this.matrix.get(index1).set(index2, true);
    }

    @Override
    public void removeEdge(GraphNode node1, GraphNode node2) {
        int index1 = this.nodes.indexOf(node1);
        int index2 = this.nodes.indexOf(node2);

        if (index1 == -1 || index2 == -1) {
            return;
        }

        this.matrix.get(index1).set(index2, false);
    }

    @Override
    public ArrayList<GraphNode> getNeighbors(GraphNode node) {
        int index = this.nodes.indexOf(node);
        if (index == -1) {
            return new ArrayList<>();
        }

        ArrayList<GraphNode> ret = new ArrayList<>();

        ArrayList<Boolean> raw = this.matrix.get(index);
        for (int i = 0; i < raw.size(); i++) {
            if (raw.get(i)) {
                ret.add(this.nodes.get(i));
            }
        }
        return ret;
    }

    @Override
    public void clear() {
        this.nodes.clear();
        this.matrix.clear();
    }

    @Override
    public ArrayList<GraphNode> nodes() {
        return this.nodes;
    }
}
