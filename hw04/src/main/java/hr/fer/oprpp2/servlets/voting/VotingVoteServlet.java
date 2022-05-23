package hr.fer.oprpp2.servlets.voting;

import hr.fer.oprpp2.dao.DAO;
import hr.fer.oprpp2.dao.DAOProvider;
import hr.fer.oprpp2.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Voting vote servlet.
 *
 * @author franzekan
 */
@WebServlet("/voting/vote")
public class VotingVoteServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String voteIdRaw = req.getParameter("id");
        if (voteIdRaw == null) {
            this.throwError(req, resp, "Option not selected");
            return;
        }

        int optionId;
        try {
            optionId = Integer.parseInt(voteIdRaw);
        } catch (NumberFormatException e) {
            this.throwError(req, resp, "OptionID is not an integer");
            return;
        }

        DAO dao = DAOProvider.getDao();
        dao.vote(optionId);

        resp.sendRedirect("/webapp1/voting/results?pollId=" + req.getParameter("pollId"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterValues("voteIds") != null) {
            DAO dao = DAOProvider.getDao();
            for (String id : req.getParameterValues("voteIds")) {
                try {
                    dao.vote(Integer.parseInt(id));
                } catch (NumberFormatException e) {
                    this.throwError(req, resp, "VoteID is not an integer");
                    return;
                }
            }
        }

        resp.sendRedirect("/webapp1/voting/results?pollId=" + req.getParameter("pollId"));
    }
}
