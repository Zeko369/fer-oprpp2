package hr.fer.oprpp2.services;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class ExcelFileGenerator {
    private final Workbook workbook = new XSSFWorkbook();

    public record ExcelRow(int num, int pow) {
    }

    public void addSheet(String name, List<ExcelRow> rows) {
        Sheet spreadsheet = this.workbook.createSheet(name);
        Row firstRow = spreadsheet.createRow(spreadsheet.getLastRowNum() + 1);
        firstRow.createCell(0).setCellValue("Number");
        firstRow.createCell(1).setCellValue("Power");

        rows.forEach(row -> {
            Row tableRow = spreadsheet.createRow(spreadsheet.getLastRowNum() + 1);
            tableRow.createCell(0).setCellValue(row.num);
            tableRow.createCell(1).setCellValue(row.pow);
        });
    }

    public void write(OutputStream os) throws IOException {
        this.workbook.write(os);
    }
}
