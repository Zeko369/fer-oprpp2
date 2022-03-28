package hr.fer.oprpp2.shared;

import hr.fer.oprpp2.shared.util.Serializer;
import hr.fer.oprpp2.shared.util.Deserializer;

import java.io.IOException;

/**
 * The type Bye message.
 *
 * @author franzekan
 */
public class ByeMessage extends Message implements IHasUID {
    private final long UID;

    /**
     * Instantiates a new Bye message.
     *
     * @param index the index
     * @param UID   the uid
     */
    public ByeMessage(long index, long UID) {
        super(index, Message.BYE_MESSAGE);

        this.UID = UID;
    }

    public long getUID() {
        return UID;
    }

    @Override
    public byte[] serialize() {
        try {
            Serializer s = new Serializer(this);
            s.dos.writeLong(UID);

            return s.close();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Deserialize bye message.
     *
     * @param data the data
     * @return the bye message
     */
    public static ByeMessage deserialize(byte[] data) {
        try {
            Deserializer d = new Deserializer(data);
            long index = d.dis.readLong();
            long UID = d.dis.readLong();
            d.close();

            return new ByeMessage(index, UID);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("%s: %d", super.toString(), this.UID);
    }
}
