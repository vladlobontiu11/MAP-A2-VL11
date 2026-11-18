package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

public interface IRepository {
    // Returns the program state stored by this repository.
    PrgState getCrtPrg();

    // Appends the current program state to the execution log.
    void logPrgStateExec() throws MyException;
}
