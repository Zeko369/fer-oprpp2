package hr.fer.oprpp2.servlets;

import hr.fer.oprpp2.services.RespondWithChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Report image servlet.
 *
 * @author franzekan
 */
@WebServlet("/reportImage")
public class ReportImageServlet extends BaseServlet {
    private static final DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

    static {
        dataset.setValue("Python", 17.926);
        dataset.setValue("JavaScript", 14.058);
        dataset.setValue("Java", 12.208);
        dataset.setValue("TypeScript", 8.472);
        dataset.setValue("Go", 8.161);
        dataset.setValue("C++", 6.670);
        dataset.setValue("Ruby", 6.165);
        dataset.setValue("PHP", 5.252);
        dataset.setValue("C#", 3.372);
        dataset.setValue("C", 3.150);
        dataset.setValue("Nix", 2.420);
        dataset.setValue("Shell", 2.184);
        dataset.setValue("Scala", 2.047);
        dataset.setValue("Kotlin", 1.028);
        dataset.setValue("Rust", 0.694);
        dataset.setValue("Other", 6.193);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RespondWithChart.send(req, resp, dataset, "Language usage in 2021");
    }
}
