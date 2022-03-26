package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.webserver.Util.LoadProperties;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SmartHttpServer {
    private final String address;
    private final String domainName;
    private final int port;
    private final int workerThreads;
    private final int sessionTimeout;
    private final Map<String, String> mimeTypes;
    private final ServerThread serverThread;
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
            server.start();
            System.out.printf("Server listening on http://%s:%d%n", server.domainName, server.port);
        } catch (SmartHttpServerException ex) {
            System.err.println("Error initializing server");
            System.err.println(ex.getMessage());
            System.exit(1);
            return;
        }

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }

    public SmartHttpServer(String configFileName) {
        Properties properties = LoadProperties.load(configFileName);

        this.address = properties.getProperty("server.address");
        this.domainName = properties.getProperty("server.domainName");
        this.port = Integer.parseInt(properties.getProperty("server.port"));
        this.workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
        this.documentRoot = Path.of(properties.getProperty("server.documentRoot"));
        this.sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));

        this.serverThread = new ServerThread();

        Properties mimeProperties = LoadProperties.load(properties.getProperty("server.mimeConfig"));
        this.mimeTypes = new HashMap<>();
        mimeProperties.keySet().forEach(key -> this.mimeTypes.put((String) key, mimeProperties.getProperty((String) key)));
    }

    protected synchronized void start() {
        this.serverThread.start();
        this.threadPool = Executors.newFixedThreadPool(this.workerThreads);
    }

    protected synchronized void stop() {
        //noinspection deprecation - TODO: think about a better way to stop the server
        this.serverThread.stop();
        this.threadPool.shutdown();
    }

    protected class ServerThread extends Thread {
        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket();
                serverSocket.bind(new InetSocketAddress(SmartHttpServer.this.address, SmartHttpServer.this.port));

                while (!Thread.currentThread().isInterrupted()) {
                    Socket client = serverSocket.accept();
                    ClientWorker worker = new ClientWorker(client);

                    System.out.println("New client connected");

                    SmartHttpServer.this.threadPool.submit(worker);
                }
            } catch (IOException e) {
                // TODO: Throw better thing here
                e.printStackTrace();
            }
        }
    }

    private class ClientWorker implements Runnable {
        private final Socket csocket;
        private InputStream istream;
        private OutputStream ostream;
        private HTTPRequest request;
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
            RequestContext rc = null;

            try {
                this.istream = this.csocket.getInputStream();
                this.ostream = new BufferedOutputStream(this.csocket.getOutputStream());

                try {
                    this.request = HTTPRequest.fromStream(this.istream);
                } catch (HTTPRequest.HTTPRequestException | MalformedURLException e) {
                    throw new HTTPError(HTTPStatus.BAD_REQUEST, e.getMessage());
                }

                if (!this.request.method().equals(HTTPMethod.GET)) {
                    // FIXME: While technically docs say you should return 400, IMO this should be a 405
                    throw new HTTPError(HTTPStatus.METHOD_NOT_ALLOWED);
                }

                if (!List.of(HTTPVersion.HTTP_1_0, HTTPVersion.HTTP_1_1).contains(this.request.version())) {
                    // FIXME: While technically docs say you should return 400, IMO this should be a 505
                    throw new HTTPError(new HTTPStatus(505, "HTTP Version not supported"));
                }

//                this.method = this.request.method();
//                this.version = this.request.version();
//                this.host = this.request.headers().getOrDefault("Host", SmartHttpServer.this.domainName);
//                if (this.host.contains(":")) {
//                    this.host = this.host.substring(0, this.host.indexOf(":"));
//                }

                this.params.putAll(this.request.getQuery());

                Path requestedPath = Paths.get(String.valueOf(SmartHttpServer.this.documentRoot), this.request.urlPath());
                // if requestedPath is not below documentRoot, return response status 403 forbidden

                File requestedFile = requestedPath.toFile();
                if (!requestedFile.exists()) {
                    throw new HTTPError(HTTPStatus.NOT_FOUND);
                }
                if (!requestedFile.isFile()) {
                    throw new HTTPError(HTTPStatus.FORBIDDEN, "Directory listing not allowed");
                }
                if (!requestedFile.canRead()) {
                    throw new HTTPError(HTTPStatus.FORBIDDEN, "File not readable");
                }

                String[] fileSplit = requestedFile.getName().split("\\.");
                String fileExtension = fileSplit[fileSplit.length - 1];

                rc = new RequestContext(this.ostream, this.params, this.permPrams, this.outputCookies);
                rc.setMimeType(SmartHttpServer.this.mimeTypes.getOrDefault(fileExtension, "application/octet-stream"));
                rc.setStatus(HTTPStatus.OK);

                try (FileInputStream fis = new FileInputStream(requestedFile)) {
                    rc.write(fis.readAllBytes());
                }
            } catch (HTTPError e) {
                System.out.println("error: " + e.getMessage());
                if (rc != null) {
                    throw new RuntimeException("RequestContext already generated");
                }

                rc = new RequestContext(this.ostream, this.params, this.permPrams, this.outputCookies);
                rc.setStatus(e.getStatus());
                rc.setMimeType("text/plain");
                try {
                    rc.write(e.getMessage().getBytes(StandardCharsets.US_ASCII));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    this.ostream.flush();
                    this.csocket.getOutputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
