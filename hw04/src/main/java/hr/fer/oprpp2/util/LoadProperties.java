package hr.fer.oprpp2.util;

import java.io.*;
import java.util.Properties;

public class LoadProperties {
    /**
     * Load properties.
     *
     * @param filename the filename
     * @return the properties
     */
    public static Properties load(String filename) {
        Properties properties = new Properties();

        File f = new File(filename);
        try (InputStream is = new FileInputStream(f)) {
            properties.load(is);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File " + filename + " does not exist.");
        } catch (IOException e) {
            throw new RuntimeException("Cannot load properties file.");
        }

        return properties;
    }
}

