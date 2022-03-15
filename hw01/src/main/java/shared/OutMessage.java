package shared;

import shared.util.Serializer;

import java.io.IOException;

public class OutMessage extends Message {
    private final long UID;
    private final String text;

    public OutMessage(long index, long UID, String text) {
        super(index);

        this.UID = UID;
        this.text = text;
    }

    @Override
    public byte getType() {
        return 4;
    }

    @Override
    public byte[] serialize() {
        try {
            Serializer s = new Serializer(this);
            s.dos.writeLong(this.UID);
            s.dos.writeUTF(this.text);

            return s.toBytes();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public OutMessage deserialize(byte[] bytes) {
        return null;
    }
}
