package interpreter.model.type;

import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public final class IntType implements IType {
    // Integer variables start with zero.
    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof IntType;
    }

    @Override
    public int hashCode() {
        return IntType.class.hashCode();
    }
}
