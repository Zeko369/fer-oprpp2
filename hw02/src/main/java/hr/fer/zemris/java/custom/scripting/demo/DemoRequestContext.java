package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.webserver.HTTP.HTTPStatus;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Demo request context.
 *
 * @author franzekan
 */
public class DemoRequestContext {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        demo1("primjer1.txt", "ISO-8859-2");
        demo1("primjer2.txt", "UTF-8");
        demo2();
    }

    private static void demo1(String filePath, String encoding) throws IOException {
        OutputStream os = Files.newOutputStream(Paths.get(filePath));
        RequestContext rc = new RequestContext(null, "GET", os, new HashMap<>(), new HashMap<>(), new ArrayList<>());

        rc.setEncoding(encoding);
        rc.setMimeType("text/plain");
        rc.setStatus(new HTTPStatus(205, "Idemo dalje"));

        // Only at this point will header be created and written...
        rc.write("Čevapčići i Šiščevapčići.");
        os.close();
    }

    private static void demo2() throws IOException {
        OutputStream os = Files.newOutputStream(Paths.get("primjer3.txt"));
        RequestContext rc = new RequestContext(null, "GET", os, new HashMap<>(), new HashMap<>(), new ArrayList<>());

        rc.setEncoding("UTF-8");
        rc.setMimeType("text/plain");
        rc.setStatus(new HTTPStatus(205, "Idemo dalje"));
        rc.addRCCookie(new RequestContext.RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/", true));
        rc.addRCCookie(new RequestContext.RCCookie("zgrada", "B4", null, null, "/", true));

        // Only at this point will header be created and written...
        rc.write("Čevapčići i Šiščevapčići.");
        os.close();

    }
}
