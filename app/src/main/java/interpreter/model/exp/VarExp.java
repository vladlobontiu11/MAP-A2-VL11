package interpreter.model.exp;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public final class VarExp implements IExp {
    private final String variableName;

    public VarExp(String variableName) {
        if (variableName == null) {
            throw new NullPointerException("Variable name cannot be null");
        }
        this.variableName = variableName;
    }

    public String getId() {
        return variableName;
    }

    public String id() {
        return variableName;
    }

    // Looks up the variable's current value in the symbol table.
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws ExpressionException {
        try {
            return symTable.lookup(variableName);
        } catch (ADTException exception) {
            throw new ExpressionException("variable " + variableName + " is not defined");
        }
    }

    @Override
    public String toString() {
        return variableName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof VarExp)) {
            return false;
        }
        VarExp otherExpression = (VarExp) other;
        return variableName.equals(otherExpression.variableName);
    }

    @Override
    public int hashCode() {
        return variableName.hashCode();
    }
}
