package sys.pro;

import java.util.HashMap;

/**
 * Expression node representing an integer constant.
 */
public class Number extends Expression {
    public final int value;

    /**
     * Constructs a constant expression.
     *
     * @param value the integer value
     */
    Number(int value) {
        this.value = value;
    }

    /**
     * Returns the string representation of this constant.
     *
     * <p>Negative values are enclosed in parentheses.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        if (this.value < 0) {
            return "(" + this.value + ")";
        }
        return "" + this.value;
    }

    /**
     * Evaluates this constant using variable bindings (ignored).
     *
     * @param variables map from variable names to integer values
     * @return the constant value
     */
    @Override
    protected int eval(HashMap<String, Integer> variables) {
        return this.value;
    }

    /**
     * Compares this constant with another object for equality.
     *
     * @param obj the object to compare
     * @return {@code true} if {@code obj} is a {@code Number} with the same value
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        return obj instanceof Number && ((Number) obj).value == this.value;
    }

    /**
     * Computes the derivative of this constant.
     *
     * @param var the variable to differentiate by (ignored)
     * @return a new {@code Number} with value {@code 0}
     */
    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    /**
     * Simplifies this constant.
     *
     * @return this same constant
     */
    @Override
    public Expression simplify() {
        return this;
    }
}
