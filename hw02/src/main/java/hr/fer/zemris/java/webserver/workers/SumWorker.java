package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker {
    private static final String[] IMAGE_NAMES = {"image.png", "image2.png"};

    @Override
    public void processRequest(RequestContext context) throws Exception {
        int a = parse(context.getParameter("a"), 1);
        int b = parse(context.getParameter("b"), 2);

        int sum = a + b;

        context.setTemporaryParameter("varA", String.valueOf(a));
        context.setTemporaryParameter("varB", String.valueOf(b));
        context.setTemporaryParameter("sum", String.valueOf(sum));

        context.setTemporaryParameter("imgName", SumWorker.IMAGE_NAMES[sum % 2]);

        context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
    }

    private int parse(String s, int defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }
}
