package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueWrapperTest {
    @Test
    void getValue() {
        ValueWrapper v1 = new ValueWrapper(null);
        assertNull(v1.getValue());

        ValueWrapper v2 = new ValueWrapper(5);
        assertEquals(5, v2.getValue());
    }

    @Test
    void setValue() {
        ValueWrapper v1 = new ValueWrapper(null);
        v1.setValue(5);
        assertEquals(5, v1.getValue());

        v1.setValue(null);
        assertNull(v1.getValue());
    }

    @Test
    void throwsForNonCorrectType() {
        ValueWrapper v1 = new ValueWrapper("10");
        ValueWrapper v2 = new ValueWrapper(true);

        assertThrows(RuntimeException.class, () -> v1.add(v2));
    }

    @Test
    void add() {
        ValueWrapper v1 = new ValueWrapper(null);
        ValueWrapper v2 = new ValueWrapper(null);
        v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.

        assertEquals(0, v1.getValue());
        assertEquals(Integer.class, v1.getValue().getClass());
        assertNull(v2.getValue());

        ValueWrapper v3 = new ValueWrapper("1.2E1");
        ValueWrapper v4 = new ValueWrapper(1);
        v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).

        assertEquals(13.0, v3.getValue());
        assertEquals(Double.class, v3.getValue().getClass());
        assertEquals(1, v4.getValue());
        assertEquals(Integer.class, v4.getValue().getClass());

        ValueWrapper v5 = new ValueWrapper("12");
        ValueWrapper v6 = new ValueWrapper(1);
        v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).

        assertEquals(13, v5.getValue());
        assertEquals(Integer.class, v5.getValue().getClass());
        assertEquals(1, v6.getValue());
        assertEquals(Integer.class, v6.getValue().getClass());

        ValueWrapper v7 = new ValueWrapper("Ankica");
        ValueWrapper v8 = new ValueWrapper(1);
        assertThrows(RuntimeException.class, () -> v7.add(v8.getValue()));
    }

    @Test
    void subtract() {
        ValueWrapper v1 = new ValueWrapper(null);
        ValueWrapper v2 = new ValueWrapper(null);
        v1.subtract(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.

        assertEquals(0, v1.getValue());
        assertEquals(Integer.class, v1.getValue().getClass());
        assertNull(v2.getValue());

        ValueWrapper v3 = new ValueWrapper("1.2E1");
        ValueWrapper v4 = new ValueWrapper(1);
        v3.subtract(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).

        assertEquals(11.0, v3.getValue());
        assertEquals(Double.class, v3.getValue().getClass());
        assertEquals(1, v4.getValue());
        assertEquals(Integer.class, v4.getValue().getClass());

        ValueWrapper v5 = new ValueWrapper("12");
        ValueWrapper v6 = new ValueWrapper(1);
        v5.subtract(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).

        assertEquals(11, v5.getValue());
        assertEquals(Integer.class, v5.getValue().getClass());
        assertEquals(1, v6.getValue());
        assertEquals(Integer.class, v6.getValue().getClass());

        ValueWrapper v7 = new ValueWrapper("Ankica");
        ValueWrapper v8 = new ValueWrapper(1);
        assertThrows(RuntimeException.class, () -> v7.subtract(v8.getValue()));
    }

    @Test
    void multiply() {
        ValueWrapper v1 = new ValueWrapper(null);
        ValueWrapper v2 = new ValueWrapper(null);
        v1.multiply(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.

        assertEquals(0, v1.getValue());
        assertEquals(Integer.class, v1.getValue().getClass());
        assertNull(v2.getValue());

        ValueWrapper v3 = new ValueWrapper("1.2E1");
        ValueWrapper v4 = new ValueWrapper(1);
        v3.multiply(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).

        assertEquals(12.0, v3.getValue());
        assertEquals(Double.class, v3.getValue().getClass());
        assertEquals(1, v4.getValue());
        assertEquals(Integer.class, v4.getValue().getClass());

        ValueWrapper v5 = new ValueWrapper("12");
        ValueWrapper v6 = new ValueWrapper(1);
        v5.multiply(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).

        assertEquals(12, v5.getValue());
        assertEquals(Integer.class, v5.getValue().getClass());
        assertEquals(1, v6.getValue());
        assertEquals(Integer.class, v6.getValue().getClass());

        ValueWrapper v7 = new ValueWrapper("Ankica");
        ValueWrapper v8 = new ValueWrapper(1);
        assertThrows(RuntimeException.class, () -> v7.multiply(v8.getValue()));
    }

    @Test
    void divide() {
        ValueWrapper v1 = new ValueWrapper(null);
        ValueWrapper v2 = new ValueWrapper(null);
        assertThrows(ArithmeticException.class, () -> v1.divide(v2.getValue())); // v1 now stores Integer(0); v2 still stores null.

        assertNull(v1.getValue());
        assertNull(v2.getValue());

        ValueWrapper v3 = new ValueWrapper("1.2E1");
        ValueWrapper v4 = new ValueWrapper(1);
        v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).

        assertEquals(13.0, v3.getValue());
        assertEquals(Double.class, v3.getValue().getClass());
        assertEquals(1, v4.getValue());
        assertEquals(Integer.class, v4.getValue().getClass());

        ValueWrapper v5 = new ValueWrapper("12");
        ValueWrapper v6 = new ValueWrapper(1);
        v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).

        assertEquals(13, v5.getValue());
        assertEquals(Integer.class, v5.getValue().getClass());
        assertEquals(1, v6.getValue());
        assertEquals(Integer.class, v6.getValue().getClass());

        ValueWrapper v7 = new ValueWrapper("Ankica");
        ValueWrapper v8 = new ValueWrapper(1);
        assertThrows(RuntimeException.class, () -> v7.add(v8.getValue()));
    }

    @Test
    void numCompare() {
    }
}
