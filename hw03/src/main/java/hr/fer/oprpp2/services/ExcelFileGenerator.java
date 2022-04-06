package hr.fer.oprpp2.services;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Excel file generator.
 *
 * @param <T> the type parameter
 * @author franzekan
 */
public class ExcelFileGenerator<T> {
    /**
     * Interface used to generate the row
     *
     * @param <T> the type parameter
     * @author franzekan
     */
    public interface ISSheetRowConverter<T> {
        /**
         * Mapper for the row.
         *
         * @param data the data
         * @return the list
         */
        List<String> map(T data);
    }

    private final Workbook workbook = new XSSFWorkbook();
    private final ISSheetRowConverter<T> converter;
    private final List<String> header;

    /**
     * Instantiates a new Excel file generator.
     *
     * @param header    the header
     * @param converter the converter
     */
    public ExcelFileGenerator(List<String> header, ISSheetRowConverter<T> converter) {
        this.header = header;
        this.converter = converter;
    }

    /**
     * Add sheet to workbook.
     *
     * @param name the name
     * @param rows the rows
     */
    public void addSheet(String name, List<T> rows) {
        Sheet spreadsheet = this.workbook.createSheet(name);
        Row firstRow = spreadsheet.createRow(spreadsheet.getLastRowNum() + 1);

        AtomicInteger i = new AtomicInteger();
        this.header.forEach((s) -> firstRow.createCell(i.getAndIncrement()).setCellValue(s));

        rows.forEach(row -> {
            Row tableRow = spreadsheet.createRow(spreadsheet.getLastRowNum() + 1);
            List<String> tmpData = this.converter.map(row);

            i.set(0);
            tmpData.forEach((s) -> tableRow.createCell(i.getAndIncrement()).setCellValue(s));
        });
    }

    /**
     * Write to output stream.
     *
     * @param os the os
     * @throws IOException the io exception
     */
    public void write(OutputStream os) throws IOException {
        this.workbook.write(os);
    }
}
