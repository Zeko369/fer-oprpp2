package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Object multistack.
 *
 * @author franzekan
 */
public class ObjectMultistack {
    private final Map<String, MultistackEntry> map = new HashMap<>();

    private static class MultistackEntry {
        /**
         * The Value.
         */
        ValueWrapper value;
        /**
         * The Next.
         */
        MultistackEntry next;

        /**
         * Instantiates a new Multistack entry.
         *
         * @param value the value
         * @param next  the next
         */
        public MultistackEntry(ValueWrapper value, MultistackEntry next) {
            this.value = value;
            this.next = next;
        }

        /**
         * Gets value.
         *
         * @return the value
         */
        public ValueWrapper getValue() {
            return value;
        }

        /**
         * Gets next.
         *
         * @return the next
         */
        public MultistackEntry getNext() {
            return next;
        }
    }

    /**
     * Push.
     *
     * @param keyName      the key name
     * @param valueWrapper the value wrapper
     */
    public void push(String keyName, ValueWrapper valueWrapper) {
        MultistackEntry entry = map.get(keyName);
        map.put(keyName, new MultistackEntry(valueWrapper, entry));
    }

    /**
     * Pop value wrapper.
     *
     * @param keyName the key name
     * @return the value wrapper
     */
    @SuppressWarnings("UnusedReturnValue")
    public ValueWrapper pop(String keyName) {
        if (this.isEmpty(keyName)) {
            throw new EmptyStackException();
        }

        MultistackEntry entry = map.get(keyName);
        ValueWrapper value = entry.getValue();
        map.put(keyName, entry.getNext());

        return value;
    }

    /**
     * Peek value wrapper.
     *
     * @param keyName the key name
     * @return the value wrapper
     */
    public ValueWrapper peek(String keyName) {
        if (this.isEmpty(keyName)) {
            throw new EmptyStackException();
        }

        return map.get(keyName).getValue();
    }

    /**
     * Is empty boolean.
     *
     * @param keyName the key name
     * @return the boolean
     */
    public boolean isEmpty(String keyName) {
        return map.get(keyName) == null;
    }
}
