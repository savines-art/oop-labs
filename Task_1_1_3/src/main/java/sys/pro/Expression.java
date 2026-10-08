package sys.pro;

import java.util.HashMap;

/**
 * Abstract base class for all symbolic arithmetic expressions.
 */
public abstract class Expression {
    /**
     * Returns a string representation of this expression.
     *
     * @return the string representation
     */
    public abstract String toString();

    /**
     * Evaluates this expression without variable bindings.
     *
     * @return the integer result
     * @throws IllegalArgumentException if the expression contains a variable
     */
    protected int eval() {
        return this.eval(new HashMap<>());
    }

    /**
     * Evaluates this expression using the given variable bindings.
     *
     * @param variables map from variable names to integer values
     * @return the integer result
     * @throws IllegalArgumentException if a variable in the expression is missing from the map
     */
    protected abstract int eval(HashMap<String, Integer> variables);

    /**
     * Evaluates this expression using a string of variable assignments.
     * The string format is {@code "name = value"} pairs separated by semicolons,
     * for example {@code "x = 1; y = 2"}. An empty string means no variables.
     * @param vars variable assignments, or an empty string
     * @return the integer result
     * @throws IllegalArgumentException if the string is a variable is missing
     */
    public int eval(String vars) {
        if (vars.isEmpty()) {
            return this.eval();
        }
        String[] varList = vars.split(";");
        HashMap<String, Integer> map = new HashMap<>();
        for (String str : varList) {
            String[] l = str.strip().split(" ");
            map.put(l[0], Integer.parseInt(l[2]));
        }
        return eval(map);
    }

    /**
     * Computes the derivative of this expression with respect to the given variable.
     *
     * @param var the variable to differentiate by
     * @return the derivative expression
     */
    public abstract Expression derivative(String var);

    /**
     * Simplifies this expression.
     *
     * @return the simplified expression
     */
    public abstract Expression simplify();

    /**
     * Prints the string representation of this expression to standard output.
     */
    public void print() {
        System.out.println(this.toString());
    }


}
