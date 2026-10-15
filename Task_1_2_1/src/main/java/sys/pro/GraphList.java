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

public class GraphList implements Graph {
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
        return this.nodes.get(node);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (GraphNode k : this.nodes.keySet()) {
            StringBuilder raw = new StringBuilder();
            raw.append(k.toString());
            raw.append(" -> ");
            for (GraphNode node : this.nodes.get(k)) {
                raw.append(node.toString());
                raw.append(", ");
            }
            raw.append("\n");
            ret.append(raw);
        }
        return ret.toString();
    }

    @Override
    public void clear() {
        this.nodes.clear();
    }

    @Override
    public void getGraph(String filename) throws InvalidPathException {
        Path path = Path.of(filename);

        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            clear();
            int number = Integer.parseInt(lines.getFirst().strip());
            for (String s : lines.get(1).split("\\s+")) {
                int value = Integer.parseInt(s.strip());
                GraphNode node = new GraphNode(value);
                addNode(node);
            }

            for (int i = 2; i < number; i++) {
                String[] l = lines.get(i).split("\\s+");
                GraphNode node = new GraphNode(Integer.parseInt(l[0]));
                for (int j = 1; j < l.length; j++) {
                    addEdge(node, new GraphNode(Integer.parseInt(l[j])));
                }
            }
        } catch (IOException err) {
            System.out.println("Caught error in input file.");
        }
    }

    @Override
    public int size() {
        return this.nodes.size();
    }

    @Override
    public ArrayList<GraphNode> nodes() {
        return new ArrayList<>(this.nodes.keySet());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Graph)) {
            return false;
        }

        if (obj instanceof GraphList) {
            if (!this.nodes.keySet().containsAll(((GraphList)obj).nodes.keySet())
                    || !((GraphList)obj).nodes.keySet().containsAll(this.nodes.keySet())) {
                return false;
            }

            for (GraphNode n : this.nodes.keySet()) {
                for (GraphNode k : ((GraphList)obj).nodes.keySet()) {
                    if (!k.equals(n)) {
                        continue;
                    }
                    ArrayList<GraphNode> neighbors1 = getNeighbors(n);
                    ArrayList<GraphNode> neighbors2 = ((GraphList)obj).getNeighbors(k);
                    if (!neighbors1.containsAll(neighbors2)
                            || !neighbors2.containsAll(neighbors1)) {
                        return false;
                    }
                }
            }
        } else if (obj instanceof GraphMatrix) {
            if (!this.nodes.keySet().containsAll(((GraphMatrix)obj).nodes) ||
                    !((GraphMatrix)obj).nodes.containsAll(this.nodes.keySet())) {
                return false;
            }

            for (GraphNode n : this.nodes.keySet()) {
                for (GraphNode k : ((GraphMatrix)obj).nodes) {
                    if (!k.equals(n)) {
                        continue;
                    }
                    ArrayList<GraphNode> neighbors1 = getNeighbors(n);
                    ArrayList<GraphNode> neighbors2 = ((GraphMatrix)obj).getNeighbors(k);
                    if (!neighbors1.containsAll(neighbors2)
                            || !neighbors2.containsAll(neighbors1)) {
                        return false;
                    }
                }
            }
        } else if (obj instanceof GraphInc) {
            for (GraphNode n : this.nodes.keySet()) {
                for (GraphNode k : ((GraphInc)obj).nodes) {
                    if (!k.equals(n)) {
                        continue;
                    }
                    ArrayList<GraphNode> neighbors1 = getNeighbors(n);
                    ArrayList<GraphNode> neighbors2 = ((GraphInc)obj).getNeighbors(k);
                    if (!neighbors1.containsAll(neighbors2)
                            || !neighbors2.containsAll(neighbors1)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
