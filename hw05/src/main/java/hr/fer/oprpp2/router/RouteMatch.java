package hr.fer.oprpp2.router;


public record RouteMatch(String authorUsername, AuthorSimpleRoute authorRoute, Long blogId) {
    public static RouteMatch of(String authorUsername, AuthorSimpleRoute authorRoute, Long blogId) {
        return new RouteMatch(authorUsername, authorRoute, blogId);
    }

    public static RouteMatch of(String authorUsername, AuthorSimpleRoute authorRoute) {
        return new RouteMatch(authorUsername, authorRoute, null);
    }
}
