package sys.pro;

/**
 * Abstract base class for expressions that combine exactly two operands.
 * Subclasses inherit the {@code left} and {@code right} child expressions as operands.
 */
public abstract class BinaryExpr extends Expression {
    public Expression left;
    public Expression right;
}
