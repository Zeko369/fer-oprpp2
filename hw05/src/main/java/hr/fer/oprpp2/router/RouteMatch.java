package hr.fer.oprpp2.router;


/**
 * The type Route match.
 *
 * @author franzekan
 */
public record RouteMatch(String authorUsername, AuthorSimpleRoute authorRoute, Long blogId) {
    /**
     * Of route match.
     *
     * @param authorUsername the author username
     * @param authorRoute    the author route
     * @param blogId         the blog id
     * @return the route match
     */
    public static RouteMatch of(String authorUsername, AuthorSimpleRoute authorRoute, Long blogId) {
        return new RouteMatch(authorUsername, authorRoute, blogId);
    }

    /**
     * Of route match.
     *
     * @param authorUsername the author username
     * @param authorRoute    the author route
     * @return the route match
     */
    public static RouteMatch of(String authorUsername, AuthorSimpleRoute authorRoute) {
        return new RouteMatch(authorUsername, authorRoute, null);
    }
}
