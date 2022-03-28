package hr.fer.zemris.java.webserver;

/**
 * The interface Dispatcher.
 *
 * @author franzekan
 */
public interface IDispatcher {
    /**
     * Dispatch request.
     *
     * @param urlPath the url path
     * @throws Exception the exception
     */
    void dispatchRequest(String urlPath) throws Exception;
}
