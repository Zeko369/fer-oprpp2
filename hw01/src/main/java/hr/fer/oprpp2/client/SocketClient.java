package hr.fer.oprpp2.client;

import hr.fer.oprpp2.shared.*;

import java.io.IOException;
import java.net.*;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

/**
 * The type Socket client.
 *
 * @author franzekan
 */
public class SocketClient {
    private final String host;
    private final int port;

    private InetSocketAddress address;
    private DatagramSocket dSocket;

    private final BlockingQueue<Message> inQueue = new LinkedBlockingQueue<>();
    private final AtomicLong messageId = new AtomicLong(0);
    private final long randomKey;
    private long uid;

    /**
     * Instantiates a new Socket client.
     *
     * @param host the host
     * @param port the port
     */
    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;

        this.randomKey = (new Random()).nextLong();
    }

    /**
     * Listen.
     *
     * @param callback the callback
     */
    public void listen(Function<InMessage, Void> callback) {
        Thread t = new Thread(() -> {
            byte[] data = new byte[1024 * 8];
            DatagramPacket packet = new DatagramPacket(data, data.length);

            while (true) {
                try {
                    this.dSocket.setSoTimeout(0);
                    this.dSocket.receive(packet);
                    Message m = Message.deserializeRaw(packet.getData());

                    System.out.println(m);

                    switch (m.getType()) {
                        case Message.IN_MESSAGE -> {
                            callback.apply((InMessage) m);

                            Message back = new AckMessage(m.getIndex(), this.uid);
                            DatagramPacket backPack = new DatagramPacket(back.serialize(), back.serialize().length);
                            backPack.setSocketAddress(new InetSocketAddress(InetAddress.getByName(this.host), this.port));
                            System.out.println("Sending acc for " + m.getIndex());
                            this.dSocket.send(backPack);
                        }
                        case Message.ACK_MESSAGE -> this.inQueue.put(m);
                    }
                } catch (SocketTimeoutException e) {
                    //noinspection UnnecessaryContinue
                    continue;
                } catch (IOException | InterruptedException e) {
                    System.err.println("Error while receiving packet: " + e.getMessage());
                }
            }
        });

        t.start();
    }

    private boolean sendPacket(Message m) throws UnknownHostException {
        int count = 0;

        byte[] data = m.serialize();
        DatagramPacket packet = new DatagramPacket(data, data.length);
        packet.setSocketAddress(new InetSocketAddress(InetAddress.getByName(this.host), this.port));

        while (count < 5) {
            System.out.println("Sending packet " + m);
            try {
                this.dSocket.send(packet);
            } catch (IOException e) {
                System.err.println("Error while sending packet: " + e.getMessage());
                count++;
                continue;
            }

            Message recM;
            try {
                recM = this.inQueue.poll(5L, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                count++;
                continue;
            }

            if (recM instanceof AckMessage hm && hm.getIndex() == m.getIndex()) {
                return true;
            }

            count++;
        }

        return false;
    }

    /**
     * Send chat message boolean.
     *
     * @param text the text
     * @return the boolean
     * @throws UnknownHostException the unknown host exception
     */
    public boolean sendChatMessage(String text) throws UnknownHostException {
        return this.sendPacket(new OutMessage(this.messageId.getAndIncrement(), this.uid, text));
    }

    /**
     * Init.
     *
     * @throws SocketException the socket exception
     */
    public void init() throws SocketException {
        this.dSocket = new DatagramSocket(null);
        this.dSocket.setSoTimeout(2000);
    }

    /**
     * NOTE: This is used before queue is activated so here we do a real listen
     *
     * @param username the username
     * @return boolean boolean
     * @throws UnknownHostException the unknown host exception
     * @throws SocketException      the socket exception
     */
    public boolean join(String username) throws UnknownHostException, SocketException {
        Message m = new HelloMessage(this.messageId.incrementAndGet(), username, this.randomKey);
        byte[] data = m.serialize();

        DatagramPacket packet = new DatagramPacket(data, data.length);
        packet.setSocketAddress(new InetSocketAddress(InetAddress.getByName(this.host), this.port));

        this.dSocket.setSoTimeout(5000);

        int count = 0;
        boolean okReturned = false;
        // TODO: Don't hard code this 5
        while (!okReturned && count < 5) {
            try {
                this.dSocket.send(packet);
            } catch (IOException e) {
                System.err.println("Error while sending packet: " + e.getMessage());
            }

            byte[] recvBuffer = new byte[1024];
            DatagramPacket recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);

            try {
                this.dSocket.receive(recvPacket);
            } catch (SocketTimeoutException ste) {
                System.out.printf("Socket timeout, trying again [%d/5]", count);
                count++;
                continue;
            } catch (IOException e) {
                System.out.println("Error while receiving packet: " + e.getMessage());
                e.printStackTrace();
                continue;
            }

            Message recM = Message.deserializeRaw(recvPacket.getData());
            if (recM instanceof AckMessage hm) {
                this.uid = hm.getUID();
                return true;
            }
        }

        return false;
    }

    /**
     * Disconnect.
     */
    public void disconnect() {
        try {
            this.sendPacket(new ByeMessage(this.messageId.incrementAndGet(), this.uid));
            this.dSocket.close();
            System.exit(0);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
