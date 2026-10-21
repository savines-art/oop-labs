package sys.pro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GraphList extends Graph {
    public HashMap<GraphNode, ArrayList<GraphNode>> nodes;

    GraphList() {
        this.nodes = new HashMap<>();
    }

    @Override
    public void addNode(GraphNode node) {
        this.nodes.putIfAbsent(node, new ArrayList<>());
    }

    @Override
    public void removeNode(GraphNode node) {
        for (GraphNode n : this.nodes.keySet()) {
            removeEdge(n, node);
        }
        this.nodes.remove(node);

    }

    @Override
    public void addEdge(GraphNode node1, GraphNode node2) {
        addNode(node1);
        addNode(node2);

        if (!this.nodes.get(node1).contains(node2)) {
            this.nodes.get(node1).add(node2);
        }
    }

    @Override
    public void removeEdge(GraphNode node1, GraphNode node2) {
        if (this.nodes.get(node1) == null || this.nodes.get(node2) == null) {
            return;
        }

        this.nodes.get(node1).remove(node2);
    }

    @Override
    public ArrayList<GraphNode> getNeighbors(GraphNode node) {
        ArrayList<GraphNode> ret = this.nodes.get(node);
        if (ret == null) {
            return new ArrayList<>();
        }
        return ret;
    }

    @Override
    public void clear() {
        this.nodes.clear();
    }

    //todo: make static method

    @Override
    public ArrayList<GraphNode> nodes() {
        return new ArrayList<>(this.nodes.keySet());
    }
    //todo: make static or default
}
