package sys.pro;

public class GraphEdge {
    GraphNode from;
    GraphNode to;

    GraphEdge(GraphNode from, GraphNode to) {
        this.from = from;
        this.to = to;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj instanceof GraphEdge && this.from.equals(((GraphEdge) obj).from) && this.to.equals(((GraphEdge) obj).to);
    }

    public boolean incident(GraphNode node) {
        return this.from.equals(node) || this.to.equals(node);
    }
}
