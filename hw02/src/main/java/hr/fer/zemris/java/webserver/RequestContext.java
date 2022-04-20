package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.webserver.HTTP.HTTPMethod;
import hr.fer.zemris.java.webserver.HTTP.HTTPStatus;
import jdk.jshell.spi.ExecutionControl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * The type Request context.
 *
 * @author franzekan
 */
public class RequestContext {
    /**
     * The type Rc cookie.
     *
     * @author franzekan
     */
    public static class RCCookie {
        private final String name;
        private final String value;
        private final String domain;
        private final String path;
        private final Integer maxAge;
        private final boolean httpOnly;

        /**
         * Instantiates a new Rc cookie.
         *
         * @param name     the name
         * @param value    the value
         * @param maxAge   the max age
         * @param domain   the domain
         * @param path     the path
         * @param httpOnly the http only
         */
        public RCCookie(String name, String value, Integer maxAge, String domain, String path, boolean httpOnly) {
            this.name = name;
            this.value = value;
            this.domain = domain;
            this.path = path;
            this.maxAge = maxAge;
            this.httpOnly = httpOnly;
        }

        /**
         * Gets name.
         *
         * @return the name
         */
        public String getName() {
            return this.name;
        }

        /**
         * Gets value.
         *
         * @return the value
         */
        public String getValue() {
            return this.value;
        }

        /**
         * Gets domain.
         *
         * @return the domain
         */
        public String getDomain() {
            return this.domain;
        }

        /**
         * Gets path.
         *
         * @return the path
         */
        public String getPath() {
            return this.path;
        }

        /**
         * Gets max age.
         *
         * @return the max age
         */
        public Integer getMaxAge() {
            return this.maxAge;
        }

        /**
         * Is http only boolean.
         *
         * @return the boolean
         */
        public boolean isHttpOnly() {
            return this.httpOnly;
        }
    }

    private final OutputStream outputStream;
    private Charset charset;

    private String method = HTTPMethod.GET;
    private String body = null; // This should be passed via whole request

    private String encoding = "UTF-8";
    // TODO: Migrate to HTTPStatus
//    private int statusCode = 200;
//    private String statusText = "OK";
    private HTTPStatus status = HTTPStatus.OK;
    private String mimeType = "text/html";
    private Long contentLength = null;

    private final Map<String, String> parameters;
    private final Map<String, String> temporaryParameters;
    private final Map<String, String> persistentParameters;
    private final List<RCCookie> outputCookies;

    private final Map<String, String> customHeaders;
    /**
     * The Ignored headers.
     */
    public static List<String> IGNORED_HEADERS = List.of("Set-Cookie", "Content-Length", "Content-Type");

    /**
     * Is header generated boolean.
     *
     * @return the boolean
     */
    public boolean isHeaderGenerated() {
        return headerGenerated;
    }

    private boolean headerGenerated = false;

    private final IDispatcher dispatcher;

    /**
     * Gets dispatcher.
     *
     * @return the dispatcher
     */
    public IDispatcher getDispatcher() {
        return dispatcher;
    }

    /**
     * Instantiates a new Request context.
     *
     * @param outputStream         the output stream
     * @param parameters           the parameters
     * @param persistentParameters the persistent parameters
     * @param outputCookies        the output cookies
     * @param temporaryParameters  the temporary parameters
     * @param dispatcher           the dispatcher
     */
    public RequestContext(String body, String method, OutputStream outputStream, Map<String, String> parameters, Map<String, String> persistentParameters, List<RCCookie> outputCookies, Map<String, String> temporaryParameters, IDispatcher dispatcher) {
        this.body = body;
        this.method = method;
        this.outputStream = Objects.requireNonNull(outputStream);
        this.parameters = parameters == null ? new HashMap<>() : parameters;
        this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
        this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
        this.customHeaders = new HashMap<>();
        this.temporaryParameters = temporaryParameters == null ? new HashMap<>() : temporaryParameters;
        this.dispatcher = dispatcher;
    }

    /**
     * Instantiates a new Request context.
     *
     * @param outputStream         the output stream
     * @param parameters           the parameters
     * @param persistentParameters the persistent parameters
     * @param outputCookies        the output cookies
     */
    public RequestContext(String body, String method, OutputStream outputStream, Map<String, String> parameters, Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
        this(body, method, outputStream, parameters, persistentParameters, outputCookies, null, null);
    }

    private void canChangeHeaders() {
        if (this.headerGenerated) {
            throw new RuntimeException("Headers already generated!");
        }
    }

