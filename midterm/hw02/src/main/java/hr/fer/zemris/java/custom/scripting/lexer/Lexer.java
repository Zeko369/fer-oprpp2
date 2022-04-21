package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.function.Predicate;

/**
 * Lexer for our custom scripting PHP like language
 *
 * @author franzekan
 */
public class Lexer {
    /**
     * Current index in the input array
     */
    private int index = 0;

    /**
     * Input array
     */
    private final char[] data;

    /**
     * Current lexer state
     */
    private LexerState state = LexerState.NORMAL;

    /**
     * Last parsed token
     */
    private Token token = null;

    /**
     * Valid symbols that are parsed as operators
     */
    private static final char[] VALID_OPERATORS = {'+', '-', '/', '*', '^'};

    /**
     * Instantiates a new Lexer.
     *
     * @param str the str
     * @throws NullPointerException if str is null
     */
    public Lexer(String str) {
        if (str == null) {
            throw new NullPointerException();
        }

        this.data = str.toCharArray();
    }

    /**
     * Checks if the end has been reached
     *
     * @return if this.index is done
     */
    private boolean isEnd() {
        return this.index >= this.data.length;
    }

    /**
     * Gets current state
     *
     * @return the state
     */
    public LexerState getState() {
        return state;
    }

    /**
     * Helper function used to create a new token, set it to the last one and return it
     *
     * @param type  Type of the new token
     * @param value Value for the new token
     * @return new Token
     */
    private Token setToken(TokenType type, Object value) {
        this.token = new Token(type, value);
        return this.token;
    }

    /**
     * Helper used to skip empty spaces
     */
    private void skipSpace() {
        if (this.getCurrent() != ' ') return;
        while (this.getCurrent() == ' ') {
            this.index++;
        }
    }

    /**
     * Helper that returns all characters till the test is no longer satisfied
     *
     * @param test Predicate used to test each character
     * @return string from currentIndex till the test is no longer satisfied
     */
    private String tillNewType(Predicate<Character> test) {
        StringBuilder sb = new StringBuilder();
        while (!this.isEnd() && test.test(this.getCurrent()) && this.getCurrent() != ' ' && !this.isStartOfTag()) {
            sb.append(this.getCurrentAndMove());
        }

        return sb.toString();
    }

    /**
     * Helper that returns all characters till it reaches the <code>tillChar</code>
     *
     * @param tillChar char that will end the string
     * @return string from currentIndex till <code>tillChar</code>
     */
    private String getTillChar(char tillChar) {
        StringBuilder sb = new StringBuilder();
        while (!this.isEnd()) {
            if (this.getCurrent() == '$' || (this.getCurrent() == tillChar && this.hasLast() && this.getLast() != '\\')) {
                break;
            }

            sb.append(this.getCurrentAndMove());
        }

        return sb.toString();
    }

    private String getTillSpace() {
        return getTillChar(' ');
    }

    private boolean hasLast() {
        return this.index > 0;
    }

    private char getLast() {
        return this.data[this.index - 1];
    }

    private char getCurrent() {
        return this.data[this.index];
    }

    private char getCurrentAndMove() {
        return this.data[this.index++];
    }

    private char getNext() {
        return this.data[this.index + 1];
    }

    private boolean isStartOfTag() {
        return this.getCurrent() == '{' && (!this.hasLast() || this.getLast() != '\\') && this.getNext() == '$';
    }

    private boolean isOperator(char op) {
        for (char c : VALID_OPERATORS) {
            if (c == op) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets next token.
     *
     * @return the next token
     */
    public Token getNextToken() {
        if (this.isEnd()) {
            return this.setToken(TokenType.EOF, null);
        }

        if (this.state == LexerState.TAG) {
            this.skipSpace();

            // COMMAND
            if (this.token.getType().equals(TokenType.TAG_OPEN)) {
                if (this.getCurrent() == '=') {
                    return this.setToken(TokenType.SYMBOL, this.getCurrentAndMove());
                }

                return this.setToken(TokenType.COMMAND, this.getTillSpace().toUpperCase());
            }

            // END
            if (this.getCurrent() == '$') {
                this.state = LexerState.NORMAL;
                this.index++;
                this.index++;

                return this.setToken(TokenType.TAG_CLOSE, "$}");
            }

            // FUNCTION
            if (this.getCurrent() == '@') {
                this.index++;
                String tmp = this.tillNewType(LexerUtils::isVariable);
                this.index++;
                return this.setToken(TokenType.FUNCTION, tmp);
            }

            // STRING LITERAL
            if (this.getCurrent() == '"') {
                this.index++;
                String tmp = this.getTillChar('"');
                this.index++;

                return this.setToken(TokenType.STRING, tmp);
            }

            // NUMBERS
            if ((this.getCurrent() == '-' && Character.isDigit(this.getNext())) || Character.isDigit(this.getCurrent())) {
                boolean negate = this.getCurrent() == '-';
                if (negate) this.index++;

                String tmp = this.tillNewType(LexerUtils::isNumber);
                try {
                    if (tmp.contains(".")) {
                        double d = Double.parseDouble(tmp);
                        if (negate) d *= -1;
                        return this.setToken(TokenType.DOUBLE, d);
                    } else {
                        int d = Integer.parseInt(tmp);
                        if (negate) d *= -1;
                        return this.setToken(TokenType.INTEGER, d);
                    }
                } catch (NumberFormatException ex) {
                    throw new LexerException("Number parse error");
                }
            }

            // OPERATOR
            if (this.isOperator(this.getCurrent())) {
                return this.setToken(TokenType.OPERATOR, String.valueOf(this.getCurrentAndMove()));
            }

            // VARIABLE
            if (!Character.isLetter(this.getCurrent())) {
                throw new LexerException("Unexpected character");
            }

            String tmp = this.tillNewType(LexerUtils::isVariable).trim();
            return this.setToken(TokenType.VARIABLE, tmp);
        }

        if (isStartOfTag()) {
            this.state = LexerState.TAG;
            this.index++;
            this.index++;

            return this.setToken(TokenType.TAG_OPEN, "{$");
        } else {
            StringBuilder sb = new StringBuilder();
            while (!this.isEnd() && !this.isStartOfTag()) {
                if (this.hasLast() && this.getLast() == '\\' && this.getCurrent() == 'n') {
                    throw new LexerException("Wild \\n inside of Text");
                }

                sb.append(this.getCurrentAndMove());
            }

            return this.setToken(TokenType.TEXT, sb.toString());
        }
    }

    /**
     * Gets current (last found) token
     *
     * @return the current token
     */
    public Token getCurrentToken() {
        return this.token;
    }
}
