package hr.fer.oprpp2.services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type File loader.
 *
 * @param <T> the type parameter
 * @author franzekan
 */
public record FileLoader<T>(String filename, IRowParser<T> parser) {
    /**
     * The interface Row parser.
     *
     * @param <T> the type parameter
     * @author franzekan
     */
    public interface IRowParser<T> {
        /**
         * Parse t.
         *
         * @param row the row
         * @return the t
         */
        T parse(String row);
    }

    /**
     * Load file list.
     *
     * @return the list
     * @throws IOException the io exception
     */
    public List<T> loadFile() throws IOException {
        List<T> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                T tmp = this.parser.parse(line);
                if (tmp != null) {
                    list.add(tmp);
                }
            }
        }

        return list;
    }

    /**
     * Write to file.
     *
     * @param lines the lines
     * @throws IOException the io exception
     */
    public void writeToFile(List<String> lines) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                bw.write(line + "\n");
            }
        }
    }
}
