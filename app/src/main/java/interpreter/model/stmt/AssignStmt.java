package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.StatementException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;

public final class AssignStmt implements IStmt {
    private final String variableName;
    private final IExp expression;

    public AssignStmt(String variableName, IExp expression) {
        if (variableName == null || expression == null) {
            throw new NullPointerException("Variable name and expression cannot be null");
        }
        this.variableName = variableName;
        this.expression = expression;
    }

    public String getId() {
        return variableName;
    }

    public String id() {
        return variableName;
    }

    public IExp getExp() {
        return expression;
    }

    public IExp exp() {
        return expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymTable();
        if (!symbolTable.isDefined(variableName)) {
            throw new StatementException("variable " + variableName + " was not declared");
        }

        IValue value = expression.eval(symbolTable);
        IValue currentValue = symbolTable.lookup(variableName);
        if (!value.getType().equals(currentValue.getType())) {
            throw new StatementException("declared type of variable " + variableName
                    + " and type of the assigned expression do not match");
        }
        try {
            symbolTable.update(variableName, value);
        } catch (ADTException exception) {
            throw new StatementException(exception.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return variableName + " = " + expression;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AssignStmt)) {
            return false;
        }
        AssignStmt otherStatement = (AssignStmt) other;
        return variableName.equals(otherStatement.variableName)
                && expression.equals(otherStatement.expression);
    }

    @Override
    public int hashCode() {
        return 31 * variableName.hashCode() + expression.hashCode();
    }
}
