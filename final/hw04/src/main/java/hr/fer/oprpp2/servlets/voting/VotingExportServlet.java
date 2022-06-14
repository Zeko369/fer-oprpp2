package hr.fer.oprpp2.servlets.voting;

import hr.fer.oprpp2.dao.DAOProvider;
import hr.fer.oprpp2.model.PollOption;
import hr.fer.oprpp2.services.ExcelFileGenerator;
import hr.fer.oprpp2.services.RespondWithChart;
import hr.fer.oprpp2.servlets.BaseServlet;
import org.jfree.data.general.DefaultPieDataset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The type Voting export servlet.
 *
 * @author franzekan
 */
@WebServlet("/voting/export")
public class VotingExportServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String format = req.getParameter("format");
        if (format == null) {
            this.throwError(req, resp, "Format not found", 404);
            return;
        }

        if (req.getParameter("pollId") == null) {
            this.throwError(req, resp, "No poll id provided");
            return;
        }


        switch (format) {
            case "json" -> this.throwError(req, resp, "JSON format is not implemented", 501);
            case "graph" -> this.handleGraph(req, resp);
            case "xlsx" -> this.handleExcel(req, resp);
            default -> this.throwError(req, resp, "Unsupported format", 404);
        }
    }

    private void handleExcel(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(Integer.parseInt(req.getParameter("pollId")));
        ExcelFileGenerator<PollOption> generator = new ExcelFileGenerator<>(
                List.of("id", "name", "votes", "youtube"),
                (vote) -> List.of(String.valueOf(vote.getId()), vote.getTitle(), String.valueOf(vote.getLikesCount()), vote.getLink())
        );

        resp.setHeader("Content-Type", "application/vnd.ms-excel");
        resp.setHeader("Content-Disposition", "attachment; filename=\"votes.xlsx\"");

        generator.addSheet("votes", pollOptions);
        generator.write(resp.getOutputStream());
    }

    private void handleGraph(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(Integer.parseInt(req.getParameter("pollId")));
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        pollOptions.forEach(vote -> dataset.setValue(vote.getTitle(), vote.getLikesCount()));

        RespondWithChart.send(req, resp, dataset, "Voting results");
    }
}
