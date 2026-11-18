package interpreter.model.value;

import interpreter.model.type.IType;
import interpreter.model.type.IntType;

public final class IntValue implements IValue {
    private final int val;

    public IntValue(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public int val() {
        return val;
    }

    // Returns the language type of this value.
    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }

    // Compares integer values by their stored number.
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IntValue)) {
            return false;
        }
        IntValue otherValue = (IntValue) other;
        return val == otherValue.val;
    }

    // Gives equal integer values the same hash code.
    @Override
    public int hashCode() {
        return Integer.hashCode(val);
    }
}
