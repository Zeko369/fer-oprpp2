package hr.fer.oprpp2.router;

import java.util.regex.Pattern;

public class SimpleRouter {

    public RouteMatch getRoute(String path) {
        String[] pathParts = path.split("/");
        switch (pathParts.length) {
            case 1:
                return RouteMatch.of(pathParts[0], AuthorSimpleRoute.AUTHOR);
            case 2:
                if (pathParts[1].equals("new")) {
                    return RouteMatch.of(pathParts[0], AuthorSimpleRoute.AUTHOR_NEW_BLOG);
                } else if (Pattern.matches("\\d+", pathParts[1])) {
                    return RouteMatch.of(pathParts[0], AuthorSimpleRoute.BLOG_SHOW, Long.parseLong(pathParts[1]));
                } else {
                    return null;
                }
            case 3:
                if (Pattern.matches("\\d+", pathParts[1])) {
                    if (pathParts[2].equals("edit")) {
                        return RouteMatch.of(pathParts[0], AuthorSimpleRoute.AUTHOR_EDIT_BLOG, Long.parseLong(pathParts[1]));
                    } else if (pathParts[2].equals("comment")) {
                        return RouteMatch.of(pathParts[0], AuthorSimpleRoute.AUTHOR_COMMENT_BLOG, Long.parseLong(pathParts[1]));
                    }
                }
            default:
                return null;
        }
    }
}
