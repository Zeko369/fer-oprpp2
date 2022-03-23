package hr.fer.zemris.java.webserver;

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
    private int statusCode = 200;
    private String statusText = "OK";
    private String mimeType = "text/html";
    private Long contentLength = null;

    private final Map<String, String> parameters;
    private Map<String, String> temporaryParameters;
    private final Map<String, String> persistentParameters;
    private final List<RCCookie> outputCookies;

    private boolean headerGenerated = false;

    public RequestContext(OutputStream outputStream, Map<String, String> parameters, Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
        this.outputStream = Objects.requireNonNull(outputStream);
        this.parameters = parameters == null ? new HashMap<>() : parameters;
        this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
        this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
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

    public void setStatusCode(int statusCode) {
        this.canChangeHeaders();
        this.statusCode = statusCode;
    }

    public void setStatusText(String statusText) {
        this.canChangeHeaders();
        this.statusText = statusText;
    }

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
        header.append(String.format("HTTP/1.1 %d %s\r\n", this.statusCode, this.statusText));
        header.append(String.format("Content-Type: %s%s\r\n", this.mimeType, this.mimeType.startsWith("text/") ? "; charset=" + this.encoding : ""));

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
