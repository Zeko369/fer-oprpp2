package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Lexer state used to indicate to the lexer if it's used to parse the Tag or the general Text
 *
 * @author franzekan
 */
public enum LexerState {
    /**
     * Normal lexer state (outside tags)
     */
    NORMAL,

    /**
     * Tag lexer state (Inside tags)
     */
    TAG,
}
