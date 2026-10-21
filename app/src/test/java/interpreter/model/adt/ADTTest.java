package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ADTTest {
    @Test
    void stackUsesLifoOrderAndRejectsPopWhenEmpty() throws ADTException {
        IStack<Integer> stack = new MyStack<>();
        stack.push(1);
        stack.push(2);

        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
        assertThrows(ADTException.class, stack::pop);
    }

    @Test
    void dictionarySupportsPutLookupAndUpdate() throws ADTException {
        IDictionary<String, Integer> dictionary = new MyDictionary<>();
        dictionary.put("x", 1);

        assertTrue(dictionary.isDefined("x"));
        assertEquals(1, dictionary.lookup("x"));
        dictionary.update("x", 2);
        assertEquals(2, dictionary.lookup("x"));
    }

    @Test
    void dictionaryRejectsLookupAndUpdateOfMissingKeys() {
        IDictionary<String, Integer> dictionary = new MyDictionary<>();

        assertFalse(dictionary.isDefined("missing"));
        assertThrows(ADTException.class, () -> dictionary.lookup("missing"));
        assertThrows(ADTException.class, () -> dictionary.update("missing", 1));
    }

    @Test
    void listSupportsAddGetAndSize() throws ADTException {
        IList<String> list = new MyList<>();
        list.add("first");
        list.add("second");

        assertEquals(2, list.size());
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
    }

    @Test
    void listRejectsAnOutOfBoundsIndex() {
        IList<String> list = new MyList<>();

        assertThrows(ADTException.class, () -> list.get(0));
    }
}
