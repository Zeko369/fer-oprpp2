package shared;

public abstract class Message {
    private final long index;

    public Message(long index) {
        this.index = index;
    }

    public long getIndex() {
        return this.index;
    }

    public abstract byte getType();

    public abstract byte[] serialize();

    public abstract Message deserialize(byte[] bytes);
}
