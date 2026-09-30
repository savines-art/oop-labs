package sys.pro;

import java.util.HashMap;

public class Add extends BinaryExpr {
    Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return this.left.toString() + " + " + this.right.toString();
    }

    @Override
    public int eval(HashMap<String, Integer> variables) {
        return this.left.eval(variables) + this.right.eval(variables);
    }

    @Override
    public int eval() {
        return this.left.eval() + this.right.eval();
    }

    @Override
    public boolean equals(Expression expr) {
        return (expr instanceof Add) && this.left.equals(((Add) expr).left) && this.right.equals(((Add) expr).right);
    }

    @Override
    public Expression derivative(String var) {
        return new Add(this.left.derivative(var), this.right.derivative(var));
    }

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
