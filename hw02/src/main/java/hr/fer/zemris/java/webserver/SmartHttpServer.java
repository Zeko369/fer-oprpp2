package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.node.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.shared.FileLoader;
import hr.fer.zemris.java.webserver.HTTP.*;
import hr.fer.zemris.java.webserver.Util.LoadProperties;
import hr.fer.zemris.java.webserver.Util.WorkerLoader;

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
    private final ServerThread serverThread;
    private ExecutorService threadPool;
    private final Path documentRoot;

    private final WorkerLoader workerLoader = new WorkerLoader();
    private final Map<String, IWebWorker> workersMap = new HashMap<>();
    private final Map<String, String> mimeTypes = new HashMap<>();

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
        mimeProperties.keySet().forEach(key -> this.mimeTypes.put((String) key, mimeProperties.getProperty((String) key)));

        Properties workersProperties = LoadProperties.load(properties.getProperty("server.workers"));
        workersProperties.forEach((key, value) -> this.workersMap.put((String) key, this.workerLoader.get((String) value)));
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

    private class ClientWorker implements Runnable, IDispatcher {
        private final Socket csocket;
        private InputStream istream;
        private OutputStream ostream;
        private HTTPRequest request;
        private final Map<String, String> params = new HashMap<>();
        private final Map<String, String> tempParams = new HashMap<>();
        private final Map<String, String> permPrams = new HashMap<>();
        private final List<RequestContext.RCCookie> outputCookies = new ArrayList<>();
        private RequestContext rc = null;
        private String SID;

        public ClientWorker(Socket csocket) {
            super();
            this.csocket = csocket;
        }

        @Override
        public void run() {
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

                this.params.putAll(this.request.getQuery());
                this.internalDispatchRequest(this.request.urlPath(), true);
            } catch (HTTPError e) {
                System.out.println("error: " + e.getMessage());
                if (this.rc.isHeaderGenerated()) {
                    throw new RuntimeException("RequestContext already generated");
                }

                this.rc.setStatus(e.getStatus());
                this.rc.setMimeType("text/plain");
                try {
                    this.rc.write(e.getMessage().getBytes(StandardCharsets.US_ASCII));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (Exception e) {
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

        @Override
        public void dispatchRequest(String urlPath) throws Exception {
            this.internalDispatchRequest(urlPath, false);
        }

        private void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
            Path requestedPath = Paths.get(String.valueOf(SmartHttpServer.this.documentRoot), urlPath);
            if (this.rc == null) {
                this.rc = new RequestContext(this.ostream, this.params, this.permPrams, this.outputCookies, null, this);
            }

            if (urlPath.startsWith("/ext")) {
                String classPath = String.format("hr.fer.zemris.java.webserver.workers.%s", urlPath.substring(5));
                IWebWorker worker = SmartHttpServer.this.workerLoader.get(classPath);
                if (worker == null) {
                    throw new HTTPError(HTTPStatus.NOT_FOUND);
                }

                worker.processRequest(this.rc);
                return;
            }

            IWebWorker worker = SmartHttpServer.this.workersMap.get(urlPath);
            if (worker != null) {
                worker.processRequest(rc);
                return;
            }

            // FIXME: This
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

            if (fileExtension.equals("smscr")) {
                String code = FileLoader.loadCode(requestedFile);

                DocumentNode root = new SmartScriptParser(code).getDocumentNode();
                new SmartScriptEngine(root, rc).execute();
                return;
            }

            this.rc.setMimeType(SmartHttpServer.this.mimeTypes.getOrDefault(fileExtension, "application/octet-stream"));
            this.rc.setStatus(HTTPStatus.OK);

            try (FileInputStream fis = new FileInputStream(requestedFile)) {
                this.rc.write(fis.readAllBytes());
            }
        }
    }
}
