package sys.pro;

import java.util.HashMap;

public class Number extends Expression {
    public final int value;

    Number(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (this.value < 0) {
            return "(" + this.value + ")";
        }
        return "" + this.value;
    }

    @Override
    public int eval(HashMap<String, Integer> variables) {
        return this.value;
    }

    @Override
    public int eval() {
        return this.value;
    }

    @Override
    public boolean equals(Expression expr) {
        return expr instanceof Number && ((Number) expr).value == this.value;
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    @Override
    public Expression simplify() {
        return this;
    }
}
