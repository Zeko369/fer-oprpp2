package hr.fer.oprpp2.client;

import java.net.*;
import java.util.concurrent.atomic.AtomicLong;

public class SocketClient {
    private final String host;
    private final int port;

    private InetSocketAddress address;
    private DatagramSocket dSocket;

    private final AtomicLong messageId = new AtomicLong(0);

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void listen() throws UnknownHostException, SocketException {
        this.dSocket = new DatagramSocket(null);
        this.dSocket.bind(new InetSocketAddress((InetAddress) null, this.port));
    }

    public void disconnect() {
        this.dSocket.close();
    }
}
