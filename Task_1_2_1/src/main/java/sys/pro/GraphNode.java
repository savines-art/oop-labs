package sys.pro;

import java.util.Objects;

public class GraphNode {
    public final int value;

    GraphNode(int value) {
        this.value = value;
    }

    public int hashCode() {
        return Objects.hash(this.value);
    }

    public String toString() {
        return  "(" + this.value + ")";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        return obj instanceof GraphNode && ((GraphNode) obj).value == this.value;
    }
}
