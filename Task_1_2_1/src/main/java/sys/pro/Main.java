package sys.pro;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Main class for demonstration.
 */
public class Main {
    /**
     * main method.
     * @param args strings of arguments.
     */
    public static void main(String[] args) {
        Graph graph = new GraphList();
        for (int i = 0; i < 100; i++) {
            graph.addNode(new GraphNode(i));
        }

        for (int i = 0; i < 90; i++) {
            graph.addEdge(new GraphNode(i), new GraphNode(i + 10));
            graph.addEdge(new GraphNode(i), new GraphNode(i + 5));
        }

        ArrayList<GraphNode> sorted = new ArrayList<>(TopSort.sort(graph));
        System.out.println(sorted.toString());
    }
}
