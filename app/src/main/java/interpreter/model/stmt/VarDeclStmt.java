package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.type.IType;

public final class VarDeclStmt implements IStmt {
    private final String name;
    private final IType type;

    public VarDeclStmt(String name, IType type) {
        if (name == null || type == null) {
            throw new NullPointerException("Variable name and type cannot be null");
        }
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String name() {
        return name;
    }

    public IType getType() {
        return type;
    }

    public IType type() {
        return type;
    }

    // Adds the variable with the default value for its type.
    @Override
    public PrgState execute(PrgState state) throws StatementException {
        if (state.getSymTable().isDefined(name)) {
            throw new StatementException("variable " + name + " is already declared");
        }
        state.getSymTable().put(name, type.defaultValue());
        return state;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof VarDeclStmt)) {
            return false;
        }
        VarDeclStmt otherStatement = (VarDeclStmt) other;
        return name.equals(otherStatement.name) && type.equals(otherStatement.type);
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + type.hashCode();
    }
}
