package hr.fer.oprpp2.services.Auth;


/**
 * The enum Register error.
 *
 * @author franzekan
 */
public enum RegisterError {
    /**
     * No error
     */
    OK,
    /**
     * Email in use register error.
     */
    EMAIL_IN_USE,
    /**
     * Username in use register error.
     */
    USERNAME_IN_USE,
    /**
     * Error register error.
     */
    ERROR
}
