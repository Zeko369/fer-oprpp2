package hr.fer.oprpp2.server;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar chat-server.jar <port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        try {
            Server server = new Server(port);
            server.listen();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