    /**
     * Sets encoding.
     *
     * @param encoding the encoding
     */
    public void setEncoding(String encoding) {
        this.canChangeHeaders();
        this.encoding = encoding;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(HTTPStatus status) {
        this.canChangeHeaders();
        this.status = status;
    }

    /**
     * Gets custom header.
     *
     * @param name the name
     * @return the custom header
     */
    public String getCustomHeader(String name) {
        return this.customHeaders.get(name);
    }

    public String getMethod() {
        return this.method;
    }

    public String getBody() {
        return this.body;
    }

    /**
     * Sets custom header.
     *
     * @param name  the name
     * @param value the value
     */
    public void setCustomHeader(String name, String value) {
        this.canChangeHeaders();
        if (IGNORED_HEADERS.contains(name)) {
            throw new RuntimeException("Cannot set header " + name);
        }

        this.customHeaders.put(name, value);
    }

    /**
     * Remove custom header.
     *
     * @param name the name
     */
    public void removeCustomHeader(String name) {
        this.canChangeHeaders();
        this.customHeaders.remove(name);
    }

//    public void setStatusCode(int statusCode) {
//        this.canChangeHeaders();
//        this.statusCode = statusCode;
//    }
//
//    public void setStatusText(String statusText) {
//        this.canChangeHeaders();
//        this.statusText = statusText;
//    }

    /**
     * Sets mime type.
     *
     * @param mimeType the mime type
     */
    public void setMimeType(String mimeType) {
        this.canChangeHeaders();
        this.mimeType = mimeType;
    }

    /**
     * Sets content length.
     *
     * @param contentLength the content length
     */
    public void setContentLength(Long contentLength) {
        this.canChangeHeaders();
        this.contentLength = contentLength;
    }

    /**
     * Add rc cookie.
     *
     * @param cookie the cookie
     */
    public void addRCCookie(RCCookie cookie) {
        this.outputCookies.add(cookie);
    }

    /**
     * Gets parameter.
     *
     * @param name the name
     * @return the parameter
     */
    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    /**
     * Gets parameter names.
     *
     * @return the parameter names
     */
    public Set<String> getParameterNames() {
        return this.parameters.keySet();
    }

    /**
     * Gets persistent parameter.
     *
     * @param name the name
     * @return the persistent parameter
     */
    public String getPersistentParameter(String name) {
        return this.persistentParameters.get(name);
    }

    /**
     * Gets persistent parameter names.
     *
     * @return the persistent parameter names
     */
    public Set<String> getPersistentParameterNames() {
        return this.persistentParameters.keySet();
    }

    /**
     * Sets persistent parameter.
     *
     * @param name  the name
     * @param value the value
     */
    public void setPersistentParameter(String name, String value) {
        this.persistentParameters.put(name, value);
    }

    /**
     * Remove persistent parameter.
     *
     * @param name the name
     */
    public void removePersistentParameter(String name) {
        this.persistentParameters.remove(name);
    }

    /**
     * Gets temporary parameter.
     *
     * @param name the name
     * @return the temporary parameter
     */
    public String getTemporaryParameter(String name) {
        return this.temporaryParameters.get(name);
    }

    /**
     * Gets temporary parameter names.
     *
     * @return the temporary parameter names
     */
    public Set<String> getTemporaryParameterNames() {
        return this.temporaryParameters.keySet();
    }

    /**
     * Sets temporary parameter.
     *
     * @param name  the name
     * @param value the value
     */
    public void setTemporaryParameter(String name, String value) {
        this.temporaryParameters.put(name, value);
    }

    /**
     * Remove temporary parameter.
     *
     * @param name the name
     */
    public void removeTemporaryParameter(String name) {
        this.temporaryParameters.remove(name);
    }

    /**
     * Gets session id.
     *
     * @return the session id
     */
    // TODO: Implement this method
    public String getSessionID() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    private byte[] generateHeader() {
        headerGenerated = true;

        StringBuilder header = new StringBuilder();
        header.append(String.format("HTTP/1.1 %s\r\n", this.status));
        header.append(String.format("Content-Type: %s%s\r\n", this.mimeType, this.mimeType.startsWith("text/") ? "; charset=" + this.encoding : ""));
        header.append("Server: simple java server\r\n");
        header.append("X-Powered-By: simple java server\r\n");

        if (this.contentLength != null) {
            header.append(String.format("Content-Length: %d\r\n", this.contentLength));
        }


        for (RCCookie cookie : this.outputCookies) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Set-Cookie: %s=%s", cookie.getName(), cookie.getValue()));
            if (cookie.getDomain() != null) {
                sb.append("; Domain=").append(cookie.getDomain());
            }
            if (cookie.getPath() != null) {
                sb.append("; Path=").append(cookie.getPath());
            }
            if (cookie.getMaxAge() != null) {
                sb.append("; Max-Age=").append(cookie.getMaxAge());
            }

            if (cookie.isHttpOnly()) {
                sb.append("; HttpOnly");
            }

            header.append(sb).append("\r\n");
        }

        header.append("\r\n");

        return header.toString().getBytes(StandardCharsets.ISO_8859_1);
    }

    private void generateHeaderIfNotGenerated() throws IOException {
        if (!headerGenerated) {
            this.charset = Charset.forName(this.encoding);
            this.write(generateHeader());
        }
    }

    /**
     * Write request context.
     *
     * @param data   the data
     * @param offset the offset
     * @param len    the len
     * @return the request context
     * @throws IOException the io exception
     */
    public RequestContext write(byte[] data, int offset, int len) throws IOException {
        this.generateHeaderIfNotGenerated();
        this.outputStream.write(data, offset, len);
        return this;
    }

    /**
     * Write request context.
     *
     * @param data the data
     * @return the request context
     * @throws IOException the io exception
     */
    public RequestContext write(byte[] data) throws IOException {
        this.generateHeaderIfNotGenerated();
        this.write(data, 0, data.length);
        return this;
    }

    /**
     * Write request context.
     *
     * @param text the text
     * @return the request context
     * @throws IOException the io exception
     */
    public RequestContext write(String text) throws IOException {
        this.generateHeaderIfNotGenerated();
        this.outputStream.write(text.getBytes(this.charset));
        return this;
    }
}
