package shared;

public abstract class Message {
    private final long index;

    public Message(long index) {
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
            case 1 -> HelloMessage.deserialize(bytes);
            case 2 -> AckMessage.deserialize(bytes);
            case 3 -> ByeMessage.deserialize(bytes);
            case 4 -> OutMessage.deserialize(bytes);
            case 5 -> InMessage.deserialize(bytes);
            default -> throw new IllegalArgumentException("Invalid message");
        };
    }
}
