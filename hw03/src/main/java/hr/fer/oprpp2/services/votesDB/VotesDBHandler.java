package hr.fer.oprpp2.services.votesDB;

import hr.fer.oprpp2.services.FileLoader;

import javax.servlet.ServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Votes db handler.
 *
 * @author franzekan
 */
// FIXME: convert all datastructures to HashMaps
public class VotesDBHandler {

    /**
     * Loads vote options.
     *
     * @param request the request
     * @return the list
     */
    public static List<VoteOption> loadOptions(ServletRequest request) {
        try {
            return Loaders.getOptionsLoader(request).loadFile();
        } catch (IOException e) {
            return List.of();
        }
    }

    /**
     * Load whole results from file.
     *
     * @param request the request
     * @return the list
     */
    public static List<WholeVote> loadWholeVotes(ServletRequest request) {
        try {
            List<VoteOption> options = Loaders.getOptionsLoader(request).loadFile();
            List<VoteResult> results = Loaders.getResultsLoader(request).loadFile();

            List<WholeVote> finalVotes = new ArrayList<>();
            for (VoteOption option : options) {
                int votes = 0;
                for (VoteResult result : results) {
                    if (result.id() == option.id()) {
                        votes = result.votes();
                    }
                }

                finalVotes.add(new WholeVote(option.id(), option.name(), votes, option.youtubeLink()));
            }

            return finalVotes.stream().sorted().toList();
        } catch (IOException e) {
            return List.of();
        }
    }

    /**
     * Vote for ID and save to file.
     *
     * @param request the request
     * @param id      the id
     */
    public static void voteFor(ServletRequest request, int id) {
        FileLoader<VoteResult> loader = Loaders.getResultsLoader(request);
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
                for (VoteOption option : Loaders.getOptionsLoader(request).loadFile()) {
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
