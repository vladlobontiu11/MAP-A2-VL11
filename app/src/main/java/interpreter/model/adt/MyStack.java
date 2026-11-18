package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MyStack<T> implements IStack<T> {
    private final Deque<T> stack = new ArrayDeque<>();

    // Adds the value so it will be the next one popped.
    @Override
    public void push(T value) {
        stack.push(value);
    }

    // Removes the top value and reports an error if the stack is empty.
    @Override
    public T pop() throws ADTException {
        if (stack.isEmpty()) {
            throw new ADTException("pop from an empty stack");
        }
        return stack.pop();
    }

    // Checks whether there are any values left to pop.
    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    // Makes a copy with the top value first.
    @Override
    public List<T> toList() {
        return new ArrayList<>(stack);
    }

    // Displays the stack from top to bottom.
    @Override
    public String toString() {
        return stack.toString();
    }
}
