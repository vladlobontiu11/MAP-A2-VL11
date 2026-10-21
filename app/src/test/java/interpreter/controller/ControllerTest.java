package interpreter.controller;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerTest {
    private static PrgState run(interpreter.model.stmt.IStmt program) throws Exception {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), program);
        new Controller(new Repository(state), false).allStep();
        return state;
    }

    @Test
    void examplesProduceExpectedOutput() throws Exception {
        PrgState first = run(Examples.EX1);
        PrgState second = run(Examples.EX2);
        PrgState third = run(Examples.EX3);

        assertEquals(new IntValue(2), first.getOut().get(0));
        assertEquals(new IntValue(18), second.getOut().get(0));
        assertEquals(new IntValue(2), third.getOut().get(0));
        assertEquals(1, first.getOut().size());
        assertEquals(1, second.getOut().size());
        assertEquals(1, third.getOut().size());
        assertTrue(first.getExeStack().isEmpty());
        assertTrue(second.getExeStack().isEmpty());
        assertTrue(third.getExeStack().isEmpty());
    }

    @Test
    void oneStepRejectsAnEmptyExecutionStack() throws Exception {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        state.getExeStack().pop();

        StatementException exception = assertThrows(StatementException.class,
                () -> new Controller(new Repository(state), false).oneStep(state));
        assertEquals("program state stack is empty", exception.getMessage());
    }
}
