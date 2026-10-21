package sys.pro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GraphInc extends Graph {
    public ArrayList<GraphNode> nodes;
    public ArrayList<ArrayList<Integer>> incidency;

    GraphInc() {
        this.nodes = new ArrayList<>();
        this.incidency = new ArrayList<>();
    }

    @Override
    public void addNode(GraphNode node) {
        if (!this.nodes.contains(node)) {

            int s = this.incidency.isEmpty() ? 0 : this.incidency.getFirst().size();

            ArrayList<Integer> row = new ArrayList<>(s);
            for (int i = 0; i < s; i++) row.add(0);

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
            if (row.get(col) != 0) {
                for (ArrayList<Integer> r : this.incidency) r.remove(col);
            }
        }
        this.incidency.remove(index);
        this.nodes.remove(index);
    }

    //todo: make enum or static fields
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
                    this.incidency.get(i).add(2);
                } else {
                    if (i == index1) {
                        this.incidency.get(i).add(-1);
                    } else {
                        this.incidency.get(i).add(1);
                    }
                }

            } else {
                this.incidency.get(i).add(0);
            }

        }
    }

    public int findColumn(int nodeIndex1, int nodeIndex2) {
        if (nodeIndex1 == -1 || nodeIndex2 == -1) return -1;

        ArrayList<Integer> raw1 = this.incidency.get(nodeIndex1);
        ArrayList<Integer> raw2 = this.incidency.get(nodeIndex2);

        int s = raw1.size();

        for (int i = 0; i < s; i++) {
            if ((raw1.get(i) == -1 && raw2.get(i) == 1) || (nodeIndex1 == nodeIndex2 && raw1.get(i) == 2)) {
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

        for (int i = 0; i < this.nodes.size(); i++) {
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
            if (v == 2) {
                ret.add(node);
            } else if (v == -1) {
                for (int r = 0; r < this.nodes.size(); r++) {
                    if (this.incidency.get(r).get(col) == 1) {
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
