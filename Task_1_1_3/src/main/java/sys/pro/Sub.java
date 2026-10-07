package sys.pro;

import java.util.HashMap;

/**
 * Expression node representing the subtraction of two expressions.
 */
public class Sub extends BinaryExpr {
    /**
     * Constructs a subtraction expression.
     *
     * @param left the left operand
     * @param right the right operand
     */
    Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the string representation of this subtraction.
     *
     * @return a string in the form {@code "left - right"}
     */
    @Override
    public String toString() {
        return this.left.toString() + " - " + this.right.toString();
    }

    /**
     * Evaluates this subtraction using the given variable bindings.
     *
     * @param variables map from variable names to integer values
     * @return the difference of the evaluated operands
     * @throws IllegalArgumentException if a variable is missing
     */
    @Override
    protected int eval(HashMap<String, Integer> variables) {
        return this.left.eval(variables) - this.right.eval(variables);
    }

    /**
     * Evaluates this subtraction without variable bindings.
     *
     * @return the difference of the evaluated operands
     * @throws IllegalArgumentException if a variable is present
     */
    @Override
    protected int eval() {
        return this.left.eval() - this.right.eval();
    }

    /**
     * Compares this subtraction with another object for equality.
     *
     * @param obj the object to compare
     * @return {@code true} if {@code obj} is a {@code Sub} with equal operands
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }
        return (obj instanceof Sub)
                && this.left.equals(((Sub) obj).left) && this.right.equals(((Sub) obj).right);
    }

    /**
     * Computes the derivative of this subtraction.
     *
     * @param var the variable to differentiate by.
     * @return sub of left and right derivatives.
     */
    @Override
    public Expression derivative(String var) {
        return new Sub(this.left.derivative(var), this.right.derivative(var));
    }

    /**
     * Simplifies this subtraction.
     * Detects equal operands, removes zero operands, and folds constant operands.
     * @return the simplified expression
     */
    @Override
    public Expression simplify() {
        boolean hasVariables = false;
        Expression simpleLeft = this.left.simplify();
        Expression simpleRight = this.right.simplify();

        if (simpleLeft.equals(simpleRight)) {
            return new Number(0);
        }

        int newLeft = 0;
        int newRight = 0;

        try {
            newLeft = simpleLeft.eval();
            newRight = simpleRight.eval();
        } catch (RuntimeException gotVariable) {
            hasVariables = true;
        }


        if (hasVariables) {
            return new Sub(simpleLeft, simpleRight);
        }

        return new Number(newLeft - newRight);
    }
}
