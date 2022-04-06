package hr.fer.oprpp2.servlets.voting;

import hr.fer.oprpp2.services.VotesDB.VotesDBHandler;
import hr.fer.oprpp2.services.VotesDB.WholeVote;
import hr.fer.oprpp2.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/glasanje/rezultati")
public class GlasanjeRezultatiServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<WholeVote> votes = VotesDBHandler.loadWholeVotes(req);

        req.setAttribute("votes", votes);
        req.setAttribute("winners", this.getWinners(votes));

        req.getRequestDispatcher("/WEB-INF/pages/voting/glasanjeRez.jsp").forward(req, resp);
    }

    // TODO: Refactor to make smarter
    private List<WholeVote> getWinners(List<WholeVote> votes) {
        int maxVotes = 0;

        for (WholeVote vote : votes) {
            if (vote.votes() > maxVotes) {
                maxVotes = vote.votes();
            }
        }

        List<WholeVote> winners = new ArrayList<>();
        for (WholeVote vote : votes) {
            if (vote.votes() == maxVotes) {
                winners.add(vote);
            }
        }

        return winners;
    }
}
