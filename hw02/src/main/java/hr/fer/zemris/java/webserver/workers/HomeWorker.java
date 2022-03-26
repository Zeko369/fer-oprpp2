package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class HomeWorker implements IWebWorker {
    @Override
    public void processRequest(RequestContext context) throws Exception {
        String bgColor = context.getPersistentParameter("bgcolor");
        if (context.getParameter("bgcolor") != null) {
            bgColor = context.getParameter("bgcolor");
        }

        context.setTemporaryParameter("background", bgColor == null ? "7F7F7F" : bgColor);
        context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
    }
}
