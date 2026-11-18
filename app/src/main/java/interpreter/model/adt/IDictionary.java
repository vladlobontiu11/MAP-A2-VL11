package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.Map;

public interface IDictionary<K, V> {
    // Checks whether the dictionary contains a key.
    boolean isDefined(K key);

    // Returns the value for a key, or reports a missing key.
    V lookup(K key) throws ADTException;

    // Adds a key and value to the dictionary.
    void put(K key, V value);

    // Changes the value for an existing key.
    void update(K key, V value) throws ADTException;

    // Removes an existing key and its value.
    void remove(K key) throws ADTException;

    // Returns a read-only copy of the dictionary.
    Map<K, V> toMap();
}
