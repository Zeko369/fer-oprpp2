package hr.fer.zemris.java.webserver.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public record HTTPRequest(String version, String urlPath, String queryString, String method,
                          Map<String, String> headers) {
    public static class HTTPRequestException extends RuntimeException {
        public HTTPRequestException(String message) {
            super(message);
        }
    }

    // TODO: support for multiple key params into arrays
    public Map<String, String> getQuery() {
        if (queryString == null || queryString.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, String> query = new HashMap<>();
        Arrays.stream(this.queryString.split("&")).forEach(param -> {
            String[] keyValue = param.split("=");
            query.put(keyValue[0], keyValue[1]);
        });

        return query;
    }

    // STATIC HELPERS

    public static HTTPRequest fromStream(InputStream inputStream) throws HTTPRequestException, MalformedURLException {
        byte[] raw;
        try {
            raw = readRequestRaw(inputStream);
        } catch (IOException ex) {
            throw new HTTPRequestException("Error while reading request.");
        }

        String rawRequest = new String(raw, StandardCharsets.UTF_8);
        List<String> headers = readHeaders(rawRequest);

        if (headers.isEmpty()) {
            throw new HTTPRequestException("Empty request received.");
        }

        String[] firstLine = headers.get(0).split(" ");
        if (firstLine.length != 3) {
            throw new HTTPRequestException("Invalid request received.");
        }

        String method = firstLine[0].toUpperCase();
        String url = firstLine[1];
        String urlPath = url.split("\\?")[0];
        String queryString = url.contains("?") ? url.substring(url.indexOf("?") + 1) : null;
        String version = firstLine[2];

        Map<String, String> headersMap = new HashMap<>();
        for (int i = 1; i < headers.size(); i++) {
            String header = headers.get(i);
            String key = header.split(":")[0];
            String value = header.substring(header.indexOf(":") + 1).trim();
            if (value.isEmpty()) {
                throw new HTTPRequestException("Invalid header received.");
            }

            headersMap.put(key, value);
        }


        return new HTTPRequest(version, urlPath, queryString, method, headersMap);
    }

    private static List<String> readHeaders(String header) {
        List<String> headers = new ArrayList<>();
        String[] headerLines = header.split("\n");
        for (String line : headerLines) {
            if (line.isEmpty()) {
                break;
            }

            headers.add(line);
        }
        return headers;
    }

    private static byte[] readRequestRaw(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int state = 0;
        l:
        while (true) {
            byte b = (byte) inputStream.read();
            if (b == -1) {
                if (baos.size() == 0) {
                    throw new IOException("Incomplete header received.");
                }

                break;
            }

            if (b != 0x0d) {
                baos.write(b);
            }

            switch (state) {
                case 0 -> state = b == 0x0d ? 1 : b == 0x0a ? 4 : state;
                case 1 -> state = b == 0x0a ? 2 : 0;
                case 2 -> state = b == 0x0d ? 3 : 0;
                case 3, 4 -> {
                    if (b == 0x0a) {
                        break l;
                    } else {
                        state = 0;
                    }
                }
            }
        }

        return baos.toByteArray();
    }
}
