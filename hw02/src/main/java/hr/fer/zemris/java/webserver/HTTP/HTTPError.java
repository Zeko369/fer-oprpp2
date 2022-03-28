package hr.fer.zemris.java.webserver.HTTP;

import java.util.Map;
import java.util.Objects;

/**
 * The type Http error.
 *
 * @author franzekan
 */
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

    /**
     * Instantiates a new Http error.
     *
     * @param code the code
     */
    public HTTPError(int code) {
        this(defaultMessages.getOrDefault(code, new HTTPStatus(code, "Unknown error")), null);
    }

    /**
     * Instantiates a new Http error.
     *
     * @param status  the status
     * @param message the message
     */
    public HTTPError(HTTPStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Instantiates a new Http error.
     *
     * @param status the status
     */
    public HTTPError(HTTPStatus status) {
        this(Objects.requireNonNull(status), status.message());
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public HTTPStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
