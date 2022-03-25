package hr.fer.oprpp2.client;

import hr.fer.oprpp2.shared.HelloMessage;
import hr.fer.oprpp2.shared.Message;

import java.io.IOException;
import java.net.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SocketClient {
    private final String host;
    private final int port;

    private InetSocketAddress address;
    private DatagramSocket dSocket;

    private final AtomicLong messageId = new AtomicLong(0);
    private final long randomKey;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;

        this.randomKey = (new Random()).nextLong();
    }

    public void listen() {
        byte[] data = new byte[1024 * 8];
        DatagramPacket packet = new DatagramPacket(data, data.length);

        while (true) {
            try {
                this.dSocket.receive(packet);
                Message m = Message.deserializeRaw(packet.getData());
                System.out.println(m);
            } catch (SocketTimeoutException e) {
                System.out.println("Socket timeout");
            } catch (IOException e) {
                System.err.println("Error while receiving packet: " + e.getMessage());
            }
        }
    }

    public void sendMessage(String text) {
    }

    public void init() throws SocketException {
        this.dSocket = new DatagramSocket(null);
//        this.dSocket.bind(new InetSocketAddress((InetAddress) null, this.port));
        this.dSocket.setSoTimeout(2000);
    }

    public void join(String username) throws UnknownHostException {
        Message m = new HelloMessage(this.messageId.incrementAndGet(), username, this.randomKey);
        byte[] data = m.serialize();

        DatagramPacket packet = new DatagramPacket(data, data.length);
        packet.setSocketAddress(new InetSocketAddress(InetAddress.getByName(this.host), this.port));

        for (int i = 0; i < 5; i++) {
            try {
                this.dSocket.send(packet);
                break;
            } catch (IOException e) {
                System.err.println("Error while sending packet: " + e.getMessage());
            }
        }
    }

    public void disconnect() {
        this.dSocket.close();
    }
}
