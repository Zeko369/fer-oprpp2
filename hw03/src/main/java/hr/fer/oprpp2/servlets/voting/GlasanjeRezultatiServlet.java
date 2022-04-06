package hr.fer.oprpp2.servlets.voting;

import hr.fer.oprpp2.services.ExcelFileGenerator;
import hr.fer.oprpp2.services.VotesDB.VoteOption;
import hr.fer.oprpp2.services.VotesDB.VoteResult;
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
        List<WholeVote> votes = VotesDBHandler.loadFinal(req);

        String format = req.getParameter("format");
        if (format != null) {
            switch (format) {
                case "json" -> this.throwError(req, resp, List.of("JSON format is not implemented"), 501);
                case "xlsx" -> {
                    ExcelFileGenerator<WholeVote> generator = new ExcelFileGenerator<>(
                            List.of("id", "name", "votes", "youtube"),
                            (vote) -> List.of(String.valueOf(vote.id()), vote.name(), String.valueOf(vote.votes()), vote.youtubeLink())
                    );

                    resp.setHeader("Content-Type", "application/vnd.ms-excel");
                    resp.setHeader("Content-Disposition", "attachment; filename=\"votes.xlsx\"");

                    generator.addSheet("votes", votes);
                    generator.write(resp.getOutputStream());
                }
                default -> this.throwError(req, resp, List.of("Unsupported format"), 404);
            }

            return;
        }

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
