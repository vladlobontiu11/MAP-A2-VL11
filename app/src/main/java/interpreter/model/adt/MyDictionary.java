package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> dictionary = new LinkedHashMap<>();

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException {
        if (!dictionary.containsKey(key)) {
            throw new ADTException("key " + key + " is not defined");
        }
        return dictionary.get(key);
    }

    @Override
    public void put(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if (!dictionary.containsKey(key)) {
            throw new ADTException("key " + key + " is not defined");
        }
        dictionary.put(key, value);
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }
}
