package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {
    private final List<T> list = new ArrayList<>();

    @Override
    public void add(T value) {
        list.add(value);
    }

    @Override
    public T get(int index) throws ADTException {
        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException exception) {
            throw new ADTException("list index " + index + " is out of bounds");
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
