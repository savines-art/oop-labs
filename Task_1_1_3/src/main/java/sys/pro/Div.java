package sys.pro;

import java.util.HashMap;

/**
 * Expression node representing the division of two expressions.
 */
public class Div extends BinaryExpr {
    /**
     * Constructs a division expression.
     *
     * @param left the left operand (dividend)
     * @param right the right operand (divisor)
     */
    Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the string representation of this division.
     *
     * @return a string in the form {@code "left / right"}
     */
    @Override
    public String toString() {
        return this.left.toString() + " / " + this.right.toString();
    }

    /**
     * Evaluates this division using the given variable bindings.
     *
     * @param variables map from variable names to integer values
     * @return the quotient of the evaluated operands
     * @throws IllegalArgumentException if a variable is missing
     * @throws ArithmeticException if the divisor evaluates to zero
     */
    @Override
    protected int eval(HashMap<String, Integer> variables) {
        return this.left.eval(variables) / this.right.eval(variables);
    }

    /**
     * Compares this division with another object for equality.
     *
     * @param obj the object to compare
     * @return {@code true} if {@code obj} is a {@code Div} with equal operands
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof Div) && this.left.equals(((Div) obj).left)
                && this.right.equals(((Div) obj).right);
    }

    /**
     * Computes the derivative of this division using the quotient rule.
     *
     * @param var the variable to differentiate by
     * @return the derivative expression
     */
    @Override
    public Expression derivative(String var) {
        return new Div(new Sub(new Mul(this.left.derivative(var), this.right),
                new Mul(this.left, this.right.derivative(var))),
                new Mul(this.right, this.right));
    }

    /**
     * Simplifies this division.
     * Removes a divisor of one and folds constant operands.
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

        } catch (IllegalArgumentException gotVariable) {
            hasVariables = true;
        }

        try {
            newRight = simpleRight.eval();
            if (newRight == 1) {
                return simpleLeft;
            }
        } catch (IllegalArgumentException gotVariable) {
            hasVariables = true;
        }

        if (hasVariables) {
            return new Div(simpleLeft, simpleRight);
        }

        return new Number(newLeft / newRight);
    }
}
