package hr.fer.oprpp2.shared;

import hr.fer.oprpp2.shared.util.Serializer;
import hr.fer.oprpp2.shared.util.Deserializer;

import java.io.IOException;

/**
 * The type In message.
 *
 * @author franzekan
 */
public class InMessage extends Message {
    private final String text;
    private final String author;

    /**
     * Instantiates a new In message.
     *
     * @param index  the index
     * @param author the author
     * @param text   the text
     */
    public InMessage(long index, String author, String text) {
        super(index, Message.IN_MESSAGE);

        this.author = author;
        this.text = text;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
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

    /**
     * Deserialize in message.
     *
     * @param data the data
     * @return the in message
     */
    public static InMessage deserialize(byte[] data) {
        try {
            Deserializer d = new Deserializer(data);
            long index = d.dis.readLong();
            String author = d.dis.readUTF();
            String text = d.dis.readUTF();
            d.close();

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
