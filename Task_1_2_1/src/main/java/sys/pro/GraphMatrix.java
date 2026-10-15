package sys.pro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GraphMatrix implements Graph {
    public ArrayList<GraphNode> nodes;
    private ArrayList<ArrayList<Boolean>> matrix;

    GraphMatrix() {
        this.nodes = new ArrayList<>();
        this.matrix = new ArrayList<>();
    }

    @Override
    public void addNode(GraphNode node) {
        if (!this.nodes.contains(node)) {
            this.nodes.add(node);
        }
        for (int i = 0; i < this.matrix.size(); i++) {
            this.matrix.get(i).add(false);
        }
        this.matrix.add(new ArrayList<>(this.nodes.size()));
    }

    @Override
    public void removeNode(GraphNode node) {
        int index = this.nodes.indexOf(node);
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
            return null;
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
