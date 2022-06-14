package hr.fer.oprpp2.servlets.voting;

import hr.fer.oprpp2.dao.DAOProvider;
import hr.fer.oprpp2.model.PollOption;
import hr.fer.oprpp2.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Voting results servlet.
 *
 * @author franzekan
 */
@WebServlet("/voting/results")
public class VotingResultsServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("pollId") == null) {
            this.throwError(req, resp, "No poll id provided");
            return;
        }

        List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(Integer.parseInt(req.getParameter("pollId")));

        req.setAttribute("votes", pollOptions);
        req.setAttribute("winners", this.getWinners(pollOptions));

        req.getRequestDispatcher("/WEB-INF/pages/voting/votingResults.jsp").forward(req, resp);
    }

    // TODO: Refactor to make smarter
    private List<PollOption> getWinners(List<PollOption> votes) {
        long maxVotes = 0;

        for (PollOption option : votes) {
            if (option.getLikesCount() > maxVotes) {
                maxVotes = option.getLikesCount();
            }
        }

        List<PollOption> winners = new ArrayList<>();
        for (PollOption vote : votes) {
            if (vote.getLikesCount() == maxVotes) {
                winners.add(vote);
            }
        }

        return winners;
    }
}
