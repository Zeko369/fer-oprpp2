package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class used to model a token returned from the Lexer
 *
 * @author franzekan
 */
public class Token {
    private final TokenType type;
    private final Object value;

    /**
     * Instantiates a new Token.
     *
     * @param type  the type
     * @param value the value
     */
    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Type getter
     *
     * @return the type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Value getter
     *
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return switch (this.type) {
            case EOF -> "(EOF)";
            case TAG_OPEN, TAG_CLOSE -> String.format("(%s)", this.type);
            default -> String.format("(%s, '%s')", this.type, this.value.toString().replace("\n", "\\n"));
        };
    }
}
