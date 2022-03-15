package server;

public class Main {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.err.println("Required only 1 argument, server port");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
    }
}
