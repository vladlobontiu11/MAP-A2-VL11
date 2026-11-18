package interpreter.view;

import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.IExp;
import interpreter.model.exp.RelOp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.AssignStmt;
import interpreter.model.stmt.CloseRFileStmt;
import interpreter.model.stmt.CompStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.IfStmt;
import interpreter.model.stmt.OpenRFileStmt;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.stmt.ReadFileStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

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

        public IStmt getProgram() {
            return program;
        }
    }

    public static final IStmt EX1 = createExample1();
    public static final IStmt EX2 = createExample2();
    public static final IStmt EX3 = createExample3();
    public static final IStmt EX4 = fileExample("test.in");
    public static final IStmt EX5 = createExample5();
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

    // Reads two numbers from a file, prints them, then closes the file.
    public static IStmt fileExample(String fileName) {
        IStmt declareFileName = new VarDeclStmt("varf", new StringType());
        IStmt setFileName = new AssignStmt("varf",
                new ValueExp(new StringValue(fileName)));
        IStmt openFile = new OpenRFileStmt(new VarExp("varf"));
        IStmt declareNumber = new VarDeclStmt("varc", new IntType());
        IStmt readFirstNumber = new ReadFileStmt(new VarExp("varf"), "varc");
        IStmt printFirstNumber = new PrintStmt(new VarExp("varc"));
        IStmt readSecondNumber = new ReadFileStmt(new VarExp("varf"), "varc");
        IStmt printSecondNumber = new PrintStmt(new VarExp("varc"));
        IStmt closeFile = new CloseRFileStmt(new VarExp("varf"));

        return new CompStmt(declareFileName,
                new CompStmt(setFileName,
                        new CompStmt(openFile,
                                new CompStmt(declareNumber,
                                        new CompStmt(readFirstNumber,
                                                new CompStmt(printFirstNumber,
                                                        new CompStmt(readSecondNumber,
                                                                new CompStmt(printSecondNumber,
                                                                        closeFile))))))));
    }

    private static IStmt createExample5() {
        IStmt declareA = new VarDeclStmt("a", new IntType());
        IStmt declareB = new VarDeclStmt("b", new IntType());
        IStmt setA = new AssignStmt("a", number(5));
        IStmt setB = new AssignStmt("b", number(7));
        IExp compareAAndB = new RelationalExp(
                RelOp.LT, new VarExp("a"), new VarExp("b"));
        IStmt printA = new PrintStmt(new VarExp("a"));
        IStmt printB = new PrintStmt(new VarExp("b"));
        IStmt chooseNumberToPrint = new IfStmt(compareAAndB, printA, printB);

        return new CompStmt(declareA,
                new CompStmt(declareB,
                        new CompStmt(setA,
                                new CompStmt(setB, chooseNumberToPrint))));
    }

    private static List<Example> createExamples() {
        List<Example> examples = new ArrayList<>();
        examples.add(new Example("Ex1: print v after assigning 2 (result: 2)", EX1));
        examples.add(new Example("Ex2: arithmetic expressions (result: 18)", EX2));
        examples.add(new Example("Ex3: conditional statement (result: 2)", EX3));
        examples.add(new Example(
                "Ex4: read test.in (result: 15, 50; empty file: 0, 0)", EX4));
        examples.add(new Example("Ex5: relational expression (result: 5)", EX5));
        return Collections.unmodifiableList(examples);
    }

    private static IExp number(int value) {
        return new ValueExp(new IntValue(value));
    }
}
