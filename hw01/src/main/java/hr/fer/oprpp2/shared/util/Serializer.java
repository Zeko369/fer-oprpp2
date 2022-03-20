package hr.fer.oprpp2.shared.util;

import hr.fer.oprpp2.shared.Message;

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

    public byte[] close() throws IOException {
        this.dos.close();
        byte[] out = this.baos.toByteArray();
        this.baos.close();

        return out;
    }
}
