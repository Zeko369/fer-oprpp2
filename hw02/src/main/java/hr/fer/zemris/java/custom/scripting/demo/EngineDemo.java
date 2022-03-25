package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.node.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngineDemo {
    public static void main(String[] args) {
        String code = BaseDemo.getContent(args);

        Map<String, String> parameters = new HashMap<>();
        Map<String, String> persistentParameters = new HashMap<>();
        List<RequestContext.RCCookie> cookies = new ArrayList<>();

        RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,
                cookies);

        parameters.put("broj", "4");

        parameters.put("a", "4");
        parameters.put("b", "2");
        persistentParameters.put("brojPoziva", "3");

        DocumentNode root = new SmartScriptParser(code).getDocumentNode();
        new SmartScriptEngine(root, rc).execute();


        System.out.println("------- DONE -------");
        System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
    }
}
