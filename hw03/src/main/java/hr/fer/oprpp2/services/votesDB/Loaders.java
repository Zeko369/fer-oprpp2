package hr.fer.oprpp2.services.votesDB;

import hr.fer.oprpp2.services.FileLoader;

import javax.servlet.ServletRequest;

/**
 * Wrapper for FileLoaders
 *
 * @author franzekan
 */
public class Loaders {
    /**
     * The constant OPTIONS_PATH.
     */
    public static final String OPTIONS_PATH = "/WEB-INF/voting-options.tsv";
    /**
     * The constant RESULTS_PATH.
     */
    public static final String RESULTS_PATH = "/WEB-INF/voting-results.tsv";

    /**
     * Gets options loader.
     *
     * @param request the request
     * @return the options loader
     */
    public static FileLoader<VoteOption> getOptionsLoader(ServletRequest request) {
        return new FileLoader<>(request.getServletContext().getRealPath(OPTIONS_PATH), (line) -> {
            String[] parts = line.split("\t");
            if (parts.length != 3) {
                return null;
            }

            try {
                return new VoteOption(Integer.parseInt(parts[0]), parts[1], parts[2]);
            } catch (NumberFormatException e) {
                return null;
            }
        });
    }

    /**
     * Gets results loader.
     *
     * @param request the request
     * @return the results loader
     */
    public static FileLoader<VoteResult> getResultsLoader(ServletRequest request) {
        return new FileLoader<>(request.getServletContext().getRealPath(RESULTS_PATH), (line) -> {
            String[] parts = line.split("\t");
            if (parts.length != 2) {
                return null;
            }

            try {
                return new VoteResult(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            } catch (NumberFormatException e) {
                return null;
            }
        });
    }
}
