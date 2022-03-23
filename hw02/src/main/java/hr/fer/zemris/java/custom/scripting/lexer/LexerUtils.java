package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Helper for checking strings inside Lexer
 *
 * @author franzekan
 */
public class LexerUtils {
    /**
     * Checks if a char is a valid in variable name
     *
     * @param c the c
     * @return the boolean
     */
    public static boolean isVariable(char c) {
        return Character.isLetter(c) || Character.isDigit(c) || c == '_';
    }

    /**
     * Checks if a char is a number / .
     *
     * @param c character to check
     * @return the boolean
     */
    public static boolean isNumber(char c) {
        return Character.isDigit(c) || c == '.';
    }
}
