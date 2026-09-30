package sys.pro;

public class Parser {
    public final String[] source;
    private final int sourceSize;
    private int current = 0;

    Parser(String source) {
        this.source = source.replace("(", "( ").replace(")", " )").split(" ");
        this.sourceSize = this.source.length;
    }

    public Expression parse() {
        return expression();
    }

    private Expression expression() {
        return term();
    }

    private Expression term() {
        Expression expr = factor();
        while(match("+", "-")) {
            String operator = previous();
            Expression right = factor();
            switch (operator) {
                case "+": expr = new Add(expr, right); break;

                case "-": expr = new Sub(expr, right); break;
            }
        }

        return expr;
    }

    private Expression factor() {
        Expression expr = primary();

        while(match("/", "*")) {
            String operator = previous();
            Expression right = primary();
            switch (operator) {
                case "/": expr = new Div(expr, right); break;

                case "*": expr = new Mul(expr, right); break;
            }
        }

        return expr;
    }

    private Expression primary() {
        if (match("(")) {
            Expression expr = expression();
            advance();
            return new Parenthed(expr);
        }

        if (peek().charAt(0) >= '0' && peek().charAt(0) <= '9') {
            advance();
            return new Number(Integer.parseInt(previous()));
        }

        return new Variable(advance());
    }

    private boolean match(String... tokens) {
        for (String token : tokens) {
            if (check(token)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private boolean check(String token) {
        return !isAtEnd() && peek().equals(token);
    }

    private String advance() {
        if (!isAtEnd()) {
            current++;
        }
        return previous();
    }

    private boolean isAtEnd() {
        return this.current >= this.sourceSize;
    }

    private String peek() {
        return this.source[current];
    }

    private  String previous() {
        return this.source[current - 1];
    }
}
