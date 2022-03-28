package hr.fer.zemris.java.webserver;

/**
 * The interface Web worker.
 *
 * @author franzekan
 */
@SuppressWarnings("UnnecessaryInterfaceModifier")
public interface IWebWorker {
    /**
     * Process request.
     *
     * @param context the context
     * @throws Exception the exception
     */
    public void processRequest(RequestContext context) throws Exception;
}
