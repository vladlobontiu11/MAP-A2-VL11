package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {
    private final List<T> list = new ArrayList<>();

    // Adds a value after the current last item.
    @Override
    public void add(T value) {
        list.add(value);
    }

    // Returns the item at an index and converts invalid indexes to ADT errors.
    @Override
    public T get(int index) throws ADTException {
        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException exception) {
            throw new ADTException("list index " + index + " is out of bounds");
        }
    }

    // Returns how many items the list contains.
    @Override
    public int size() {
        return list.size();
    }

    // Returns a separate copy of the list.
    @Override
    public List<T> toList() {
        return new ArrayList<>(list);
    }

    // Displays the items in their stored order.
    @Override
    public String toString() {
        return list.toString();
    }
}
