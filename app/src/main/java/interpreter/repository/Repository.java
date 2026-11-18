package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> programStates = new ArrayList<>();
    private final String logFilePath;

    public Repository(PrgState programState, String logFilePath) {
        if (programState == null) {
            throw new NullPointerException("Program state cannot be null");
        }
        if (logFilePath == null) {
            throw new NullPointerException("Log file path cannot be null");
        }
        programStates.add(programState);
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg() {
        return programStates.get(0);
    }

    // Appends a snapshot of the current state to the log file.
    @Override
    public void logPrgStateExec() throws MyException {
        // Append each state so running another step does not erase earlier states.
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write(getCrtPrg().toLogString());
            writer.newLine();
        } catch (IOException exception) {
            throw new RepositoryException("cannot write the log file " + logFilePath
                    + ": " + exception.getMessage());
        }
    }
}
