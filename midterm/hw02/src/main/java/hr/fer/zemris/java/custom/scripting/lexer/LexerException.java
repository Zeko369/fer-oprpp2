package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Custom exception thrown inside Lexer
 *
 * @author franzekan
 */
public class LexerException extends RuntimeException {
    /**
     * Instantiates a new Lexer exception.
     *
     * @param message the message
     */
    public LexerException(String message) {
        super(message);
    }
}
