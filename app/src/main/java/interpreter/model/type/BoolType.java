package interpreter.model.type;

import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public final class BoolType implements IType {
    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BoolType;
    }

    @Override
    public int hashCode() {
        return BoolType.class.hashCode();
    }
}
