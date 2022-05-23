package hr.fer.oprpp2.servlets.voting;

import hr.fer.oprpp2.dao.DAOProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Voting servlet.
 *
 * @author franzekan
 */
@WebServlet("/voting")
public class VotingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pollId = req.getParameter("pollId");
        if (pollId == null) {
            req.setAttribute("polls", DAOProvider.getDao().getPolls());
            req.getRequestDispatcher("/WEB-INF/pages/voting/votingIndex.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("pollOptions", DAOProvider.getDao().getPollOptions(Integer.parseInt(pollId)));
        req.getRequestDispatcher("/WEB-INF/pages/voting/votingShow.jsp").forward(req, resp);
    }
}

