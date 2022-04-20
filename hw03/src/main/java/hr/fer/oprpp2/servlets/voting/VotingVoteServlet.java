package hr.fer.oprpp2.servlets.voting;

import hr.fer.oprpp2.services.votesDB.VoteOption;
import hr.fer.oprpp2.services.votesDB.VotesDBHandler;
import hr.fer.oprpp2.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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

        resp.sendRedirect("/webapp1/voting/results");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VoteOption> voteOptions = VotesDBHandler.loadOptions(req);
        if (req.getParameterValues("voteIds") != null) {
            List<Integer> voteIds = new ArrayList<>();
            for (String id : req.getParameterValues("voteIds")) {
                int voteId;
                try {
                    voteId = Integer.parseInt(id);
                } catch (NumberFormatException e) {
                    this.throwError(req, resp, "VoteID is not an integer");
                    return;
                }

                int finalVoteId = voteId;
                if (voteOptions.stream().noneMatch(vo -> vo.id() == finalVoteId)) {
                    this.throwError(req, resp, String.format("VoteID (%d) is not found", finalVoteId));
                    return;
                }

                voteIds.add(voteId);
            }

            if (!voteIds.isEmpty()) {
                VotesDBHandler.voteFor(req, voteIds);
            }
        }

        resp.sendRedirect("/webapp1/voting/results");
    }
}
