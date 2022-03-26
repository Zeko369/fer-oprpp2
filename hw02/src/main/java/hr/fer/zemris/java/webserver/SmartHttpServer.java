package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.webserver.Util.LoadProperties;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
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
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                this.istream = this.csocket.getInputStream();
                this.ostream = new BufferedOutputStream(this.csocket.getOutputStream());

                int state = 0;
                l:
                while (true) {
                    byte b = (byte) this.istream.read();
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
                        case 0 -> {

                            if (b == 0x0d) {
                                state = 1;
                            } else if (b == 0x0a) {
                                state = 4;
                            }
                        }
                        case 1 -> {

                            if (b == 0x0a) {
                                state = 2;
                            } else {
                                state = 0;
                            }
                        }
                        case 2 -> {

                            if (b == 0x0d) {
                                state = 3;
                            } else {
                                state = 0;
                            }
                        }
                        case 3, 4 -> {
                            if (b == 0x0a) {
                                break l;
                            } else {
                                state = 0;
                            }
                        }
                    }
                }

                System.out.println(baos.toString());

                byte[] header = new byte[]{
                        'h', 'e', 'l', 'l', 'o', ' ', 'w', 'o', 'r', 'l', 'd', '\r', '\n',
                };

                this.ostream.write(
                        ("HTTP/1.1 " + 200 + " " + "OK" + "\r\n" +
                                "Server: simple java server\r\n" +
                                "Content-Type: " + "text/plain" + "\r\n" +
                                "Content-Length: " + header.length + "\r\n" +
                                "Connection: close\r\n" +
                                "\r\n").getBytes(StandardCharsets.US_ASCII)
                );
                this.ostream.write(header);

                this.ostream.flush();
                this.csocket.getOutputStream().close();

                // Then read complete request header from your client in separate method...
//                List<String> request = this.readRequest();
//                System.out.println("Request: " + request);
//
//                if (request.isEmpty()) {
//                    System.out.println("HEREJk");
//                    this.returnError(400, "Bad request");
//                    return;
//                }
//
//                // If header is invalid (less then a line at least) return response status 400
//                String firstLine = request.get(0);
//                System.out.println(firstLine);


//                this.returnError(400, "Bad request");
//                this.csocket.getOutputStream().close();

                // Extract (method, requestedPath, version) from firstLine
                // if method not GET or version not HTTP/1.0 or HTTP/1.1 return response status 400
                // Go through headers, and if there is header “Host: xxx”, assign host property
                // to trimmed value after “Host:”; else, set it to server’s domainName
                // If xxx is of form some-name:number, just remember “some-name”-part
//                String path;
//                String paramString;
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private List<String> readRequest() throws IOException {
            List<String> output = new ArrayList<>();

            byte[] arr = this.csocket.getInputStream().readAllBytes();
            System.out.println(new String(arr));

            return output;
        }

        private void returnError(int status, String message) throws IOException {
            RequestContext rc = new RequestContext(this.csocket.getOutputStream(), this.params, this.permPrams, this.outputCookies);
            rc.setMimeType("text/plain");
            rc.setStatusCode(status);
            rc.setStatusText(message);

            this.csocket.getOutputStream().flush();
        }
    }
}
