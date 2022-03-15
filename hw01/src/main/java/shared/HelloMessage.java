package shared;

import shared.util.Serializer;

import java.io.IOException;

public class HelloMessage extends Message {
    private final String name;
    private final long randomKey;

    public HelloMessage(long index, String name, long randomKey) {
        super(index);

        this.name = name;
        this.randomKey = randomKey;
    }

    @Override
    public byte getType() {
        return 1;
    }

    @Override
    public byte[] serialize() {
        try {
            Serializer s = new Serializer(this);
            s.dos.writeUTF(this.name);
            s.dos.writeLong(this.randomKey);

            return s.toBytes();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public HelloMessage deserialize(byte[] data) {
        return null;
    }
}
