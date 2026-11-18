package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

public interface IStmt {
    // Runs this statement and returns the updated program state.
    PrgState execute(PrgState state) throws MyException;
}
