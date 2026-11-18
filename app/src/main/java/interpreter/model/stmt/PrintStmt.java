package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;

public final class PrintStmt implements IStmt {
    private final IExp exp;

    public PrintStmt(IExp exp) {
        if (exp == null) {
            throw new NullPointerException("Print expression cannot be null");
        }
        this.exp = exp;
    }

    public IExp getExp() {
        return exp;
    }

    public IExp exp() {
        return exp;
    }

    // Evaluates the expression and adds its value to the output list.
    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.getOut().add(exp.eval(state.getSymTable()));
        return state;
    }

    @Override
    public String toString() {
        return "print(" + exp + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PrintStmt)) {
            return false;
        }
        PrintStmt otherStatement = (PrintStmt) other;
        return exp.equals(otherStatement.exp);
    }

    @Override
    public int hashCode() {
        return exp.hashCode();
    }
}
