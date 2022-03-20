package hr.fer.oprpp2.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloMessageTest {
    private HelloMessage m = null;

    @BeforeEach
    public void setUp() {
        this.m = new HelloMessage(2, "Foo Bar", 1234L);
    }

    @Test
    public void testGetter() {
        assertEquals(2, m.getIndex());
        assertEquals("Foo Bar", m.getName());
        assertEquals(1234L, m.getRandomKey());
    }

    @Test
    public void serializeDeserialize() {
        byte[] tmp = m.serialize();
        HelloMessage parsed = HelloMessage.deserialize(tmp);

        assertNotNull(parsed);
        assertEquals(m.getIndex(), parsed.getIndex());

        assertInstanceOf(HelloMessage.class, parsed);

        assertEquals(m.getName(), parsed.getName());
        assertEquals(m.getRandomKey(), parsed.getRandomKey());
    }

    @Test
    public void toStringTest() {
        assertEquals("[2]Hello: Foo Bar, 1234", m.toString());
    }
}
