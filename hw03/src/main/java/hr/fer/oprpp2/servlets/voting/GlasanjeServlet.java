package hr.fer.oprpp2.servlets.voting;

import hr.fer.oprpp2.services.VotesDB.VoteOption;
import hr.fer.oprpp2.services.VotesDB.VotesDBHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VoteOption> voteOptions = VotesDBHandler.loadOptions(req);

        req.setAttribute("voteOptions", voteOptions);
        req.getRequestDispatcher("/WEB-INF/pages/voting/glasanjeIndex.jsp").forward(req, resp);
    }
}

