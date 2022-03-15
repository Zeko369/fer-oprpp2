package server;

import java.net.*;

public class Main {
    public static void main(String[] args) throws SocketException {
        if (args.length != 1) {
            System.err.println("Usage: java -jar chat-server.jar <port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        Server server = new Server(port);
        server.listen();
    }
}
