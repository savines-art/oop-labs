package sys.pro;

import java.util.HashMap;

/**
 * Expression node representing a named variable.
 */
public class Variable extends Expression {
    public final String name;

    /**
     * Constructs a variable expression with the given name.
     *
     * @param lexeme the variable name
     */
    Variable(String lexeme) {
        this.name = lexeme;
    }

    /**
     * Returns the variable name.
     *
     * @return the variable name
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Evaluates this variable using the given bindings.
     *
     * @param variables map from variable names to integer values
     * @return the value bound to this variable's name
     * @throws IllegalArgumentException if the variable is not bound
     */
    @Override
    protected int eval(HashMap<String, Integer> variables) {
        if (variables.get(this.name) == null) {
            throw new IllegalArgumentException(
                    "Can't evaluate a variable without defining variables.");
        }
        return variables.get(this.name);
    }

    /**
     * Evaluates this variable without bindings.
     *
     * @return never returns normally
     * @throws IllegalArgumentException always, because a variable cannot be evaluated without bindings
     */
    @Override
    protected int eval() {
        throw new IllegalArgumentException(
                "Can't evaluate a variable without defining variables.");
    }

    /**
     * Compares this variable with another object for equality.
     *
     * @param obj the object to compare
     * @return {@code true} if {@code obj} is a {@code Variable} with the same name
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }
        return obj instanceof Variable
                && ((Variable) obj).name.equals(this.name);
    }

    /**
     * Computes the derivative of this variable with respect to the given variable.
     *
     * @param var the variable to differentiate by
     * @return 1 if var
     * equals this variable's name, otherwise 0.
     */
    @Override
    public Expression derivative(String var) {
        return var.equals(this.name) ? new Number(1) : new Number(0);
    }

    /**
     * Simplifies this variable.
     *
     * @return this same variable
     */
    @Override
    public Expression simplify() {
        return this;
    }
}
