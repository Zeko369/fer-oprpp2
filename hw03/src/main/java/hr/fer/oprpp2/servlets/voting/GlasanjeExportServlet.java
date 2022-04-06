package hr.fer.oprpp2.servlets.voting;

import hr.fer.oprpp2.services.ExcelFileGenerator;
import hr.fer.oprpp2.services.VotesDB.VotesDBHandler;
import hr.fer.oprpp2.services.VotesDB.WholeVote;
import hr.fer.oprpp2.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/glasanje/export")
public class GlasanjeExportServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String format = req.getParameter("format");
        if (format == null) {
            this.throwError(req, resp, "Format not found", 404);
            return;
        }

        List<WholeVote> votes = VotesDBHandler.loadWholeVotes(req);

        switch (format) {
            case "json" -> this.throwError(req, resp, "JSON format is not implemented", 501);
            case "graph" -> this.handleGraph(req, resp, votes);
            case "xlsx" -> this.handleExcel(req, resp, votes);
            default -> this.throwError(req, resp, "Unsupported format", 404);
        }
    }

    private void handleExcel(HttpServletRequest req, HttpServletResponse resp, List<WholeVote> votes) throws IOException {
        ExcelFileGenerator<WholeVote> generator = new ExcelFileGenerator<>(
                List.of("id", "name", "votes", "youtube"),
                (vote) -> List.of(String.valueOf(vote.id()), vote.name(), String.valueOf(vote.votes()), vote.youtubeLink())
        );

        resp.setHeader("Content-Type", "application/vnd.ms-excel");
        resp.setHeader("Content-Disposition", "attachment; filename=\"votes.xlsx\"");

        generator.addSheet("votes", votes);
        generator.write(resp.getOutputStream());
    }

    private void handleGraph(HttpServletRequest req, HttpServletResponse resp, List<WholeVote> votes) throws ServletException, IOException {
        this.throwError(req, resp, "Graph format is not implemented", 501);
    }
}
