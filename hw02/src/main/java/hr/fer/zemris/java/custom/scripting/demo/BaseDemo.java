package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.shared.FileLoader;

import java.io.FileNotFoundException;

/**
 * The type Base demo.
 *
 * @author franzekan
 */
public abstract class BaseDemo {
    /**
     * Simple class used to read the file or print a message if the file is not found / path not provided.
     *
     * @param args the args
     * @return the content
     */
    public String getContent(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: EngineDemo <file>");
            System.exit(1);
        }

        try {
            return FileLoader.loadCode(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
            return null;
        }
    }
}
