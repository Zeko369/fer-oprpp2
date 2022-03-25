package hr.fer.zemris.java.webserver.Util;

import hr.fer.zemris.java.webserver.SmartHttpServerException;

import java.io.IOException;
import java.util.Properties;

public class LoadProperties {
    public static Properties load(String filename) {
        Properties properties = new Properties();
        try {
            properties.load(LoadProperties.class.getResourceAsStream(filename));
        } catch (IOException e) {
            throw new SmartHttpServerException("Cannot load properties file.");
        }

        return properties;
    }
}
