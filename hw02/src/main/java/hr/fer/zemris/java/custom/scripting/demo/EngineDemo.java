package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.node.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.File;
import java.util.*;

public class EngineDemo extends BaseDemo {
    public static void main(String[] args) {
        new EngineDemo().run(args);
    }

    public void run(String[] args) {
        String code = this.getContent(args);

        Map<String, String> parameters = new HashMap<>();
        Map<String, String> persistentParameters = new HashMap<>();
        Map<String, String> temporaryParameters = new HashMap<>();
        List<RequestContext.RCCookie> cookies = new ArrayList<>();

        RequestContext rc = new RequestContext(
                System.out,
                parameters,
                persistentParameters,
                cookies,
                temporaryParameters,
                null
        );

        parameters.put("broj", "4");
        temporaryParameters.put("path", "/");

        parameters.put("a", "4");
        parameters.put("b", "2");
        persistentParameters.put("brojPoziva", "3");

        File f = new File("./webroot");
        File[] files = f.listFiles();
        assert files != null;
        temporaryParameters.put("FileCount", String.valueOf(files.length - 1));
        for (int i = 0; i < files.length; i++) {
            temporaryParameters.put(String.format("file-name:%d", i), files[i].getName());
            temporaryParameters.put(String.format("file-path:%d", i), String.format("%s/%s", f.getName(), files[i].getName()));
        }

        DocumentNode root = new SmartScriptParser(code).getDocumentNode();
        new SmartScriptEngine(root, rc).execute();


        System.out.println("------- DONE -------");
        System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
    }
}
