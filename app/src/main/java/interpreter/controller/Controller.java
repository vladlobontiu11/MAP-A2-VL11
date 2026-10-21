package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

import java.util.Objects;

public class Controller {
    private final IRepository repo;
    private final boolean display;

    public Controller(IRepository repo, boolean display) {
        this.repo = Objects.requireNonNull(repo);
        this.display = display;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        if (state.getExeStack().isEmpty()) {
            throw new StatementException("program state stack is empty");
        }
        IStmt currentStatement = state.getExeStack().pop();
        return currentStatement.execute(state);
    }

    public void allStep() throws MyException {
        PrgState programState = repo.getCrtPrg();
        showState(programState);
        while (!programState.getExeStack().isEmpty()) {
            oneStep(programState);
            showState(programState);
        }
    }

    private void showState(PrgState state) {
        if (display) {
            System.out.println(state);
        }
    }
}
