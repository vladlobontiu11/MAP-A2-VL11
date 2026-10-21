package interpreter.model;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;

import java.util.Objects;

public class PrgState {
    private final IStack<IStmt> exeStack;
    private final IDictionary<String, IValue> symTable;
    private final IList<IValue> out;
    private final IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable,
                    IList<IValue> out, IStmt program) {
        this.exeStack = Objects.requireNonNull(exeStack);
        this.symTable = Objects.requireNonNull(symTable);
        this.out = Objects.requireNonNull(out);
        this.originalProgram = Objects.requireNonNull(program);
        exeStack.push(program);
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    @Override
    public String toString() {
        return "Original program: " + originalProgram
                + "\nExecution stack: " + exeStack
                + "\nSymbol table: " + symTable
                + "\nOutput: " + out;
    }
}
