package shared;

import shared.util.Deserializer;
import shared.util.Serializer;

import java.io.IOException;

public class InMessage extends Message {
    private final String text;
    private final String author;

    public InMessage(long index, String author, String text) {
        super(index);

        this.author = author;
        this.text = text;
    }

    @Override
    public byte getType() {
        return Message.IN_MESSAGE;
    }

    @Override
    public byte[] serialize() {
        try {
            Serializer s = new Serializer(this);
            s.dos.writeUTF(this.author);
            s.dos.writeUTF(this.text);

            return s.close();
        } catch (IOException e) {
            return null;
        }
    }

    public static InMessage deserialize(byte[] data) {
        try {
            Deserializer d = new Deserializer(data);
            long index = d.dis.readLong();
            String author = d.dis.readUTF();
            String text = d.dis.readUTF();

            return new InMessage(index, author, text);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("%s: (%s), %s", super.toString(), this.author, this.text);
    }
}
