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

            System.out.println(message);

            try {
                switch (message.getType()) {
                    case Message.HELLO_MESSAGE -> this.handleHelloMessage((HelloMessage) message, packet);
                    case Message.ACK_MESSAGE -> this.handleAckMessage((AckMessage) message, packet);
                    case Message.BYE_MESSAGE -> this.handleByeMessage((ByeMessage) message, packet);
                    case Message.OUT_MESSAGE -> this.handleOutMessage((OutMessage) message, packet);
                    case Message.IN_MESSAGE -> this.handleInMessage((InMessage) message, packet);
                }
            } catch (IOException e) {
                System.err.println("Failed to handle message");
            }
        }
    }

    private void handleHelloMessage(HelloMessage message, DatagramPacket packet) throws IOException {
        System.out.printf("New connection: %s\n", message);

        synchronized (this.connections) {
            Connection connection = this.getConnection(packet.getAddress(), packet.getPort());
            if (connection == null) {
                connection = new Connection(packet.getAddress(),
                        packet.getPort(),
                        message.getName(),
                        this.uidSequence.incrementAndGet(),
                        this.dSocket
                );
                this.connections.add(connection);
            }

            Message m = new AckMessage(0, connection.uid());
            DatagramPacket p = new DatagramPacket(m.serialize(), m.serialize().length, packet.getAddress(), packet.getPort());
            this.dSocket.send(p);
        }
    }

    private void handleAckMessage(AckMessage message, DatagramPacket packet) {
        Connection connection;
        synchronized (this.connections) {
            connection = this.getConnectionByUID(message.getUID());
        }

        if (connection == null) {
            System.err.printf("Received ACK for unknown connection: %s\n", message);
            return;
        }

        connection.outboundMessages().add(message);
    }

    private void handleByeMessage(ByeMessage message, DatagramPacket packet) {
        Connection connection = this.getConnection(packet.getAddress(), packet.getPort());
        if (connection != null) {
            connection.cleanup();
            this.connections.remove(connection);
        }
    }

    private void handleOutMessage(OutMessage message, DatagramPacket packet) throws IOException {
        Connection c = this.getConnectionByUID(message.getUID());
        if (c == null) {
            System.err.println("Received out message from unknown connection");
            return;
        }

        this.sendToAll(c.username(), message.getText());
        this.sendToConnection(c, new AckMessage(message.getIndex(), c.uid()));
    }

    private void sendToConnection(Connection c, Message m) throws IOException {
        DatagramPacket p = new DatagramPacket(m.serialize(), m.serialize().length, c.ip(), c.port());
        this.dSocket.send(p);
    }

    private void sendToAll(String author, String text) {
        synchronized (this.connections) {
            for (Connection c : this.connections) {
                c.inboundMessages().add(new InMessage(c.inCounter().getAndIncrement(), author, text));
            }
        }
    }

    private void handleInMessage(InMessage message, DatagramPacket packet) {
//        Connection connection = this.getConnection(packet.getAddress(), packet.getPort());
//        if (connection != null) {
        System.out.println(message);
//        }
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
