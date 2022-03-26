package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class BgColorWorker implements IWebWorker {
    @Override
    public void processRequest(RequestContext context) throws Exception {
        String message = "Error setting background color (color format wrong or missing)";
        String color = context.getPersistentParameter("bgcolor");
        if (context.getParameter("bgcolor") != null) {
            String tmp = context.getParameter("bgcolor");
            if (tmp.length() == 6 && tmp.matches("[0-9a-fA-F]+")) {
                color = tmp;
                message = "Bg color set to " + tmp;
            }
        }

        if (color == null) {
            color = "7F7F7F";
        } else {
            context.setPersistentParameter("bgcolor", color);
        }

        context.setTemporaryParameter("message", message);
        context.setTemporaryParameter("background", color);
        context.getDispatcher().dispatchRequest("/private/pages/bgColorMessage.smscr");
    }
}
