package hr.fer.zemris.java.webserver;

public record HTTPStatus(int code, String message) {
    public static HTTPStatus OK = new HTTPStatus(200, "Ok");
    public static HTTPStatus BAD_REQUEST = new HTTPStatus(400, "Bad request");
    public static HTTPStatus FORBIDDEN = new HTTPStatus(403, "Forbidden");
    public static HTTPStatus NOT_FOUND = new HTTPStatus(404, "Not found");
    public static HTTPStatus METHOD_NOT_ALLOWED = new HTTPStatus(405, "Method not allowed");
    public static HTTPStatus INTERNAL_SERVER_ERROR = new HTTPStatus(500, "Internal server error");

    @Override
    public String toString() {
        return String.format("%d %s", code, message);
    }
}
