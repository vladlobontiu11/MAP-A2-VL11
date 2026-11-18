package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.List;

public interface IStack<T> {
    // Adds a value to the top of the stack.
    void push(T value);

    // Removes and returns the top value, or reports an empty stack.
    T pop() throws ADTException;

    // Tells whether the stack has no values.
    boolean isEmpty();

    // Returns a copy of the stack, starting with its top value.
    List<T> toList();
}
