package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.webserver.HTTP.HTTPStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class RequestContext {
    public static class RCCookie {
        private final String name;
        private final String value;
        private final String domain;
        private final String path;
        private final Integer maxAge;

        public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
            this.name = name;
            this.value = value;
            this.domain = domain;
            this.path = path;
            this.maxAge = maxAge;
        }

        public String getName() {
            return this.name;
        }

        public String getValue() {
            return this.value;
        }

        public String getDomain() {
            return this.domain;
        }

        public String getPath() {
            return this.path;
        }

        public Integer getMaxAge() {
            return this.maxAge;
        }
    }

    private final OutputStream outputStream;
    private Charset charset;

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
    public static List<String> IGNORED_HEADERS = List.of("Set-Cookie", "Content-Length", "Content-Type");

    private boolean headerGenerated = false;

    private final IDispatcher dispatcher;

    public RequestContext(OutputStream outputStream, Map<String, String> parameters, Map<String, String> persistentParameters, List<RCCookie> outputCookies, Map<String, String> temporaryParameters, IDispatcher dispatcher) {
        this.outputStream = Objects.requireNonNull(outputStream);
        this.parameters = parameters == null ? new HashMap<>() : parameters;
        this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
        this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
        this.customHeaders = new HashMap<>();
        this.temporaryParameters = temporaryParameters == null ? new HashMap<>() : temporaryParameters;
        this.dispatcher = dispatcher;
    }

    public RequestContext(OutputStream outputStream, Map<String, String> parameters, Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
        this(outputStream, parameters, persistentParameters, outputCookies, null, null);
    }

    private void canChangeHeaders() {
        if (this.headerGenerated) {
            throw new RuntimeException("Headers already generated!");
        }
    }

    public void setEncoding(String encoding) {
        this.canChangeHeaders();
        this.encoding = encoding;
    }

    public void setStatus(HTTPStatus status) {
        this.canChangeHeaders();
        this.status = status;
    }

    public String getCustomHeader(String name) {
        return this.customHeaders.get(name);
    }

    public void setCustomHeader(String name, String value) {
        this.canChangeHeaders();
        if (IGNORED_HEADERS.contains(name)) {
            throw new RuntimeException("Cannot set header " + name);
        }

        this.customHeaders.put(name, value);
    }

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

    public void setMimeType(String mimeType) {
        this.canChangeHeaders();
        this.mimeType = mimeType;
    }

    public void setContentLength(Long contentLength) {
        this.canChangeHeaders();
        this.contentLength = contentLength;
    }

    public void addRCCookie(RCCookie cookie) {
        this.outputCookies.add(cookie);
    }

    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    public Set<String> getParameterNames() {
        return this.parameters.keySet();
    }

    public String getPersistentParameter(String name) {
        return this.persistentParameters.get(name);
    }

    public Set<String> getPersistentParameterNames() {
        return this.persistentParameters.keySet();
    }

    public void setPersistentParameter(String name, String value) {
        this.persistentParameters.put(name, value);
    }

    public void removePersistentParameter(String name) {
        this.persistentParameters.remove(name);
    }

    public String getTemporaryParameter(String name) {
        return this.temporaryParameters.get(name);
    }

    public Set<String> getTemporaryParameterNames() {
        return this.temporaryParameters.keySet();
    }

    public void setTemporaryParameter(String name, String value) {
        this.temporaryParameters.put(name, value);
    }

    public void removeTemporaryParameter(String name) {
        this.temporaryParameters.remove(name);
    }

    // TODO: Implement this method
    public String getSessionID() {
        return "";
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
            sb.append(String.format("Set-Cookie: %s=\"%s\"", cookie.getName(), cookie.getValue()));
            if (cookie.getDomain() != null) {
                sb.append("; Domain=").append(cookie.getDomain());
            }
            if (cookie.getPath() != null) {
                sb.append("; Path=").append(cookie.getPath());
            }
            if (cookie.getMaxAge() != null) {
                sb.append("; Max-Age=").append(cookie.getMaxAge());
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

    public RequestContext write(byte[] data, int offset, int len) throws IOException {
        this.generateHeaderIfNotGenerated();
        this.outputStream.write(data, offset, len);
        return this;
    }

    public RequestContext write(byte[] data) throws IOException {
        this.generateHeaderIfNotGenerated();
        this.write(data, 0, data.length);
        return this;
    }

    public RequestContext write(String text) throws IOException {
        this.generateHeaderIfNotGenerated();
        this.outputStream.write(text.getBytes(this.charset));
        return this;
    }
}
