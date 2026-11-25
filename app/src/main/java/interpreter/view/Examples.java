package interpreter.view;

import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.IExp;
import interpreter.model.exp.RelOp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.ReadHeapExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.AssignStmt;
import interpreter.model.stmt.CloseRFileStmt;
import interpreter.model.stmt.CompStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.IfStmt;
import interpreter.model.stmt.NewStmt;
import interpreter.model.stmt.OpenRFileStmt;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.stmt.ReadFileStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.stmt.WhileStmt;
import interpreter.model.stmt.WriteHeapStmt;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
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
    public static final IStmt EX6 = createExample6();
    public static final IStmt EX7 = createExample7();
    public static final IStmt EX8 = createExample8();
    public static final IStmt EX9 = createExample9();
    public static final IStmt EX10 = createExample10();
    public static final List<Example> ALL = createExamples();

    private Examples() {
    }

    // Builds a program that assigns and prints the number 2.
    private static IStmt createExample1() {
        IStmt declareVariable = new VarDeclStmt("v", new IntType());
        IStmt assignValue = new AssignStmt("v", number(2));
        IStmt printValue = new PrintStmt(new VarExp("v"));

        return new CompStmt(declareVariable,
                new CompStmt(assignValue, printValue));
    }

    // Builds a program that evaluates an arithmetic expression.
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

    // Builds a program that uses a boolean condition to choose a value.
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

    // Builds a program that compares two integers and prints the smaller one.
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

    // Builds a program that allocates and prints two reference values.
    private static IStmt createExample6() {
        IStmt declareV = new VarDeclStmt("v", new RefType(new IntType()));
        IStmt allocateV = new NewStmt("v", number(20));
        IStmt declareA = new VarDeclStmt("a",
                                new RefType(new RefType(new IntType())));
        IStmt allocateA = new NewStmt("a", new VarExp("v"));
        IStmt printV = new PrintStmt(new VarExp("v"));
        IStmt printA = new PrintStmt(new VarExp("a"));

        return new CompStmt(declareV,
                                new CompStmt(allocateV,
                                        new CompStmt(declareA,
                                                new CompStmt(allocateA, new CompStmt(printV, printA)))));
    }

    // Builds a program that reads through nested references in the heap.
    private static IStmt createExample7() {
        IStmt declareV = new VarDeclStmt("v", new RefType(new IntType()));
        IStmt allocateV = new NewStmt("v", number(20));
        IStmt declareA = new VarDeclStmt("a",
                                new RefType(new RefType(new IntType())));
        IStmt allocateA = new NewStmt("a", new VarExp("v"));
        IStmt printValue = new PrintStmt(new ReadHeapExp(new VarExp("v")));
        IExp nestedValue = new ReadHeapExp(new ReadHeapExp(new VarExp("a")));
        IExp addFive = new ArithExp(ArithOp.ADD, nestedValue, number(5));
        IStmt printNestedValue = new PrintStmt(addFive);

        return new CompStmt(declareV,
                                new CompStmt(allocateV,
                                        new CompStmt(declareA,
                                                new CompStmt(allocateA,
                                                        new CompStmt(printValue, printNestedValue)))));
    }

    // Builds a program that changes a heap value and reads the new value.
    private static IStmt createExample8() {
        IStmt declareV = new VarDeclStmt("v", new RefType(new IntType()));
        IStmt allocateV = new NewStmt("v", number(20));
        IStmt printOriginal = new PrintStmt(new ReadHeapExp(new VarExp("v")));
        IStmt writeNewValue = new WriteHeapStmt("v", number(30));
        IExp addFive = new ArithExp(ArithOp.ADD,
                                new ReadHeapExp(new VarExp("v")), number(5));
        IStmt printUpdatedValue = new PrintStmt(addFive);

        return new CompStmt(declareV,
                                new CompStmt(allocateV,
                                        new CompStmt(printOriginal,
                                                new CompStmt(writeNewValue, printUpdatedValue))));
    }

    // Builds a program that tests whether garbage collection keeps nested references.
    private static IStmt createExample9() {
        IStmt declareV = new VarDeclStmt("v", new RefType(new IntType()));
        IStmt allocateV = new NewStmt("v", number(20));
        IStmt declareA = new VarDeclStmt("a",
                                new RefType(new RefType(new IntType())));
        IStmt allocateA = new NewStmt("a", new VarExp("v"));
        IStmt allocateNewV = new NewStmt("v", number(30));
        IStmt printOriginalValue = new PrintStmt(
                                new ReadHeapExp(new ReadHeapExp(new VarExp("a"))));

        return new CompStmt(declareV,
                                new CompStmt(allocateV,
                                        new CompStmt(declareA,
                                                new CompStmt(allocateA,
                                                        new CompStmt(allocateNewV, printOriginalValue)))));
    }

    // Builds a loop that prints 4 down to 1, then prints zero.
    private static IStmt createExample10() {
        IStmt declareV = new VarDeclStmt("v", new IntType());
        IStmt setV = new AssignStmt("v", number(4));
        IExp condition = new RelationalExp(
                                RelOp.GT, new VarExp("v"), number(0));
        IStmt decreaseAndPrint = new CompStmt(
                                new PrintStmt(new VarExp("v")),
                                new AssignStmt("v",
                                        new ArithExp(ArithOp.SUB, new VarExp("v"), number(1))));
        IStmt loop = new WhileStmt(condition, decreaseAndPrint);

        return new CompStmt(declareV,
                                new CompStmt(setV,
                                        new CompStmt(loop, new PrintStmt(new VarExp("v")))));
    }

    // Creates the ordered list of examples and their menu descriptions.
    private static List<Example> createExamples() {
        List<Example> examples = new ArrayList<>();
        examples.add(new Example("Ex1: print v after assigning 2 (result: 2)", EX1));
        examples.add(new Example("Ex2: arithmetic expressions (result: 18)", EX2));
        examples.add(new Example("Ex3: conditional statement (result: 2)", EX3));
        examples.add(new Example(
                "Ex4: read test.in (result: 15, 50; empty file: 0, 0)", EX4));
        examples.add(new Example("Ex5: relational expression (result: 5)", EX5));
        examples.add(new Example(
                "Ex6: allocate references (result: (1, int), (2, Ref(int)))", EX6));
        examples.add(new Example("Ex7: read nested references (result: 20, 25)", EX7));
        examples.add(new Example("Ex8: write to the heap (result: 20, 35)", EX8));
        examples.add(new Example("Ex9: safe garbage collection (result: 20)", EX9));
        examples.add(new Example("Ex10: while loop (result: 4, 3, 2, 1, 0)", EX10));
        return Collections.unmodifiableList(examples);
    }

    // Makes a constant integer expression for the example programs.
    private static IExp number(int value) {
        return new ValueExp(new IntValue(value));
    }
}
