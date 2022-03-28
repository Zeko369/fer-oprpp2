package hr.fer.oprpp2.shared.util;

import hr.fer.oprpp2.shared.Message;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The type Serializer.
 *
 * @author franzekan
 */
public class Serializer {
    private final ByteArrayOutputStream baos;
    /**
     * The Dos.
     */
    public final DataOutputStream dos;

    /**
     * Instantiates a new Serializer.
     *
     * @param m the m
     * @throws IOException the io exception
     */
    public Serializer(Message m) throws IOException {
        this.baos = new ByteArrayOutputStream();
        this.dos = new DataOutputStream(this.baos);

        dos.writeByte(m.getType());
        dos.writeLong(m.getIndex());
    }

    /**
     * Close byte [ ].
     *
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    public byte[] close() throws IOException {
        this.dos.close();
        byte[] out = this.baos.toByteArray();
        this.baos.close();

        return out;
    }
}
