package hr.fer.oprpp2.server;

import hr.fer.oprpp2.server.Util.SendAndWait;
import hr.fer.oprpp2.shared.Message;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Connection.
 *
 * @author franzekan
 */
public record Connection(
        InetAddress ip,
        int port,
        String username,
        long uid,
        AtomicInteger inCounter,
        BlockingQueue<Message> inboundMessages,
        AtomicInteger outCounter,
        BlockingQueue<Message> outboundMessages
) {
    /**
     * The type Worker.
     *
     * @author franzekan
     */
    public class Worker implements Runnable {
        private final DatagramSocket socket;

        /**
         * Instantiates a new Worker.
         *
         * @param socket the socket
         */
        public Worker(DatagramSocket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            //noinspection InfiniteLoopStatement
            while (true) {
                try {
                    Message message = Connection.this.inboundMessages.take();
                    SendAndWait.send(socket, message, Connection.this);
                } catch (InterruptedException | SocketException | UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Instantiates a new Connection.
     *
     * @param ip       the ip
     * @param port     the port
     * @param username the username
     * @param uid      the uid
     * @param socket   the socket
     */
    public Connection(InetAddress ip, int port, String username, long uid, DatagramSocket socket) {
        this(ip,
                port,
                username,
                uid,
                new AtomicInteger(0),
                new LinkedBlockingQueue<>(),
                new AtomicInteger(0),
                new LinkedBlockingQueue<>()
        );

        new Thread(new Worker(socket)).start();
    }

    /**
     * Cleanup.
     */
    public void cleanup() {
        System.out.printf("[user:%d] Connection closed\n", this.uid);
    }
}
