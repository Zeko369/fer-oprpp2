package hr.fer.oprpp2.services.VotesDB;

import hr.fer.oprpp2.services.FileLoader;

import javax.servlet.ServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// FIXME: convert all datastructures to HashMaps
public class VotesDBHandler {
    private static final String OPTIONS_PATH = "/WEB-INF/glasanje-definicija.tsv";
    private static final String VOTES_PATH = "/WEB-INF/glasanje-rezultati.tsv";

    public static List<VoteOption> loadOptions(ServletRequest request) {
        try {
            return VotesDBHandler.getOptionsLoader(request).loadFile();
        } catch (IOException e) {
            return List.of();
        }
    }

    private static FileLoader<VoteOption> getOptionsLoader(ServletRequest request) {
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

    public static void voteFor(ServletRequest request, int id) {
        FileLoader<VoteResult> loader = new FileLoader<>(request.getServletContext().getRealPath(VOTES_PATH), (line) -> {
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

        List<VoteResult> results = new ArrayList<>();
        boolean reloadFile = false;
        try {
            results = loader.loadFile();
        } catch (FileNotFoundException e) {
            reloadFile = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (results.stream().noneMatch(r -> r.id() == id)) {
            reloadFile = true;
        }

        if (reloadFile) {
            try {
                for (VoteOption option : VotesDBHandler.getOptionsLoader(request).loadFile()) {
                    if (results.stream().noneMatch(r -> r.id() == option.id())) {
                        results.add(new VoteResult(option.id(), 0));
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        for (VoteResult result : results) {
            if (result.id() == id) {
                result.increment();
            }
        }

        List<String> outputs = results.stream().map(res -> res.id() + "\t" + res.votes()).toList();

        try {
            loader.writeToFile(outputs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
