package hr.fer.oprpp2.shared;

import hr.fer.oprpp2.shared.util.Serializer;
import hr.fer.oprpp2.shared.util.Deserializer;

import java.io.IOException;

public class OutMessage extends Message implements IHasUID {
    private final long UID;
    private final String text;

    public OutMessage(long index, long UID, String text) {
        super(index, Message.OUT_MESSAGE);

        this.UID = UID;
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public long getUID() {
        return this.UID;
    }

    @Override
    public byte[] serialize() {
        try {
            Serializer s = new Serializer(this);
            s.dos.writeLong(this.UID);
            s.dos.writeUTF(this.text);

            return s.close();
        } catch (IOException e) {
            return null;
        }
    }

    public static OutMessage deserialize(byte[] data) {
        try {
            Deserializer d = new Deserializer(data);
            long index = d.dis.readLong();
            long uid = d.dis.readLong();
            String text = d.dis.readUTF();
            d.close();

            return new OutMessage(index, uid, text);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("%s: %d, %s", super.toString(), this.UID, this.text);
    }
}
