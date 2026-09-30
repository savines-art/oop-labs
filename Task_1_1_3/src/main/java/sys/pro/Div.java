package sys.pro;

import java.util.HashMap;

public class Div extends BinaryExpr {
    Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return this.left.toString() + " / " + this.right.toString();
    }

    @Override
    public int eval(HashMap<String, Integer> variables) {
        return this.left.eval(variables) / this.right.eval(variables);
    }

    @Override
    public int eval() {
        return this.left.eval() / this.right.eval();
    }

    @Override
    public boolean equals(Expression expr) {
        return (expr instanceof Div) && this.left.equals(((Div) expr).left) && this.right.equals(((Div) expr).right);
    }

    @Override
    public Expression derivative(String var) {
        return new Div(new Sub(new Mul(this.left.derivative(var), this.right),
                new Mul(this.left, this.right.derivative(var))),
                new Mul(this.right, this.right));
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
            newRight = simpleRight.eval();
            if (newRight == 1) {
                return simpleLeft;
            }
        } catch (RuntimeException gotVariable) {
            hasVariables = true;
        }

        if (hasVariables) {
            return new Mul(simpleLeft, simpleRight);
        }

        return new Number(newLeft / newRight);
    }
}
