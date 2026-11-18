package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ADTTest {
    // Checks that the stack is LIFO and throws when empty.
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

    // Checks adding, finding, and changing a dictionary value.
    @Test
    void dictionarySupportsPutLookupAndUpdate() throws ADTException {
        IDictionary<String, Integer> dictionary = new MyDictionary<>();
        dictionary.put("x", 1);

        assertTrue(dictionary.isDefined("x"));
        assertEquals(1, dictionary.lookup("x"));
        dictionary.update("x", 2);
        assertEquals(2, dictionary.lookup("x"));
    }

    // Checks that a missing dictionary key causes an error.
    @Test
    void dictionaryRejectsLookupAndUpdateOfMissingKeys() {
        IDictionary<String, Integer> dictionary = new MyDictionary<>();

        assertFalse(dictionary.isDefined("missing"));
        assertThrows(ADTException.class, () -> dictionary.lookup("missing"));
        assertThrows(ADTException.class, () -> dictionary.update("missing", 1));
    }

    // Checks adding items and reading them by index.
    @Test
    void listSupportsAddGetAndSize() throws ADTException {
        IList<String> list = new MyList<>();
        list.add("first");
        list.add("second");

        assertEquals(2, list.size());
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
    }

    // Checks that an invalid list index causes an error.
    @Test
    void listRejectsAnOutOfBoundsIndex() {
        IList<String> list = new MyList<>();

        assertThrows(ADTException.class, () -> list.get(0));
    }

    // Checks that removing a dictionary key works and missing keys fail.
    @Test
    void dictionaryRemovesKeysAndReturnsASnapshot() throws ADTException {
        IDictionary<String, Integer> dictionary = new MyDictionary<>();
        dictionary.put("x", 1);

        java.util.Map<String, Integer> copy = dictionary.toMap();
        dictionary.update("x", 2);

        assertEquals(1, copy.get("x"));
        assertEquals(2, dictionary.lookup("x"));
        assertThrows(UnsupportedOperationException.class, () -> copy.put("y", 3));

        dictionary.remove("x");
        assertFalse(dictionary.isDefined("x"));
        assertThrows(ADTException.class, () -> dictionary.remove("x"));
    }

    // Checks that stack and list copies have the expected order.
    @Test
    void stackAndListReturnCopiesInTheirExpectedOrder() throws ADTException {
        IStack<Integer> stack = new MyStack<>();
        stack.push(1);
        stack.push(2);

        IList<Integer> list = new MyList<>();
        list.add(1);
        list.add(2);

        assertEquals(java.util.List.of(2, 1), stack.toList());
        assertEquals(java.util.List.of(1, 2), list.toList());
    }
}
