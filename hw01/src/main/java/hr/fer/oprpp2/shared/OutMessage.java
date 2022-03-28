package hr.fer.oprpp2.shared;

import hr.fer.oprpp2.shared.util.Serializer;
import hr.fer.oprpp2.shared.util.Deserializer;

import java.io.IOException;

/**
 * The type Out message.
 *
 * @author franzekan
 */
public class OutMessage extends Message implements IHasUID {
    private final long UID;
    private final String text;

    /**
     * Instantiates a new Out message.
     *
     * @param index the index
     * @param UID   the uid
     * @param text  the text
     */
    public OutMessage(long index, long UID, String text) {
        super(index, Message.OUT_MESSAGE);

        this.UID = UID;
        this.text = text;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
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

    /**
     * Deserialize out message.
     *
     * @param data the data
     * @return the out message
     */
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
