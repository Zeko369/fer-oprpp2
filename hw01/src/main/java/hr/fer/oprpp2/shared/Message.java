package hr.fer.oprpp2.shared;

/**
 * The type Message.
 *
 * @author franzekan
 */
public abstract class Message {
    private final long index;
    private final byte type;

    /**
     * The constant HELLO_MESSAGE.
     */
    public static final byte HELLO_MESSAGE = 1;
    /**
     * The constant ACK_MESSAGE.
     */
    public static final byte ACK_MESSAGE = 2;
    /**
     * The constant BYE_MESSAGE.
     */
    public static final byte BYE_MESSAGE = 3;
    /**
     * The constant OUT_MESSAGE.
     */
    public static final byte OUT_MESSAGE = 4;
    /**
     * The constant IN_MESSAGE.
     */
    public static final byte IN_MESSAGE = 5;

    /**
     * Instantiates a new Message.
     *
     * @param index the index
     * @param type  the type
     */
    protected Message(long index, byte type) {
        this.index = index;
        this.type = type;
    }

    /**
     * Gets index.
     *
     * @return the index
     */
    public long getIndex() {
        return this.index;
    }

    public String toString() {
        String className = this.getClass().getSimpleName();

        return String.format("[%d]%s", this.index, className.substring(0, className.length() - 7));
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public byte getType() {
        return this.type;
    }

    /**
     * Serialize byte [ ].
     *
     * @return the byte [ ]
     */
    public abstract byte[] serialize();

    /**
     * Deserialize raw message.
     *
     * @param bytes the bytes
     * @return the message
     */
    public static Message deserializeRaw(byte[] bytes) {
        if (bytes.length < 1) {
            throw new IllegalArgumentException("Message empty");
        }

        return switch (bytes[0]) {
            case HELLO_MESSAGE -> HelloMessage.deserialize(bytes);
            case ACK_MESSAGE -> AckMessage.deserialize(bytes);
            case BYE_MESSAGE -> ByeMessage.deserialize(bytes);
            case OUT_MESSAGE -> OutMessage.deserialize(bytes);
            case IN_MESSAGE -> InMessage.deserialize(bytes);
            default -> throw new IllegalArgumentException("Invalid message");
        };
    }
}
