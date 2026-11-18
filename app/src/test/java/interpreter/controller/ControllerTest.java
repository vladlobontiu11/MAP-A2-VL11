package interpreter.controller;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerTest {
    @TempDir
    Path temporaryDirectory;

    // Runs a whole program and returns its final state.
    private PrgState run(IStmt program) throws Exception {
        PrgState state = PrgState.of(program);
        String logFile = temporaryDirectory.resolve("a2-test-log.txt").toString();
        new Controller(new Repository(state, logFile), false).allStep();
        return state;
    }

    // Checks the final output from all three example programs.
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

    // Checks that one step cannot run with an empty stack.
    @Test
    void oneStepRejectsAnEmptyExecutionStack() throws Exception {
        PrgState state = PrgState.of(new NopStmt());
        state.getExeStack().pop();

        StatementException exception = assertThrows(StatementException.class,
                () -> new Controller(new Repository(state,
                        temporaryDirectory.resolve("empty-stack-log.txt").toString()),
                        false).oneStep(state));
        assertEquals("program state stack is empty", exception.getMessage());
    }
}
