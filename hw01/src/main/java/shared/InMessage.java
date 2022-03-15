package shared;

import shared.util.Serializer;

import java.io.IOException;

public class InMessage extends Message {
    private final String text;
    private final String author;

    public InMessage(long index, String text, String author) {
        super(index);

        this.text = text;
        this.author = author;
    }

    @Override
    public byte getType() {
        return 5;
    }

    @Override
    public byte[] serialize() {
        try {
            Serializer s = new Serializer(this);
            s.dos.writeUTF(this.author);
            s.dos.writeUTF(this.text);

            return s.toBytes();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public InMessage deserialize(byte[] bytes) {
        return null;
    }
}
