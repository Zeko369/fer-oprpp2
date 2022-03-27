package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.shared.FileLoader;

import java.io.FileNotFoundException;

public abstract class BaseDemo {
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
