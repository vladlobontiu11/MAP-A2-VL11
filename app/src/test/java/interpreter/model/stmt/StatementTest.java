package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StatementTest {
    private static PrgState state(IStmt program) {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), program);
    }

    private static IExp number(int value) {
        return new ValueExp(new IntValue(value));
    }

    @Test
    void compositeStatementSchedulesFirstThenSecond() throws Exception {
        IStmt first = new NopStmt();
        IStmt second = new NopStmt();
        PrgState state = state(new CompStmt(first, second));

        state.getExeStack().pop().execute(state);

        assertEquals(first, state.getExeStack().pop());
        assertEquals(second, state.getExeStack().pop());
    }

    @Test
    void declarationSetsDefaultValueAndRejectsRedeclaration() throws Exception {
        PrgState state = state(new NopStmt());
        VarDeclStmt declaration = new VarDeclStmt("x", new IntType());

        declaration.execute(state);

        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
        assertThrows(StatementException.class, () -> declaration.execute(state));

        new VarDeclStmt("flag", new BoolType()).execute(state);
        assertEquals(new BoolValue(false), state.getSymTable().lookup("flag"));
    }

    @Test
    void assignmentUpdatesDeclaredVariableAndRejectsErrors() throws Exception {
        PrgState state = state(new NopStmt());
        new VarDeclStmt("x", new IntType()).execute(state);

        new AssignStmt("x", number(7)).execute(state);
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));
        assertThrows(StatementException.class,
                () -> new AssignStmt("missing", number(1)).execute(state));
        assertThrows(StatementException.class,
                () -> new AssignStmt("x", new ValueExp(new BoolValue(true))).execute(state));
    }

    @Test
    void printAppendsEvaluatedValue() throws Exception {
        PrgState state = state(new NopStmt());

        new PrintStmt(number(9)).execute(state);

        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(9), state.getOut().get(0));
    }

    @Test
    void ifSchedulesOnlyTheSelectedBranchAndRejectsNonBooleanCondition() throws Exception {
        PrgState state = state(new NopStmt());
        IStmt thenBranch = new NopStmt();
        IStmt elseBranch = new PrintStmt(number(0));

        new IfStmt(new ValueExp(new BoolValue(true)), thenBranch, elseBranch).execute(state);

        assertEquals(thenBranch, state.getExeStack().pop());
        new IfStmt(new ValueExp(new BoolValue(false)), thenBranch, elseBranch).execute(state);
        assertEquals(elseBranch, state.getExeStack().pop());
        assertThrows(StatementException.class,
                () -> new IfStmt(number(1), thenBranch, elseBranch).execute(state));
    }

    @Test
    void nopLeavesStateUnchanged() throws Exception {
        PrgState state = state(new NopStmt());

        new NopStmt().execute(state);

        assertEquals(new NopStmt(), state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(0, state.getOut().size());
    }
}
