package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public final class IfStmt implements IStmt {
    private final IExp conditionExpression;
    private final IStmt thenStatement;
    private final IStmt elseStatement;

    public IfStmt(IExp conditionExpression, IStmt thenStatement, IStmt elseStatement) {
        if (conditionExpression == null || thenStatement == null || elseStatement == null) {
            throw new NullPointerException("Condition and branch statements cannot be null");
        }
        this.conditionExpression = conditionExpression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    public IExp getCond() {
        return conditionExpression;
    }

    public IExp cond() {
        return conditionExpression;
    }

    public IStmt getThenS() {
        return thenStatement;
    }

    public IStmt thenS() {
        return thenStatement;
    }

    public IStmt getElseS() {
        return elseStatement;
    }

    public IStmt elseS() {
        return elseStatement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue conditionValue = conditionExpression.eval(state.getSymTable());
        if (!(conditionValue instanceof BoolValue)) {
            throw new StatementException("if condition is not a boolean");
        }
        BoolValue booleanCondition = (BoolValue) conditionValue;
        if (booleanCondition.getVal()) {
            state.getExeStack().push(thenStatement);
        } else {
            state.getExeStack().push(elseStatement);
        }
        return state;
    }

    @Override
    public String toString() {
        return "(if " + conditionExpression + " then " + thenStatement + " else " + elseStatement + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IfStmt)) {
            return false;
        }
        IfStmt otherStatement = (IfStmt) other;
        return conditionExpression.equals(otherStatement.conditionExpression)
                && thenStatement.equals(otherStatement.thenStatement)
                && elseStatement.equals(otherStatement.elseStatement);
    }

    @Override
    public int hashCode() {
        int result = conditionExpression.hashCode();
        result = 31 * result + thenStatement.hashCode();
        result = 31 * result + elseStatement.hashCode();
        return result;
    }
}
