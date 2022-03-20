package hr.fer.oprpp2.shared.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class Deserializer {
    private final ByteArrayInputStream is;
    public final DataInputStream dis;

    public Deserializer(byte[] bytes) throws IOException {
        this.is = new ByteArrayInputStream(bytes);
        this.dis = new DataInputStream(is);
        this.dis.readByte();
    }

    public void close() throws IOException {
        this.dis.close();
        this.is.close();
    }
}
