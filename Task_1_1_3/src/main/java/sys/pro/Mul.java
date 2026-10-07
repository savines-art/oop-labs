package sys.pro;

import java.util.HashMap;

/**
 * Expression node representing the multiplication of two expressions.
 */
public class Mul extends BinaryExpr {
    /**
     * Constructs a multiplication expression.
     *
     * @param left the left operand
     * @param right the right operand
     */
    Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the string representation of this multiplication.
     *
     * @return a string in the form {@code "left * right"}
     */
    @Override
    public String toString() {
        return this.left.toString() + " * " + this.right.toString();
    }

    /**
     * Evaluates this multiplication using the given variable bindings.
     *
     * @param variables map from variable names to integer values
     * @return the product of the evaluated operands
     * @throws IllegalArgumentException if a variable is missing
     */
    @Override
    protected int eval(HashMap<String, Integer> variables) {
        return this.left.eval(variables) * this.right.eval(variables);
    }

    /**
     * Evaluates this multiplication without variable bindings.
     *
     * @return the product of the evaluated operands
     * @throws IllegalArgumentException if a variable is present
     */
    @Override
    protected int eval() {
        return this.left.eval() * this.right.eval();
    }

    /**
     * Compares this multiplication with another object for equality.
     *
     * @param obj the object to compare
     * @return {@code true} if {@code obj} is a {@code Mul} with equal operands
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }
        return (obj instanceof Mul) && this.left.equals(((Mul) obj).left)
                && this.right.equals(((Mul) obj).right);
    }

    /**
     * Computes the derivative of this multiplication.
     *
     * @param var the variable to differentiate by
     * @return the derivative expression
     */
    @Override
    public Expression derivative(String var) {
        return new Add(new Mul(this.left.derivative(var), this.right),
                new Mul(this.left, this.right.derivative(var)));
    }

    /**
     * Simplifies this multiplication.
     * Removes factors of zero and one and folds constant operands.
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
                return new Number(0);
            } else if (newLeft == 1) {
                return simpleRight;
            }
        } catch (RuntimeException gotVariable) {
            hasVariables = true;
        }

        try {
            newRight = simpleRight.eval();
            if (newRight == 0) {
                return new Number(0);
            } else if (newRight == 1) {
                return simpleLeft;
            }
        } catch (RuntimeException gotVariable) {
            hasVariables = true;
        }

        if (hasVariables) {
            return new Mul(simpleLeft, simpleRight);
        }

        return new Number(newLeft * newRight);
    }
}
