package hr.fer.oprpp2.shared;

import hr.fer.oprpp2.shared.util.Serializer;
import hr.fer.oprpp2.shared.util.Deserializer;

import java.io.IOException;

public class AckMessage extends Message {
    private final long UID;

    public AckMessage(long index, long UID) {
        super(index);

        this.UID = UID;
    }

    public long getUID() {
        return UID;
    }

    @Override
    public byte getType() {
        return ACK_MESSAGE;
    }

    @Override
    public byte[] serialize() {
        try {
            Serializer s = new Serializer(this);
            s.dos.writeLong(UID);

            return s.close();
        } catch (IOException e) {
            return null;
        }
    }

    public static AckMessage deserialize(byte[] data) {
        try {
            Deserializer d = new Deserializer(data);
            long index = d.dis.readLong();
            long UID = d.dis.readLong();

            return new AckMessage(index, UID);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("%s: %d", super.toString(), this.UID);
    }
}
