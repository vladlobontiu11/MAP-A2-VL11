package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> dictionary = new LinkedHashMap<>();

    // Checks whether the given key exists.
    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    // Finds a key's value and reports an error if the key is missing.
    @Override
    public V lookup(K key) throws ADTException {
        if (!dictionary.containsKey(key)) {
            throw new ADTException("key " + key + " is not defined");
        }
        return dictionary.get(key);
    }

    // Adds a new key and its value.
    @Override
    public void put(K key, V value) {
        dictionary.put(key, value);
    }

    // Replaces the value for a key that already exists.
    @Override
    public void update(K key, V value) throws ADTException {
        if (!dictionary.containsKey(key)) {
            throw new ADTException("key " + key + " is not defined");
        }
        dictionary.put(key, value);
    }

    // Removes a key and reports an error if it does not exist.
    @Override
    public void remove(K key) throws ADTException {
        if (!dictionary.containsKey(key)) {
            throw new ADTException("key " + key + " is not defined");
        }
        dictionary.remove(key);
    }

    // Returns a read-only copy so callers cannot change this dictionary.
    @Override
    public Map<K, V> toMap() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(dictionary));
    }

    // Displays all dictionary entries.
    @Override
    public String toString() {
        return dictionary.toString();
    }
}
