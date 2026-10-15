package sys.pro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GraphInc implements Graph {
    public ArrayList<GraphNode> nodes;
    public ArrayList<GraphEdge> edges;
    public ArrayList<ArrayList<Integer>> incidency;

    GraphInc() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.incidency = new ArrayList<>();
    }

    @Override
    public void addNode(GraphNode node) {
        if (!this.nodes.contains(node)) {
            this.nodes.add(node);
            this.incidency.add(new ArrayList<>(this.edges.size()));
        }
    }

    @Override
    public void removeNode(GraphNode node) {
        int index = this.nodes.indexOf(node);
        this.nodes.remove(index);
        for (GraphEdge edge : this.edges) {
            if (edge.from.equals(node)) {
                removeEdge(node, edge.to);
            } else if (edge.to.equals(node)) {
                removeEdge(edge.from, node);
            }
        }
    }

    @Override
    public void addEdge(GraphNode node1, GraphNode node2) {
        addNode(node1);
        addNode(node2);
        GraphEdge edge = new GraphEdge(node1, node2);
        this.edges.add(edge);
        int index1 = this.nodes.indexOf(node1);
        int index2 = this.nodes.indexOf(node2);
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

    @Override
    public void removeEdge(GraphNode node1, GraphNode node2) {
        GraphEdge edge = new GraphEdge(node1, node2);
        int index = this.edges.indexOf(edge);
        this.edges.remove(index);
        for (int i = 0; i < this.nodes.size(); i++) {
            this.incidency.get(i).remove(index);
        }
    }

    @Override
    public ArrayList<GraphNode> getNeighbors(GraphNode node) {
        ArrayList<GraphNode> ret = new ArrayList<>();
        for (GraphEdge edge : this.edges) {
            if (edge.from.equals(node)) {
                ret.add(edge.to);
            }
        }
        return ret;
    }

    @Override
    public void clear() {
        this.nodes.clear();
        this.edges.clear();
        this.incidency.clear();
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
        return this.nodes;
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
            if (!this.nodes.containsAll(((GraphList)obj).nodes.keySet())
                    || !((GraphList)obj).nodes.keySet().containsAll(this.nodes)) {
                return false;
            }

            for (GraphNode n : this.nodes) {
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
            if (!this.nodes.containsAll(((GraphMatrix)obj).nodes) ||
                    !((GraphMatrix)obj).nodes.containsAll(this.nodes)) {
                return false;
            }

            for (GraphNode n : this.nodes) {
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
            if (!this.nodes.containsAll(((GraphInc)obj).nodes) ||
                    !((GraphInc)obj).nodes.containsAll(this.nodes)) {
                return false;
            }

            for (GraphNode n : this.nodes) {
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
