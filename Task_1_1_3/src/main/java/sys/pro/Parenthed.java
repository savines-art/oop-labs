package sys.pro;

import java.util.HashMap;

/**
 * Expression wrapper that preserves explicit parentheses from the source text.
 */
public class Parenthed extends Expression {
    public Expression inner;

    /**
     * Constructs a parenthesized expression wrapper.
     *
     * @param expr the inner expression
     */
    Parenthed(Expression expr) {
        this.inner = expr;
    }

    /**
     * Returns the string representation with parentheses.
     *
     * @return a string in the form {@code "(inner)"}
     */
    @Override
    public String toString() {
        return "(" + this.inner.toString() + ")";
    }

    /**
     * Evaluates the inner expression using variable bindings.
     *
     * @param variables map from variable names to integer values
     * @return the result of evaluating the inner expression
     * @throws IllegalArgumentException if a variable is missing
     */
    @Override
    protected int eval(HashMap<String, Integer> variables) {
        return this.inner.eval(variables);
    }

    /**
     * Evaluates the inner expression without variable bindings.
     *
     * @return the result of evaluating the inner expression
     * @throws IllegalArgumentException if a variable is present
     */
    @Override
    protected int eval() {
        return this.inner.eval();
    }

    /**
     * Compares this parenthesized expression with another object for equality.
     *
     * @param obj the object to compare
     * @return {@code true} if {@code obj} is a {@code Parenthed} with an equal inner expression
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }
        return obj instanceof Parenthed && this.inner.equals(((Parenthed) obj).inner);
    }

    /**
     * Computes the derivative of the inner expression.
     *
     * @param var the variable to differentiate by
     * @return the derivative of the inner expression
     */
    @Override
    public Expression derivative(String var) {
        return this.inner.derivative(var);
    }

    /**
     * Simplifies the inner expression.
     *
     * @return the simplified inner expression
     */
    @Override
    public Expression simplify() {
        return this.inner.simplify();
    }
}
