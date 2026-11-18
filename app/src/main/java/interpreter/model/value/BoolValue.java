package interpreter.model.value;

import interpreter.model.type.BoolType;
import interpreter.model.type.IType;

public final class BoolValue implements IValue {
    private final boolean val;

    public BoolValue(boolean val) {
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }

    public boolean val() {
        return val;
    }

    // Returns the language type of this value.
    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return Boolean.toString(val);
    }

    // Compares boolean values by their stored result.
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BoolValue)) {
            return false;
        }
        BoolValue otherValue = (BoolValue) other;
        return val == otherValue.val;
    }

    // Gives equal boolean values the same hash code.
    @Override
    public int hashCode() {
        return Boolean.hashCode(val);
    }
}
