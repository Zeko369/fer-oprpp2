package shared;

import shared.util.Serializer;

import java.io.IOException;

public class ByeMessage extends Message {
    private final long UID;

    public ByeMessage(long index, long UID) {
        super(index);

        this.UID = UID;
    }

    @Override
    public byte getType() {
        return 3;
    }

    @Override
    public byte[] serialize() {
        try {
            Serializer s = new Serializer(this);
            s.dos.writeLong(UID);

            return s.toBytes();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public ByeMessage deserialize(byte[] data) {
        return null;
    }
}
