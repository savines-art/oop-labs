package sys.pro;

import java.util.Objects;

public class GraphNode {
    public final int value;

    /**
     * Creates a graph node with the specified integer value.
     *
     * @param value the value associated with this node
     */
    GraphNode(int value) {
        this.value = value;
    }

    /**
     * Computes the hash code for this vertex based on its name.
     *
     * @return the hash code value for this vertex
     */
    public int hashCode() {
        return Objects.hash(this.value);
    }

    /**
     * Returns a string representation of this node in the form {@code (value)}.
     *
     * @return a string representation of this node
     */
    public String toString() {
        return  "(" + this.value + ")";
    }

    /**
     * Compares this node with another object for equality.
     * <p>
     * Two nodes are considered equal if they are {@code GraphNode} instances
     * with the same integer value.
     *
     * @param obj the object to compare with
     * @return {@code true} if the specified object is a {@code GraphNode}
     *         with the same value; {@code false} otherwise
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        return obj instanceof GraphNode && ((GraphNode) obj).value == this.value;
    }
}
