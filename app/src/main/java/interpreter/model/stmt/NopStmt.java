package interpreter.model.stmt;

import interpreter.model.PrgState;

public final class NopStmt implements IStmt {
    // Does nothing and leaves the program state unchanged.
    @Override
    public PrgState execute(PrgState state) {
        return state;
    }

    @Override
    public String toString() {
        return "nop";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof NopStmt;
    }

    @Override
    public int hashCode() {
        return NopStmt.class.hashCode();
    }
}
