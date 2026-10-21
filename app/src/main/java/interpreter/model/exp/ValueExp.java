package interpreter.model.exp;

import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

import java.util.Objects;

public record ValueExp(IValue value) implements IExp {
    public ValueExp {
        Objects.requireNonNull(value);
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
