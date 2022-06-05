package hr.fer.oprpp2.services.Auth;

public record AuthResult<T>(T enumType, Long userId) {
}
