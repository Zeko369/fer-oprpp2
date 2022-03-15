package shared;

public abstract class Message {
    private final long index;

    public static final byte HELLO_MESSAGE = 1;
    public static final byte ACK_MESSAGE = 2;
    public static final byte BYE_MESSAGE = 3;
    public static final byte OUT_MESSAGE = 4;
    public static final byte IN_MESSAGE = 5;

    protected Message(long index) {
        this.index = index;
    }

    public long getIndex() {
        return this.index;
    }

    public String toString() {
        String className = this.getClass().getSimpleName();

        return String.format("[%d]%s", this.index, className.substring(0, className.length() - 7));
    }

    public abstract byte getType();

    public abstract byte[] serialize();

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
