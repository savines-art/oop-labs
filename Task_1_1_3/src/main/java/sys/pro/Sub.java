package sys.pro;

import java.util.HashMap;

public class Sub extends BinaryExpr {
    Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return this.left.toString() + " - " + this.right.toString();
    }

    @Override
    public int eval(HashMap<String, Integer> variables) {
        return this.left.eval(variables) - this.right.eval(variables);
    }

    @Override
    public int eval() {
        return this.left.eval() - this.right.eval();
    }

    @Override
    public boolean equals(Expression expr) {
        return (expr instanceof Sub) && this.left.equals(((Sub) expr).left) && this.right.equals(((Sub) expr).right);
    }

    @Override
    public Expression derivative(String var) {
        return new Sub(this.left.derivative(var), this.right.derivative(var));
    }

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
