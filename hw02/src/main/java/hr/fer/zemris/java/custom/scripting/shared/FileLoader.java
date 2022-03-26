package hr.fer.zemris.java.custom.scripting.shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Helper for reading the file
 *
 * @author franzekan
 */
public class FileLoader {
    /**
     * Load code string.
     *
     * @param filename the filename
     * @return the string
     */
    public static String loadCode(String filename) throws FileNotFoundException {
        return FileLoader.loadCode(new File(filename));
    }

    public static String loadCode(File file) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String tmp = scanner.nextLine();
                if (tmp.startsWith("#")) {
                    continue;
                }

                sb.append(tmp);
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}


