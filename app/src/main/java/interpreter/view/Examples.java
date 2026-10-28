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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Examples {
    public static final class Example {
        private final String name;
        private final IStmt program;

        public Example(String name, IStmt program) {
            if (name == null || program == null) {
                throw new NullPointerException("Example name and program cannot be null");
            }
            this.name = name;
            this.program = program;
        }

        public String getName() {
            return name;
        }

        public String name() {
            return name;
        }

        public IStmt getProgram() {
            return program;
        }

        public IStmt program() {
            return program;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Example)) {
                return false;
            }
            Example otherExample = (Example) other;
            return name.equals(otherExample.name) && program.equals(otherExample.program);
        }

        @Override
        public int hashCode() {
            return 31 * name.hashCode() + program.hashCode();
        }

        @Override
        public String toString() {
            return "Example[name=" + name + ", program=" + program + "]";
        }
    }

    public static final IStmt EX1 = createExample1();
    public static final IStmt EX2 = createExample2();
    public static final IStmt EX3 = createExample3();

    public static final List<Example> ALL = createExamples();

    private Examples() {
    }

    private static IStmt createExample1() {
        IStmt declareVariable = new VarDeclStmt("v", new IntType());
        IStmt assignValue = new AssignStmt("v", number(2));
        IStmt printValue = new PrintStmt(new VarExp("v"));

        return new CompStmt(declareVariable,
                new CompStmt(assignValue, printValue));
    }

    private static IStmt createExample2() {
        IExp multiplication = new ArithExp(ArithOp.MUL, number(3), number(5));
        IExp addition = new ArithExp(ArithOp.ADD, number(2), multiplication);
        IExp increment = new ArithExp(ArithOp.ADD, new VarExp("a"), number(1));

        IStmt declareA = new VarDeclStmt("a", new IntType());
        IStmt declareB = new VarDeclStmt("b", new IntType());
        IStmt assignA = new AssignStmt("a", addition);
        IStmt assignB = new AssignStmt("b", increment);
        IStmt printB = new PrintStmt(new VarExp("b"));

        return new CompStmt(declareA,
                new CompStmt(declareB,
                        new CompStmt(assignA,
                                new CompStmt(assignB, printB))));
    }

    private static IStmt createExample3() {
        IStmt declareA = new VarDeclStmt("a", new BoolType());
        IStmt declareV = new VarDeclStmt("v", new IntType());
        IStmt setA = new AssignStmt("a", new ValueExp(new BoolValue(true)));
        IStmt setVToTwo = new AssignStmt("v", number(2));
        IStmt setVToThree = new AssignStmt("v", number(3));
        IStmt chooseV = new IfStmt(new VarExp("a"), setVToTwo, setVToThree);
        IStmt printV = new PrintStmt(new VarExp("v"));

        return new CompStmt(declareA,
                new CompStmt(declareV,
                        new CompStmt(setA,
                                new CompStmt(chooseV, printV))));
    }

    private static List<Example> createExamples() {
        List<Example> examples = new ArrayList<>();
        examples.add(new Example("Ex1: int v; v = 2; print(v)", EX1));
        examples.add(new Example("Ex2: arithmetic expressions (prints 18)", EX2));
        examples.add(new Example("Ex3: conditional (prints 2)", EX3));
        return Collections.unmodifiableList(examples);
    }

    private static IExp number(int value) {
        return new ValueExp(new IntValue(value));
    }
}
