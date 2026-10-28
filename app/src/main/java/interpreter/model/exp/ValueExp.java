package interpreter.model.exp;

import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public final class ValueExp implements IExp {
    private final IValue value;

    public ValueExp(IValue value) {
        if (value == null) {
            throw new NullPointerException("Expression value cannot be null");
        }
        this.value = value;
    }

    public IValue getValue() {
        return value;
    }

    public IValue value() {
        return value;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ValueExp)) {
            return false;
        }
        ValueExp otherExpression = (ValueExp) other;
        return value.equals(otherExpression.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
