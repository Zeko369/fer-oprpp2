package hr.fer.oprpp2.servlets;

import hr.fer.oprpp2.util.ValueWithErrors;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        @SuppressWarnings("deprecation")
        JFreeChart chart = ChartFactory.createPieChart3D("Language usage in 2021", dataset, true, true, false);

        int width = ValueWithErrors.ofParamWithDefault(req, "width", 500).value();
        int height = ValueWithErrors.ofParamWithDefault(req, "height", 400).value();

        resp.setContentType("image/png");
        ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, width, height);
    }
}
