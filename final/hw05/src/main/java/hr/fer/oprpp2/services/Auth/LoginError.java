package hr.fer.oprpp2.services.Auth;


/**
 * The enum Login error.
 *
 * @author franzekan
 */
public enum LoginError {
    /**
     * No error
     */
    OK,
    /**
     * User not found login error.
     */
    USER_NOT_FOUND,
    /**
     * Wrong password login error.
     */
    WRONG_PASSWORD,
    /**
     * Error login error.
     */
    ERROR
}
