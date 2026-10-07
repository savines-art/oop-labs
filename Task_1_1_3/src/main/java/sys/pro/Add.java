package sys.pro;

import java.util.HashMap;

/**
 * Expression node representing the addition of two expressions.
 */
public class Add extends BinaryExpr {
    /**
     * Constructs an addition expression.
     *
     * @param left the left operand
     * @param right the right operand
     */
    Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the string representation of this addition.
     *
     * @return a string in the form {@code "left + right"}
     */
    @Override
    public String toString() {
        return this.left.toString() + " + " + this.right.toString();
    }

    /**
     * Evaluates this addition using the given variable bindings.
     *
     * @param variables map from variable names to integer values
     * @return the sum of the evaluated operands
     * @throws IllegalArgumentException if a variable is missing
     */
    @Override
    protected int eval(HashMap<String, Integer> variables) {
        return this.left.eval(variables) + this.right.eval(variables);
    }

    /**
     * Evaluates this addition without variable bindings.
     *
     * @return the sum of the evaluated operands
     * @throws IllegalArgumentException if a variable is present
     */
    @Override
    protected int eval() {
        return this.left.eval() + this.right.eval();
    }

    /**
     * Compares this addition with another object for equality.
     *
     * @param obj the object to compare
     * @return {@code true} if {@code obj} is an {@code Add} with equal operands
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        return (obj instanceof Add) && this.left.equals(((Add) obj).left)
                && this.right.equals(((Add) obj).right);
    }

    /**
     * Computes the derivative of this addition.
     *
     * @param var the variable to differentiate by
     * @return sum of left and right derivatives.
     */
    @Override
    public Expression derivative(String var) {
        return new Add(this.left.derivative(var), this.right.derivative(var));
    }

    /**
     * Simplifies this addition.
     * Removes zero operands and folds constant operands.
     * @return the simplified expression
     */
    @Override
    public Expression simplify() {
        boolean hasVariables = false;
        Expression simpleLeft = this.left.simplify();
        Expression simpleRight = this.right.simplify();

        int newLeft = 0;
        int newRight = 0;

        try {
            newLeft = simpleLeft.eval();
            if (newLeft == 0) {
                return simpleRight;
            }
        } catch (RuntimeException gotVariable) {
            hasVariables = true;
        }

        try {
            newRight = simpleRight.eval();
            if (newRight == 0) {
                return simpleLeft;
            }
        } catch (RuntimeException gotVariable) {
            hasVariables = true;
        }

        if (hasVariables) {
            return new Add(simpleLeft, simpleRight);
        }

        return new Number(newLeft + newRight);
    }
}
