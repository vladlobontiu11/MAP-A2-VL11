package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue condition = cond.eval(state.getSymTable());
        if (!(condition instanceof BoolValue boolValue)) {
            throw new StatementException("if condition is not a boolean");
        }
        state.getExeStack().push(boolValue.val() ? thenS : elseS);
        return state;
    }

    @Override
    public String toString() {
        return "(if " + cond + " then " + thenS + " else " + elseS + ")";
    }
}
