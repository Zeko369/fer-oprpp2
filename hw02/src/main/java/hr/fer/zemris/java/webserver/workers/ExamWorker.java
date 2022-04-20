package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.HTTP.HTTPMethod;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class ExamWorker implements IWebWorker {
    @Override
    public void processRequest(RequestContext context) throws Exception {
        context.setTemporaryParameter("method", context.getMethod());

        if (context.getMethod().equals(HTTPMethod.GET)) {
            context.setTemporaryParameter("ime", context.getParameter("ime"));
            context.setTemporaryParameter("prezime", context.getParameter("prezime"));
        }

        if (context.getMethod().equals(HTTPMethod.POST)) {
            String body = context.getBody();
            if (body == null || body.isEmpty()) {
                context.setTemporaryParameter("error", "Body is empty!");
                context.getDispatcher().dispatchRequest("/private/pages/exam.smscr");
                return;
            } else {
                String[] parts = body.split("&");
                for (String part : parts) {
                    String[] keyValue = part.split("=");
                    if (keyValue.length != 2) {
                        context.setTemporaryParameter("error", "Invalid body!");
                    }

                    String key = keyValue[0];
                    String value = keyValue[1];
                    context.setTemporaryParameter(key, value);
                }
            }
        }

        context.getDispatcher().dispatchRequest("/private/pages/exam.smscr");
    }
}
