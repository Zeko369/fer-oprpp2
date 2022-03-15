package client;

public record MessageRow(long index, String author, String text) {
    public String toString(String servername) {
        return String.format("[%s] Message from user: %s\n%s\n\n", servername, author, text);
    }
}
