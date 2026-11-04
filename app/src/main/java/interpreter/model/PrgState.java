package interpreter.model;

import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.*;
import java.io.BufferedReader;

public class PrgState {
    private final IStack<IStmt> exeStack;
    private final IDictionary<String,IValue> symTable;
    private final IList<IValue> out;
    private final IDictionary<StringValue,BufferedReader> fileTable;
    private final IStmt originalProgram;
    public PrgState(IStack<IStmt> exeStack, IDictionary<String,IValue> symTable, IList<IValue> out, IStmt program) {
        this(exeStack, symTable, out, new MyDictionary<>(), program);
    }
    public PrgState(IStack<IStmt> exeStack, IDictionary<String,IValue> symTable, IList<IValue> out,
                    IDictionary<StringValue,BufferedReader> fileTable, IStmt program) {
        if (exeStack==null||symTable==null||out==null||fileTable==null||program==null) throw new NullPointerException("Program state components cannot be null");
        this.exeStack=exeStack; this.symTable=symTable; this.out=out; this.fileTable=fileTable; this.originalProgram=program; exeStack.push(program);
    }
    public static PrgState of(IStmt program) { return new PrgState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),new MyDictionary<>(),program); }
    public IStack<IStmt> getExeStack(){return exeStack;} public IDictionary<String,IValue> getSymTable(){return symTable;}
    public IList<IValue> getOut(){return out;} public IDictionary<StringValue,BufferedReader> getFileTable(){return fileTable;}
    public IStmt getOriginalProgram(){return originalProgram;}
    public String toLogString() {
        StringBuilder sb=new StringBuilder("ExeStack:\n"); exeStack.toList().forEach(s->sb.append(s).append('\n'));
        sb.append("SymTable:\n"); symTable.toMap().forEach((k,v)->sb.append(k).append(" --> ").append(v).append('\n'));
        sb.append("Out:\n"); out.toList().forEach(v->sb.append(v).append('\n'));
        sb.append("FileTable:\n"); fileTable.toMap().keySet().forEach(f->sb.append(f.val()).append('\n')); return sb.toString();
    }
    @Override public String toString(){return "Original program: "+originalProgram+"\nExecution stack: "+exeStack+"\nSymbol table: "+symTable+"\nOutput: "+out+"\nFile table: "+fileTable.toMap().keySet();}
}
