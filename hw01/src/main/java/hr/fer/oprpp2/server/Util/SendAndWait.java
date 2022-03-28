package hr.fer.oprpp2.server.Util;

import hr.fer.oprpp2.server.Connection;
import hr.fer.oprpp2.shared.AckMessage;
import hr.fer.oprpp2.shared.Message;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.TimeUnit;

/**
 * The type Send and wait.
 *
 * @author franzekan
 */
public class SendAndWait {
    /**
     * Send.
     *
     * @param socket the socket
     * @param m      the m
     * @param c      the c
     * @throws SocketException      the socket exception
     * @throws UnknownHostException the unknown host exception
     */
    public static void send(DatagramSocket socket, Message m, Connection c) throws SocketException, UnknownHostException {
        int tries = 0;

        socket.setSoTimeout(5000);
        byte[] buf = m.serialize();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, c.ip(), c.port());

        while (tries < 5) {
            try {
                socket.send(packet);
            } catch (IOException e) {
                System.err.printf("[error]: Error sending packet, %d tries left.", 5 - tries);
                tries++;
                continue;
            }

            try {
                Message received = c.outboundMessages().poll(5, TimeUnit.SECONDS);
                if (received == null) {
                    tries++;
                    continue;
                }

                if (received.getType() == Message.ACK_MESSAGE) {
                    AckMessage ack = (AckMessage) received;
                    if (ack.getIndex() == m.getIndex() && ack.getUID() == c.uid()) {
                        System.out.printf("[info]: Message sent successfully. [%d, user:%d]\n", m.getIndex(), c.uid());
                        return;
                    }
                }
            } catch (InterruptedException e) {
                System.err.printf("[error]: Error sending packet, %d tries left.", 5 - tries);
            }

            tries++;
        }
    }
}
