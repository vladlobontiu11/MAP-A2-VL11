package interpreter.model.value;

import interpreter.model.type.IType;

public interface IValue {
    // Returns the type used to check assignments.
    IType getType();
}
