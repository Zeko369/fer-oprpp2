package hr.fer.oprpp2.services.Auth;

/**
 * The type Auth result.
 *
 * @param <T> the type parameter
 * @author franzekan
 */
public record AuthResult<T>(T enumType, Long userId) {
}
