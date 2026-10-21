package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.StatementException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;

public record AssignStmt(String id, IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, IValue> symTable = state.getSymTable();
        if (!symTable.isDefined(id)) {
            throw new StatementException("variable " + id + " was not declared");
        }

        IValue value = exp.eval(symTable);
        IValue currentValue = symTable.lookup(id);
        if (!value.getType().equals(currentValue.getType())) {
            throw new StatementException("declared type of variable " + id
                    + " and type of the assigned expression do not match");
        }
        try {
            symTable.update(id, value);
        } catch (ADTException exception) {
            throw new StatementException(exception.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return id + " = " + exp;
    }
}
