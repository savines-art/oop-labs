package sys.pro;

import java.util.HashMap;

public class Parenthed extends Expression {
    public Expression inner;

    Parenthed(Expression expr) {
        this.inner = expr;
    }

    @Override
    public String toString() {
        return "(" + this.inner.toString() + ")";
    }

    @Override
    public int eval(HashMap<String, Integer> variables) {
        return this.inner.eval(variables);
    }

    @Override
    public int eval() {
        return this.inner.eval();
    }

    @Override
    public boolean equals(Expression expr) {
        return expr instanceof Parenthed && this.inner.equals(((Parenthed) expr).inner);
    }

    @Override
    public Expression derivative(String var) {
        return this.inner.derivative(var);
    }

    @Override
    public Expression simplify() {
        return this.inner.simplify();
    }
}
