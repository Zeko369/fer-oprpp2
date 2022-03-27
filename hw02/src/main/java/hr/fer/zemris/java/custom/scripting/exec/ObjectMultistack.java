package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

public class ObjectMultistack {
    private final Map<String, MultistackEntry> map = new HashMap<>();

    private static class MultistackEntry {
        ValueWrapper value;
        MultistackEntry next;

        public MultistackEntry(ValueWrapper value, MultistackEntry next) {
            this.value = value;
            this.next = next;
        }

        public ValueWrapper getValue() {
            return value;
        }

        public MultistackEntry getNext() {
            return next;
        }
    }

    public void push(String keyName, ValueWrapper valueWrapper) {
        MultistackEntry entry = map.get(keyName);
        map.put(keyName, new MultistackEntry(valueWrapper, entry));
    }

    public ValueWrapper pop(String keyName) {
        if (this.isEmpty(keyName)) {
            throw new EmptyStackException();
        }

        MultistackEntry entry = map.get(keyName);
        ValueWrapper value = entry.getValue();
        map.put(keyName, entry.getNext());

        return value;
    }

    public ValueWrapper peek(String keyName) {
        if (this.isEmpty(keyName)) {
            throw new EmptyStackException();
        }

        return map.get(keyName).getValue();
    }

    public boolean isEmpty(String keyName) {
        return map.get(keyName) == null;
    }
}
