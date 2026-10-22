package sys.pro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a graph data structure.
 * A graph consists of {@link GraphNode} objects and directed edges between them.
 * Implementations may store the graph using different internal representations,
 * such as an adjacency list, adjacency matrix, or incidence matrix.
 */
public interface Graph {
    /**
     * Adds the specified node to this graph.
     * If the node is already present, the graph is left unchanged.
     *
     * @param node the node to add
     */
    void addNode(GraphNode node);

    /**
     * Removes the specified node and all edges incident to it from this graph.
     * If the node is not present, the graph is left unchanged.
     *
     * @param node the node to remove
     */
    void removeNode(GraphNode node);

    /**
     * Adds a directed edge from {@code node1} to {@code node2}.
     * If either node is not present, implementations may add it automatically.
     *
     * @param node1 the source node of the edge
     * @param node2 the target node of the edge
     */
    void addEdge(GraphNode node1, GraphNode node2);

    /**
     * Removes the directed edge from {@code node1} to {@code node2}.
     * If the edge or either node does not exist, the graph is left unchanged.
     *
     * @param node1 the source node of the edge
     * @param node2 the target node of the edge
     */
    void removeEdge(GraphNode node1, GraphNode node2);

    /**
     * Returns the neighbors of the specified node, the nodes reachable
     * from it by outgoing edges.
     * If the specified node is not present in this graph, an empty list is returned.
     *
     * @param node the node whose neighbors are requested
     * @return a list of neighbor nodes
     */
    ArrayList<GraphNode> getNeighbors(GraphNode node);

    /**
     * Removes all nodes and edges from this graph.
     */
    void clear();

    /**
     * Replaces the contents of this graph with a graph read from the specified file.
     * The default implementation clears the current graph, reads the number of
     * entries from the first line, node values from the second line, and edge
     * definitions from the remaining lines.
     * File format: in the first line written number of nodes.
     * Second line: written values of all nodes in graph.
     * Remaining lines: first number is value of node and next ones are values of its neighbors.
     *
     * @param filename the path to the file containing the graph description
     * @throws InvalidPathException if the specified filename cannot be converted
     *                              to a valid path
     */
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

    /**
     * Returns the number of nodes in this graph.
     *
     * @return the number of nodes
     */
    int size();

    /**
     * Returns all nodes contained in this graph.
     *
     * @return a list of nodes in this graph
     */
    ArrayList<GraphNode> nodes();
}
