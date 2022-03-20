package hr.fer.oprpp2.server;

import hr.fer.oprpp2.shared.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Server {
    private final int port;
    private final DatagramSocket dSocket;

    private final AtomicLong uidSequence = new AtomicLong(new Random().nextLong());
    private final List<Connection> connections;

    public Server(int port) throws SocketException {
        this.port = port;
        this.connections = new ArrayList<>();

        this.dSocket = new DatagramSocket(null);
        this.dSocket.bind(new InetSocketAddress((InetAddress) null, port));
    }

    public void listen() {
        System.out.println("Listening on port " + this.port);

        //noinspection InfiniteLoopStatement
        while (true) {
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {
                this.dSocket.receive(packet);
            } catch (IOException e) {
                continue;
            }

            Message message = Message.deserializeRaw(packet.getData());
            if (message == null) {
                System.err.println("Received invalid message");
                continue;
            }

            switch (message.getType()) {
                case Message.HELLO_MESSAGE -> this.handleHelloMessage((HelloMessage) message, packet);
                case Message.ACK_MESSAGE -> this.handleAckMessage((AckMessage) message, packet);
                case Message.BYE_MESSAGE -> this.handleByeMessage((ByeMessage) message, packet);
                case Message.OUT_MESSAGE -> this.handleOutMessage((OutMessage) message, packet);
                case Message.IN_MESSAGE -> this.handleInMessage((InMessage) message, packet);
            }
        }
    }

    private void handleHelloMessage(HelloMessage message, DatagramPacket packet) {
        Connection connection = new Connection(packet.getAddress(), packet.getPort(), message.getName(), this.uidSequence.incrementAndGet());
        this.connections.add(connection);
    }

    private void handleAckMessage(AckMessage message, DatagramPacket packet) {
        Connection connection = this.getConnection(packet.getAddress(), packet.getPort());
        if (connection != null) {
            System.out.println("here");
        }
    }

    private void handleByeMessage(ByeMessage message, DatagramPacket packet) {
        Connection connection = this.getConnection(packet.getAddress(), packet.getPort());
        if (connection != null) {
            connection.cleanup();
            this.connections.remove(connection);
        }
    }

    private void handleOutMessage(OutMessage message, DatagramPacket packet) {
        Connection connection = this.getConnection(packet.getAddress(), packet.getPort());
        if (connection != null) {
            System.out.println("here");
        }
    }

    private void handleInMessage(InMessage message, DatagramPacket packet) {
        Connection connection = this.getConnection(packet.getAddress(), packet.getPort());
        if (connection != null) {
            System.out.println("here");
        }
    }

    // util

    private Connection getConnection(InetAddress ip, int port) {
        return this.connections.stream()
                .filter(c -> c.ip().equals(ip) && c.port() == port)
                .findFirst()
                .orElse(null);
    }

    private Connection getConnectionByUID(long uid) {
        return this.connections.stream()
                .filter(c -> c.uid() == uid)
                .findFirst()
                .orElse(null);
    }
}
