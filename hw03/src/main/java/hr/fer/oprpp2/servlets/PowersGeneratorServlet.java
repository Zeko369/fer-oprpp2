package hr.fer.oprpp2.servlets;

import hr.fer.oprpp2.services.ExcelFileGenerator;
import hr.fer.oprpp2.util.ValueWithErrors;
import org.apache.poi.ss.usermodel.Sheet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/powers")
public class PowersGeneratorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ValueWithErrors<Integer> a = ValueWithErrors.ofParam(req, "a");
        ValueWithErrors<Integer> b = ValueWithErrors.ofParam(req, "b");
        ValueWithErrors<Integer> n = ValueWithErrors.ofParam(req, "n");

        List<String> errors = ValueWithErrors.getAllErrors(a, b, n);
        if (!a.isInvalid() && !b.isInvalid()) {
            if (a.value() > b.value()) {
                errors.add("a must be less than b");
            }
        }

        if (errors.size() > 0) {
            req.setAttribute("error", String.join("<br/>", errors));
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        resp.setHeader("Content-Type", "application/vnd.ms-excel");
        resp.setHeader("Content-Disposition", "attachment; filename=\"powers.xlsx\"");

        ExcelFileGenerator<ExcelRow> generator = new ExcelFileGenerator<>(
                List.of("Number", "Power"),
                (data) -> List.of(String.valueOf(data.num()), String.valueOf(data.pow()))
        );

        for (int i = 0; i < n.value(); i++) {
            List<ExcelRow> rows = new ArrayList<>();
            for (int j = a.value(); j <= b.value(); j++) {
                rows.add(new ExcelRow(j, (int) Math.pow(j, i)));
            }

            generator.addSheet("Power + " + i, rows);
        }

        generator.write(resp.getOutputStream());
    }


    private record ExcelRow(int num, int pow) {
    }

}
