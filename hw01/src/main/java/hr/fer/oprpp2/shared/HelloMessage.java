package hr.fer.oprpp2.shared;

import hr.fer.oprpp2.shared.util.Serializer;
import hr.fer.oprpp2.shared.util.Deserializer;

import java.io.IOException;

public class HelloMessage extends Message {
    private final String name;
    private final long randomKey;

    public HelloMessage(long index, String name, long randomKey) {
        super(index, Message.HELLO_MESSAGE);

        this.name = name;
        this.randomKey = randomKey;
    }

    public String getName() {
        return this.name;
    }

    public long getRandomKey() {
        return this.randomKey;
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
            d.close();

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
