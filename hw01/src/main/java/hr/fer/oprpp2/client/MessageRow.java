package hr.fer.oprpp2.client;

/**
 * The type Message row.
 *
 * @author franzekan
 */
public record MessageRow(long index, String author, String text) {
    /**
     * To string string.
     *
     * @param servername the servername
     * @return the string
     */
    public String toString(String servername) {
        return String.format("[%s] Message from user: %s\n%s\n\n", servername, author, text);
    }
}
