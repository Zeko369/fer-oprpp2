package hr.fer.oprpp2.servlets.voting;

import hr.fer.oprpp2.services.VotesDB.VoteOption;
import hr.fer.oprpp2.services.VotesDB.VotesDBHandler;
import hr.fer.oprpp2.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VoteOption> voteOptions = VotesDBHandler.loadOptions(req);

        String voteIdRaw = req.getParameter("id");
        if (voteIdRaw == null) {
            this.throwError(req, resp, List.of("Vote not selected"));
            return;
        }

        int voteId;
        try {
            voteId = Integer.parseInt(voteIdRaw);
        } catch (NumberFormatException e) {
            this.throwError(req, resp, List.of("VoteID is not an integer"));
            return;
        }

        if (voteOptions.stream().noneMatch(vo -> vo.id() == voteId)) {
            this.throwError(req, resp, List.of("VoteID not found"));
        }

        VotesDBHandler.voteFor(req, voteId);

        resp.sendRedirect("/glasanje-rezultati");
    }
}
