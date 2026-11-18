package interpreter.model.type;

import interpreter.model.value.IValue;

public interface IType {
    // Creates the initial value used when a variable of this type is declared.
    IValue defaultValue();
}
