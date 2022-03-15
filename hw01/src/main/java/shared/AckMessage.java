package shared;

import shared.util.Serializer;

import java.io.IOException;

public class AckMessage extends Message {
    private final long UID;

    public AckMessage(long index, long UID) {
        super(index);

        this.UID = UID;
    }


    @Override
    public byte getType() {
        return 2;
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
    public AckMessage deserialize(byte[] data) {
        return null;
    }
}
