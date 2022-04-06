package hr.fer.oprpp2.servlets.voting;

import hr.fer.oprpp2.services.votesDB.VoteOption;
import hr.fer.oprpp2.services.votesDB.VotesDBHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/voting")
public class VotingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VoteOption> voteOptions = VotesDBHandler.loadOptions(req);

        req.setAttribute("voteOptions", voteOptions);
        req.getRequestDispatcher("/WEB-INF/pages/voting/votingIndex.jsp").forward(req, resp);
    }
}

