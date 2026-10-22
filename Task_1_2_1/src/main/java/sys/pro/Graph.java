package sys.pro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public interface Graph {
    //todo: for tests: make hierarchy

    void addNode(GraphNode node);
    void removeNode(GraphNode node);
    void addEdge(GraphNode node1, GraphNode node2);
    void removeEdge(GraphNode node1, GraphNode node2);
    ArrayList<GraphNode> getNeighbors(GraphNode node);
    void clear();

    default void crateGraphFromFile(String filename) throws InvalidPathException {
        Path path = Path.of(filename);

        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            clear();
            int number = Integer.parseInt(lines.getFirst().strip());
            for (String s : lines.get(1).strip().split("\\s+")) {
                int value = Integer.parseInt(s.strip());
                GraphNode node = new GraphNode(value);
                addNode(node);
            }

            for (int i = 2; i < number; i++) {
                String[] l = lines.get(i).strip().split("\\s+");
                GraphNode node = new GraphNode(Integer.parseInt(l[0]));
                for (int j = 1; j < l.length; j++) {
                    addEdge(node, new GraphNode(Integer.parseInt(l[j])));
                }
            }
        } catch (IOException err) {
            System.out.println("Caught error in input file.");
        }
    }
    int size();
    ArrayList<GraphNode> nodes();
}
