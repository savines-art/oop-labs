package sys.pro;

import java.util.HashMap;

public abstract class Expression {
    public abstract String toString();
    public abstract int eval();
    public abstract int eval(HashMap<String, Integer> variables);
    public abstract boolean equals(Expression expr);
    public abstract Expression derivative(String var);
    public abstract Expression simplify();

    public void print() {
        System.out.println(this.toString());
    }

    public int eval(String vars) {
        if (vars.isEmpty()) {
            return this.eval();
        }
        String[] varList = vars.split(";");
        HashMap<String, Integer> map = new HashMap<>();
        for (String str : varList) {
            String[] l = str.strip().split(" ");
            map.put(l[0], Integer.parseInt(l[2]));
        }
        return eval(map);
    }
}
