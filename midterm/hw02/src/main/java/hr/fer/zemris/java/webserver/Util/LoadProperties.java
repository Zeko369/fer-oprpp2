package hr.fer.zemris.java.webserver.Util;

import hr.fer.zemris.java.webserver.SmartHttpServerException;

import java.io.*;
import java.util.Properties;

/**
 * The type Load properties.
 *
 * @author franzekan
 */
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
            throw new SmartHttpServerException("File " + filename + " does not exist.");
        } catch (IOException e) {
            throw new SmartHttpServerException("Cannot load properties file.");
        }

        return properties;
    }
}
