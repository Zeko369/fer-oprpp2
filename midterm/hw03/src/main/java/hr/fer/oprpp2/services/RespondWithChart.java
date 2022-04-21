package hr.fer.oprpp2.services;

import hr.fer.oprpp2.util.ValueWithErrors;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Respond with chart.
 *
 * @author franzekan
 */
public class RespondWithChart {
    /**
     * Send.
     *
     * @param req     the req
     * @param resp    the resp
     * @param dataset the dataset
     * @param title   the title
     * @throws IOException the io exception
     */
    public static void send(HttpServletRequest req, HttpServletResponse resp, DefaultPieDataset<String> dataset, String title) throws IOException {
        @SuppressWarnings("deprecation")
        JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

        int width = ValueWithErrors.ofParamWithDefault(req, "width", 500).value();
        int height = ValueWithErrors.ofParamWithDefault(req, "height", 400).value();

        resp.setContentType("image/png");
        ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, width, height);
    }
}
