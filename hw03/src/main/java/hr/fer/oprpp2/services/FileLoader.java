package hr.fer.oprpp2.services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public record FileLoader<T>(String filename, IRowParser<T> parser) {
    public interface IRowParser<T> {
        T parse(String row);
    }

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

    public void writeToFile(List<String> lines) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                bw.write(line + "\n");
            }
        }
    }
}
