package hr.fer.zemris.java.webserver.HTTP;

/**
 * The type Http status.
 *
 * @author franzekan
 */
public record HTTPStatus(int code, String message) {
    /**
     * The constant OK.
     */
    public static HTTPStatus OK = new HTTPStatus(200, "Ok");
    /**
     * The constant BAD_REQUEST.
     */
    public static HTTPStatus BAD_REQUEST = new HTTPStatus(400, "Bad request");
    /**
     * The constant FORBIDDEN.
     */
    public static HTTPStatus FORBIDDEN = new HTTPStatus(403, "Forbidden");
    /**
     * The constant NOT_FOUND.
     */
    public static HTTPStatus NOT_FOUND = new HTTPStatus(404, "Not found");
    /**
     * The constant METHOD_NOT_ALLOWED.
     */
    public static HTTPStatus METHOD_NOT_ALLOWED = new HTTPStatus(405, "Method not allowed");
    /**
     * The constant INTERNAL_SERVER_ERROR.
     */
    public static HTTPStatus INTERNAL_SERVER_ERROR = new HTTPStatus(500, "Internal server error");

    @Override
    public String toString() {
        return String.format("%d %s", code, message);
    }
}
