package hr.fer.zemris.java.webserver;

import java.util.Map;
import java.util.Objects;

public class HTTPError extends RuntimeException {
    private static final Map<Integer, HTTPStatus> defaultMessages = Map.of(
            200, HTTPStatus.OK,
            400, HTTPStatus.BAD_REQUEST,
            403, HTTPStatus.FORBIDDEN,
            404, HTTPStatus.NOT_FOUND,
            500, HTTPStatus.INTERNAL_SERVER_ERROR
    );

    private final HTTPStatus status;
    private final String message;

    public HTTPError(int code) {
        this(defaultMessages.getOrDefault(code, new HTTPStatus(code, "Unknown error")), null);
    }

    public HTTPError(HTTPStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HTTPError(HTTPStatus status) {
        this(Objects.requireNonNull(status), status.message());
    }

    public HTTPStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
