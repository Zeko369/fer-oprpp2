package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;

/**
 * The type Echo params.
 *
 * @author franzekan
 */
public class EchoParams implements IWebWorker {
    @Override
    public void processRequest(RequestContext context) throws Exception {
        context.setMimeType("text/html");
        context.write("<html><body>")
                .write("<h1>Params from GET:</h1>")
                .write("<table>")
                .write("<thead><tr><th>Name</th><th>Value</th></tr></thead>")
                .write("<tbody>");

        context.getParameterNames().forEach(key -> {
            try {
                context.write(String.format("<tr><td>%s</td><td>%s</td></tr>", key, context.getParameter(key)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        context.write("</tbody>")
                .write("</table>")
                .write("</body></html>");
    }
}
