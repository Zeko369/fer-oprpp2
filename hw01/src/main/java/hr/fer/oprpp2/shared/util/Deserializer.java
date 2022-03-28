package hr.fer.oprpp2.shared.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * The type Deserializer.
 *
 * @author franzekan
 */
public class Deserializer {
    private final ByteArrayInputStream is;
    /**
     * The Dis.
     */
    public final DataInputStream dis;

    /**
     * Instantiates a new Deserializer.
     *
     * @param bytes the bytes
     * @throws IOException the io exception
     */
    public Deserializer(byte[] bytes) throws IOException {
        this.is = new ByteArrayInputStream(bytes);
        this.dis = new DataInputStream(is);
        this.dis.readByte();
    }

    /**
     * Close.
     *
     * @throws IOException the io exception
     */
    public void close() throws IOException {
        this.dis.close();
        this.is.close();
    }
}
