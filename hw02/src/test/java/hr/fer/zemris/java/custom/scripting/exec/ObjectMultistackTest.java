package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

class ObjectMultistackTest {
    private ObjectMultistack multistack;

    @BeforeEach
    void setUp() {
        this.multistack = new ObjectMultistack();
        this.multistack.push("year", new ValueWrapper(2000));
        this.multistack.push("price", new ValueWrapper(200.51));
    }

    @Test
    void simpleGet() {
        assertEquals(2000, this.multistack.peek("year").getValue());
        assertEquals(200.51, this.multistack.peek("price").getValue());
    }

    @Test
    void complexExample() {
        this.multistack.push("year", new ValueWrapper(1900));
        assertEquals(1900, multistack.peek("year").getValue());

        this.multistack.peek("year").setValue((Integer) multistack.peek("year").getValue() + 50);
        assertEquals(1950, multistack.peek("year").getValue());

        this.multistack.pop("year");
        assertEquals(2000, multistack.peek("year").getValue());

        this.multistack.peek("year").add("5");
        assertEquals(2005, multistack.peek("year").getValue());

        System.out.printf("Before 5 -> %s\n", this.multistack.peek("year").getValue().getClass());
        this.multistack.peek("year").add(5);
        assertEquals(2010, multistack.peek("year").getValue());

        this.multistack.peek("year").add(5.0);
        assertEquals(2015.0, multistack.peek("year").getValue());
    }

    @Test
    void throwForEmptyStack() {
        assertThrows(EmptyStackException.class, () -> multistack.pop("foobar"));
    }
}
