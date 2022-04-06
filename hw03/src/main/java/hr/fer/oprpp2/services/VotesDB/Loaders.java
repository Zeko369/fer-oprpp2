package hr.fer.oprpp2.services.VotesDB;

import hr.fer.oprpp2.services.FileLoader;

import javax.servlet.ServletRequest;

public class Loaders {
    public static final String OPTIONS_PATH = "/WEB-INF/voting-options.tsv";
    public static final String RESULTS_PATH = "/WEB-INF/voting-results.tsv";

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
