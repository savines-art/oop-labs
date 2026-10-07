package sys.pro;

/**
 * Recursive-descent parser for symbolic arithmetic expressions.
 * Supports integer constants, variables, parentheses, and the binary
 * operators.
 */
public class Parser {
    private static String[] tokens;
    private static int sourceSize;
    private static int current = 0;

    /**
     * Parses a source string into an expression.
     *
     * @param source the expression string
     * @return the parsed expression
     */
    public static Expression parse(String source) {
        tokens = source.replace("(", "( ").replace(")", " )").trim().split(" +");
        sourceSize = tokens.length;
        Expression ret = expression();
        current = 0;
        return ret;
    }

    /**
     * Following methods build a formal grammar to parse ast.
     * Parses an expression (top-level rule, delegates to {@link #term()}).
     *
     * @return the parsed expression
     */
    private static Expression expression() {
        return term();
    }

    /**
     * Parses a term, handling addition and subtraction.
     *
     * @return the parsed term
     */
    private static Expression term() {
        Expression expr = factor();
        while(match("+", "-")) {
            String operator = previous();
            Expression right = factor();
            switch (operator) {
                case "+": {
                    expr = new Add(expr, right);
                    break;
                }

                case "-": {
                    expr = new Sub(expr, right);
                    break;
                }

                default: {
                    break;
                }
            }
        }

        return expr;
    }

    /**
     * Parses a factor, handling multiplication and division.
     *
     * @return the parsed factor
     */
    private static Expression factor() {
        Expression expr = primary();

        while(match("/", "*")) {
            String operator = previous();
            Expression right = primary();
            switch (operator) {
                case "/": {
                    expr = new Div(expr, right);
                    break;
                }

                case "*": {
                    expr = new Mul(expr, right);
                    break;
                }

                default: {
                    break;
                }
            }
        }

        return expr;
    }

    /**
     * Parses a primary expression: number, variable, or parenthesized expression.
     *
     * @return the parsed primary expression
     */
    private static Expression primary() {
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

    /**
     * Tries to match and consume one of the given tokens.
     *
     * @param tokens the tokens to match
     * @return {@code true} if one token matched and was consumed
     */
    private static boolean match(String... tokens) {
        for (String token : tokens) {
            if (check(token)) {
                advance();
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether the current token equals the given token.
     *
     * @param token the token to check
     * @return {@code true} if the current token matches
     */
    private static boolean check(String token) {
        return !isAtEnd() && peek().equals(token);
    }

    /**
     * Advances to the next token and returns the previous token.
     *
     * @return the previous token
     */
    private static String advance() {
        if (!isAtEnd()) {
            current++;
        }
        return previous();
    }

    /**
     * Checks whether the parser has reached the end of the token stream.
     *
     * @return {@code true} if no more tokens are available
     */
    private static boolean isAtEnd() {
        return current >= sourceSize;
    }

    /**
     * Returns the current token without consuming it.
     *
     * @return the current token
     */
    private static String peek() {
        return tokens[current];
    }

    /**
     * Returns the most recently consumed token.
     *
     * @return the previous token
     */
    private static String previous() {
        return tokens[current - 1];
    }
}
