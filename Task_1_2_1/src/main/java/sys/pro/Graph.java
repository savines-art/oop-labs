package sys.pro;

import java.nio.file.InvalidPathException;
import java.util.ArrayList;

public interface Graph {
    public abstract void addNode(GraphNode node);
    public abstract void removeNode(GraphNode node);
    public abstract void addEdge(GraphNode node1, GraphNode node2);
    public abstract void removeEdge(GraphNode node1, GraphNode node2);
    public abstract ArrayList<GraphNode> getNeighbors(GraphNode node);
    public abstract void clear();
    public abstract void getGraph(String filename) throws InvalidPathException;
}
