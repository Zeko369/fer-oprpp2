package hr.fer.oprpp2.servlets.voting;

import hr.fer.oprpp2.services.votesDB.VoteOption;
import hr.fer.oprpp2.services.votesDB.VotesDBHandler;
import hr.fer.oprpp2.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The type Voting vote servlet.
 *
 * @author franzekan
 */
@WebServlet("/voting/vote")
public class VotingVoteServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VoteOption> voteOptions = VotesDBHandler.loadOptions(req);

        String voteIdRaw = req.getParameter("id");
        if (voteIdRaw == null) {
            this.throwError(req, resp, "Vote not selected");
            return;
        }

        int voteId;
        try {
            voteId = Integer.parseInt(voteIdRaw);
        } catch (NumberFormatException e) {
            this.throwError(req, resp, "VoteID is not an integer");
            return;
        }

        if (voteOptions.stream().noneMatch(vo -> vo.id() == voteId)) {
            this.throwError(req, resp, "VoteID not found");
        }

        VotesDBHandler.voteFor(req, voteId);

        resp.sendRedirect("/webapp2/voting/results");
    }
}
