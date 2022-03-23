package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * The enum Token type.
 *
 * @author franzekan
 */
public enum TokenType {
    /**
     * Text (outside of tag)
     */
    TEXT,

    /**
     * Tag open {$
     */
    TAG_OPEN,

    /**
     * Tag close $}
     */
    TAG_CLOSE,

    /**
     * Symbol =
     */
    SYMBOL,

    /**
     * Operator <code>+/-*^</code>
     */
    OPERATOR,

    /**
     * Function i.e. @sin
     */
    FUNCTION,

    /**
     * Command (only FOR is supported)
     */
    COMMAND,

    /**
     * String literal
     */
    STRING,

    /**
     * Variable
     */
    VARIABLE,

    /**
     * Integer literal
     */
    INTEGER,

    /**
     * Double literal
     */
    DOUBLE,

    /**
     * End of document
     */
    EOF
}
