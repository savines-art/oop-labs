package sys.pro;

import java.util.ArrayList;

public class GraphInc extends AbstractGraph {
    public ArrayList<GraphNode> nodes;
    public ArrayList<ArrayList<Integer>> incidency;
    private static final int BEGIN = -1;
    private static final int END = 1;
    private static final int SELF = 2;
    private static final int NOTHING = 0;

    GraphInc() {
        this.nodes = new ArrayList<>();
        this.incidency = new ArrayList<>();
    }

    @Override
    public void addNode(GraphNode node) {
        if (!this.nodes.contains(node)) {

            int s = this.incidency.isEmpty() ? 0 : this.incidency.getFirst().size();

            ArrayList<Integer> row = new ArrayList<>(s);
            for (int i = 0; i < s; i++) {
                row.add(NOTHING);
            }

            this.nodes.add(node);
            this.incidency.add(row);
        }
    }

    @Override
    public void removeNode(GraphNode node) {
        int index = this.nodes.indexOf(node);
        if (index == -1) {
            return;
        }

        ArrayList<Integer> row = this.incidency.get(index);
        for (int col = row.size() - 1; col >= 0; col--) {
            if (row.get(col) != NOTHING) {
                for (ArrayList<Integer> r : this.incidency) {
                    r.remove(col);
                }
            }
        }
        this.incidency.remove(index);
        this.nodes.remove(index);
    }

    @Override
    public void addEdge(GraphNode node1, GraphNode node2) {
        addNode(node1);
        addNode(node2);
        int index1 = this.nodes.indexOf(node1);
        int index2 = this.nodes.indexOf(node2);

        if (findColumn(index1, index2) != -1) {
            return;
        }

        for (int i = 0; i < this.incidency.size(); i++) {
            if (i == index1 || i == index2) {
                if (index1 == index2) {
                    this.incidency.get(i).add(SELF);
                } else {
                    if (i == index1) {
                        this.incidency.get(i).add(BEGIN);
                    } else {
                        this.incidency.get(i).add(END);
                    }
                }

            } else {
                this.incidency.get(i).add(NOTHING);
            }

        }
    }

    /**
     * Finds the incidence-matrix column that represents the directed edge
     * from the first specified node to the second specified node.
     * <p>
     * If both indexes refer to the same node, this method searches for a
     * self-loop column.
     *
     * @param nodeIndex1 the index of the source node
     * @param nodeIndex2 the index of the target node
     * @return the column index representing the edge, or {@code -1} if no such
     *         edge exists or if either node index is invalid
     */
    private int findColumn(int nodeIndex1, int nodeIndex2) {
        if (nodeIndex1 == -1 || nodeIndex2 == -1) return -1;

        ArrayList<Integer> raw1 = this.incidency.get(nodeIndex1);
        ArrayList<Integer> raw2 = this.incidency.get(nodeIndex2);

        int s = raw1.size();

        for (int i = 0; i < s; i++) {
            if ((raw1.get(i) == BEGIN && raw2.get(i) == END) || (nodeIndex1 == nodeIndex2 && raw1.get(i) == SELF)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void removeEdge(GraphNode node1, GraphNode node2) {
        int index1 = this.nodes.indexOf(node1);
        int index2 = this.nodes.indexOf(node2);

        if (index1 == -1 || index2 == -1) {
            return;
        }

        int index = this.findColumn(index1, index2);

        if (index == -1) {
            return;
        }

        for (int i = 0; i < this.size(); i++) {
            this.incidency.get(i).remove(index);
        }
    }

    @Override
    public ArrayList<GraphNode> getNeighbors(GraphNode node) {
        ArrayList<GraphNode> ret = new ArrayList<>();
        int index = this.nodes.indexOf(node);

        if (index == -1) {
            return ret;
        }

        ArrayList<Integer> row = this.incidency.get(index);
        for (int col = 0; col < row.size(); col++) {
            int v = row.get(col);
            if (v == SELF) {
                ret.add(node);
            } else if (v == BEGIN) {
                for (int r = 0; r < this.nodes.size(); r++) {
                    if (this.incidency.get(r).get(col) == END) {
                        ret.add(this.nodes.get(r));
                        break;
                    }
                }
            }
        }

        return ret;
    }

    @Override
    public void clear() {
        this.nodes.clear();
        this.incidency.clear();
    }

    @Override
    public ArrayList<GraphNode> nodes() {
        return this.nodes;
    }
}
