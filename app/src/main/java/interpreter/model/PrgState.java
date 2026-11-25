package interpreter.model;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.util.Map;
import java.util.TreeMap;

public class PrgState {
    private final IStack<IStmt> exeStack;
    private final IDictionary<String, IValue> symTable;
    private final IList<IValue> out;
    private final IDictionary<StringValue, BufferedReader> fileTable;
    private final IHeap heap;
    private final IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack,
                    IDictionary<String, IValue> symTable,
                    IList<IValue> out,
                    IDictionary<StringValue, BufferedReader> fileTable,
                    IHeap heap,
                    IStmt program) {
        if (exeStack == null || symTable == null || out == null
                || fileTable == null || heap == null || program == null) {
            throw new NullPointerException("Program state components cannot be null");
        }

        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = program;
        exeStack.push(program);
    }

    // Creates a state with empty stacks, tables, output, and heap.
    public static PrgState of(IStmt program) {
        return new PrgState(new MyStack<IStmt>(),
                new MyDictionary<String, IValue>(),
                new MyList<IValue>(),
                new MyDictionary<StringValue, BufferedReader>(),
                new MyHeap(),
                program);
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

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IHeap getHeap() {
        return heap;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    // Builds all five sections written to the execution log.
    public String toLogString() {
        StringBuilder text = new StringBuilder();

        text.append("ExeStack:\n");
        for (IStmt statement : exeStack.toList()) {
            text.append(statement).append('\n');
        }

        text.append("SymTable:\n");
        Map<String, IValue> variables = symTable.toMap();
        for (String name : variables.keySet()) {
            text.append(name).append(" --> ").append(variables.get(name)).append('\n');
        }

        text.append("Out:\n");
        for (IValue value : out.toList()) {
            text.append(value).append('\n');
        }

        text.append("FileTable:\n");
        for (StringValue fileName : fileTable.toMap().keySet()) {
            text.append(fileName.getVal()).append('\n');
        }

        text.append("Heap:\n");
        Map<Integer, IValue> heapContent = new TreeMap<>(heap.getContent());
        for (Integer address : heapContent.keySet()) {
            text.append(address).append(" --> ").append(heapContent.get(address)).append('\n');
        }
        return text.toString();
    }

    @Override
    public String toString() {
        return "Original program: " + originalProgram
                + "\nExecution stack: " + exeStack
                + "\nSymbol table: " + symTable
                + "\nOutput: " + out
                + "\nFile table: " + fileTable.toMap().keySet()
                + "\nHeap: " + heap;
    }
}
