package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.List;

public interface IList<T> {
    // Adds a value to the end of the list.
    void add(T value);

    // Returns the value at an index, or reports an invalid index.
    T get(int index) throws ADTException;

    // Returns the number of values in the list.
    int size();

    // Returns a copy of the list in its current order.
    List<T> toList();
}
