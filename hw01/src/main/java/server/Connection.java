package server;

import java.net.InetAddress;

public record Connection(InetAddress ip, int port, String username, long uid) {
    public void cleanup() {
        System.out.println("Connection closed");
    }
}
