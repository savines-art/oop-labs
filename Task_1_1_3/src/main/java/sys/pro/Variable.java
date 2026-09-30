package sys.pro;

import java.util.HashMap;

public class Variable extends Expression {
    public final String name;

    Variable(String lexeme) {
        this.name = lexeme;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int eval(HashMap<String, Integer> variables) {
        return variables.get(this.name);
    }

    @Override
    public int eval() {
        throw new RuntimeException("Can't evaluate a variable without defining variables.");
    }

    @Override
    public boolean equals(Expression expr) {
        return expr instanceof Variable && ((Variable) expr).name.equals(this.name);
    }

    @Override
    public Expression derivative(String var) {
        return var.equals(this.name) ? new Number(1) : new Number(0);
    }

    @Override
    public Expression simplify() {
        return this;
    }
}
