package shared.util;

import shared.Message;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Serializer {
    private final ByteArrayOutputStream baos;
    public final DataOutputStream dos;

    public Serializer(Message m) throws IOException {
        this.baos = new ByteArrayOutputStream();
        this.dos = new DataOutputStream(this.baos);

        dos.writeByte(m.getType());
        dos.writeLong(m.getIndex());
    }

    public byte[] toBytes() throws IOException {
        this.dos.close();
        return this.baos.toByteArray();
    }
}
