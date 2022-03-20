package hr.fer.oprpp2.server;

import hr.fer.oprpp2.shared.Message;

import java.net.InetAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public record Connection(
        InetAddress ip,
        int port,
        String username,
        long uid,
        BlockingQueue<Message> inboundMessages,
        BlockingQueue<Message> outboundMessages
) {
    public Connection(InetAddress ip, int port, String username, long uid) {
        this(ip, port, username, uid, new LinkedBlockingQueue<>(), new LinkedBlockingQueue<>());
    }

    public void cleanup() {
        System.out.println("Connection closed");
    }
}
