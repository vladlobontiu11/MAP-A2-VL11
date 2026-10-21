package interpreter.view;

import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.AssignStmt;
import interpreter.model.stmt.CompStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.IfStmt;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;

import java.util.List;

public final class Examples {
    public record Example(String name, IStmt program) {
    }

    public static final IStmt EX1 = new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("v", number(2)),
                    new PrintStmt(new VarExp("v"))));

    public static final IStmt EX2 = new CompStmt(new VarDeclStmt("a", new IntType()),
            new CompStmt(new VarDeclStmt("b", new IntType()),
                    new CompStmt(new AssignStmt("a", new ArithExp(ArithOp.ADD, number(2),
                            new ArithExp(ArithOp.MUL, number(3), number(5)))),
                            new CompStmt(new AssignStmt("b",
                                    new ArithExp(ArithOp.ADD, new VarExp("a"), number(1))),
                                    new PrintStmt(new VarExp("b"))))));

    public static final IStmt EX3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
            new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                            new CompStmt(new IfStmt(new VarExp("a"),
                                    new AssignStmt("v", number(2)),
                                    new AssignStmt("v", number(3))),
                                    new PrintStmt(new VarExp("v"))))));

    public static final List<Example> ALL = List.of(
            new Example("Ex1: int v; v = 2; print(v)", EX1),
            new Example("Ex2: arithmetic expressions (prints 18)", EX2),
            new Example("Ex3: conditional (prints 2)", EX3)
    );

    private Examples() {
    }

    private static IExp number(int value) {
        return new ValueExp(new IntValue(value));
    }
}
