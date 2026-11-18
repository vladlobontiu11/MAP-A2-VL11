package interpreter.model.stmt;

import interpreter.model.PrgState;

public final class CompStmt implements IStmt {
    private final IStmt first;
    private final IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        if (first == null || second == null) {
            throw new NullPointerException("Statements cannot be null");
        }
        this.first = first;
        this.second = second;
    }

    public IStmt getFirst() {
        return first;
    }

    public IStmt first() {
        return first;
    }

    public IStmt getSecond() {
        return second;
    }

    public IStmt second() {
        return second;
    }

    // Pushes both statements so the first one runs before the second.
    @Override
    public PrgState execute(PrgState state) {
        // The stack runs its top first, so push the second statement first.
        state.getExeStack().push(second);
        state.getExeStack().push(first);
        return state;
    }

    @Override
    public String toString() {
        return "(" + first + "; " + second + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CompStmt)) {
            return false;
        }
        CompStmt otherStatement = (CompStmt) other;
        return first.equals(otherStatement.first) && second.equals(otherStatement.second);
    }

    @Override
    public int hashCode() {
        return 31 * first.hashCode() + second.hashCode();
    }
}
