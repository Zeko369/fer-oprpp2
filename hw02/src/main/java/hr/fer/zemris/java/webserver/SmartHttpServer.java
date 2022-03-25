package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.webserver.Util.LoadProperties;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutorService;

public class SmartHttpServer {
    private final String address;
    private final String domainName;
    private final int port;
    private final int workerThreads;
    private final int sessionTimeout;
    private final Map<String, String> mimeTypes;
    private ServerThread serverThread;
    private ExecutorService threadPool;
    private final Path documentRoot;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java SmartHttpServer ./path/to/config.properties");
            System.exit(1);
        }

        SmartHttpServer server;
        try {
            server = new SmartHttpServer(args[0]);
        } catch (SmartHttpServerException ex) {
            System.err.println("Error initializing server");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    public SmartHttpServer(String configFileName) {
        Properties properties = LoadProperties.load(configFileName);

        this.address = properties.getProperty("server.address");
        this.domainName = properties.getProperty("server.domainName");
        this.port = Integer.parseInt(properties.getProperty("server.port"));
        this.workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
        this.documentRoot = Path.of(properties.getProperty("server.documentRoot"));

        Properties mimeProperties = LoadProperties.load(properties.getProperty("server.mimeConfig"));
        this.mimeTypes = new HashMap<>();
        mimeProperties.keySet().forEach(key -> this.mimeTypes.put((String) key, mimeProperties.getProperty((String) key)));

        this.sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
    }

    protected synchronized void start() {
        // … start server thread if not already running …
        // … init threadpool by Executors.newFixedThreadPool(...); …
    }

    protected synchronized void stop() {
        // … signal server thread to stop running …
        // … shutdown threadpool …
    }

    protected static class ServerThread extends Thread {
        @Override
        public void run() {
            // given in pesudo-code:
            // open serverSocket on specified port
            // while(true) {
            // Socket client = serverSocket.accept();
            // ClientWorker cw = new ClientWorker(client);
            // submit cw to threadpool for execution
            // }
        }
    }

    private static class ClientWorker implements Runnable {
        private final Socket csocket;
        private InputStream istream;
        private OutputStream ostream;
        private String version;
        private String method;
        private String host;
        private final Map<String, String> params = new HashMap<>();
        private final Map<String, String> tempParams = new HashMap<>();
        private final Map<String, String> permPrams = new HashMap<>();
        private final List<RequestContext.RCCookie> outputCookies = new ArrayList<>();
        private String SID;

        public ClientWorker(Socket csocket) {
            super();
            this.csocket = csocket;
        }

        @Override
        public void run() {
            // obtain input stream from socket
            // obtain output stream from socket
            // Then read complete request header from your client in separate method...
            List<String> request = null; // readRequest();
            // If header is invalid (less then a line at least) return response status 400
            String firstLine = request.get(0);
            // Extract (method, requestedPath, version) from firstLine
            // if method not GET or version not HTTP/1.0 or HTTP/1.1 return response status 400
            // Go through headers, and if there is header “Host: xxx”, assign host property
            // to trimmed value after “Host:”; else, set it to server’s domainName
            // If xxx is of form some-name:number, just remember “some-name”-part
            String path;
            String paramString;
            // (path, paramString) = split requestedPath to path and parameterString
            // parseParameters(paramString); ==> your method to fill map parameters
            // requestedPath = resolve path with respect to documentRoot
            // if requestedPath is not below documentRoot, return response status 403 forbidden
            // check if requestedPath exists, is file and is readable; if not, return status 404
            // else extract file extension
            // find in mimeTypes map appropriate mimeType for current file extension
            // (you filled that map during the construction of SmartHttpServer from mime.properties)
            // if no mime type found, assume application/octet-stream
            // create a rc = new RequestContext(...); set mime-type; set status to 200
            // If you want, you can modify RequestContext to allow you to add additional headers
            // so that you can add “Content-Length: 12345” if you know that file has 12345 bytes
            // open file, read its content and write it to rc (that will generate header and send
            // file bytes to client)
        }
    }
}
