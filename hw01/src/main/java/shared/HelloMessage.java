package shared;

import shared.util.Deserializer;
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

    public String getName() {
        return name;
    }

    @Override
    public byte getType() {
        return Message.HELLO_MESSAGE;
    }

    @Override
    public byte[] serialize() {
        try {
            Serializer s = new Serializer(this);
            s.dos.writeUTF(this.name);
            s.dos.writeLong(this.randomKey);

            return s.close();
        } catch (IOException e) {
            return null;
        }
    }

    public static HelloMessage deserialize(byte[] data) {
        try {
            Deserializer d = new Deserializer(data);
            long index = d.dis.readLong();
            String name = d.dis.readUTF();
            long randomKey = d.dis.readLong();

            return new HelloMessage(index, name, randomKey);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("%s: %s, %d", super.toString(), this.name, this.randomKey);
    }
}
